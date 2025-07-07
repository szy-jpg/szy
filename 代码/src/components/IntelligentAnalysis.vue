<template>
  <div class="analysis-container">
    <h2 style="margin-bottom: 24px;">智能分析</h2>
    <div class="analysis-charts">
      <div class="analysis-card analysis-alarm">
        <div class="analysis-title">设备报警次数统计</div>
        <div id="alarmChart" style="width: 380px; height: 260px;"></div>
      </div>
      <div
        class="analysis-card"
        v-for="dev in deviceList"
        :key="dev.id"
      >
        <div class="analysis-title">环境温度趋势（设备{{dev.id}}）</div>
        <div :id="'tempChart-' + dev.id" style="width: 380px; height: 260px;"></div>
      </div>
    </div>
    <div class="analysis-anomaly anomaly-wide">
      <h3 style="margin-bottom: 12px;">异常检测</h3>
      <div class="anomaly-table-wrap">
        <table v-if="uniqueAnomalyList.length > 0" class="anomaly-table">
          <thead>
            <tr>
              <th>设备序号</th>
              <th>时间</th>
              <th>异常类型</th>
              <th>异常值</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in uniqueAnomalyList" :key="item.device_id + '-' + item.record_time + '-' + item.temperature">
              <td>{{ item.device_id }}</td>
              <td>{{ formatTime(item.record_time) }}</td>
              <td>温度异常</td>
              <td>{{ item.temperature }}℃</td>
            </tr>
          </tbody>
        </table>
        <div v-else style="padding: 18px; text-align: center; color: #888;">暂无异常</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick, computed } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

const alarmList = ref([])
const tempListMap = ref({}) // { deviceId: [温度数据] }
const anomalyList = ref([])
const deviceList = ref([])

function formatTime(str) {
  if (!str) return '';
  // 兼容ISO格式，显示到分钟
  return str.replace('T', ' ').substring(0, 16);
}

const uniqueAnomalyList = computed(() => {
  const seen = new Set();
  return anomalyList.value.filter(item => {
    const key = `${item.device_id}-${formatTime(item.record_time)}-${item.temperature}`;
    if (seen.has(key)) return false;
    seen.add(key);
    return true;
  });
});

onMounted(async () => {
  // 1. 获取报警统计和设备列表
  const alarmRes = await axios.get('/api/analysis/alarm-count')
  alarmList.value = alarmRes.data
  deviceList.value = alarmList.value.map(i => ({ id: i.device_id, name: `设备${i.device_id}` }))

  // 2. 绘制报警统计图
  const alarmChart = echarts.init(document.getElementById('alarmChart'))
  alarmChart.setOption({
    title: { text: '设备报警次数统计' },
    xAxis: { type: 'category', data: alarmList.value.map(i => i.device_id) },
    yAxis: { type: 'value' },
    series: [{ type: 'bar', data: alarmList.value.map(i => i.count), name: '报警次数' }]
  })

  // 3. 获取每个设备的温度趋势并绘制
  for (const dev of deviceList.value) {
    const tempRes = await axios.get('/api/analysis/temp-trend', { params: { deviceId: dev.id } })
    tempListMap.value[dev.id] = tempRes.data
    await nextTick()
    const chart = echarts.init(document.getElementById('tempChart-' + dev.id))
    chart.setOption({
      title: { text: `环境温度趋势（设备${dev.id}）` },
      xAxis: { type: 'category', data: tempRes.data.map(i => formatTime(i.record_time)) },
      yAxis: { type: 'value' },
      series: [{ type: 'line', data: tempRes.data.map(i => i.temperature), name: '温度' }]
    })
  }

  // 4. 异常检测
  const anomalyRes = await axios.get('/api/analysis/anomaly')
  anomalyList.value = anomalyRes.data
})
</script>

<style scoped>
.analysis-container {
  padding: 32px;
}
.analysis-charts {
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
}
.analysis-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 24px #0001;
  padding: 18px 18px 8px 18px;
  margin-bottom: 24px;
  min-width: 440px;
  max-width: 480px;
}
.analysis-title {
  font-size: 22px;
  color: #1976d2;
  font-weight: bold;
  margin-bottom: 12px;
  text-align: center;
}
.analysis-alarm {
  margin-bottom: 32px;
}
.analysis-anomaly {
  background: #f6f8fa;
  border-radius: 12px;
  padding: 18px 24px;
  margin-top: 32px;
  min-width: 400px;
  max-width: 900px;
}
.analysis-anomaly.anomaly-wide {
  background: #f6f8fa;
  border-radius: 12px;
  padding: 12px 12px 18px 18px;
  margin-top: 18px;
  min-width: 900px;
  max-width: 1200px;
  margin-left: 0;
  margin-right: auto;
}
.anomaly-table-wrap {
  max-height: 300px;
  overflow-y: auto;
  background: #222;
  border-radius: 10px;
  box-shadow: 0 2px 16px #0001;
  padding: 0;
}
.anomaly-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  color: #fff;
  background: #222;
  border-radius: 10px;
  overflow: hidden;
}
.anomaly-table th, .anomaly-table td {
  border: 1px solid #444;
  padding: 12px 16px;
  text-align: center;
  font-size: 18px;
}
.anomaly-table th {
  background: #333;
  color: #fff;
  font-weight: bold;
  position: sticky;
  top: 0;
  z-index: 2;
  box-shadow: 0 2px 8px #1118;
  border-bottom: 2px solid #444;
}
@media (max-width: 1100px) {
  .analysis-charts {
    flex-direction: column;
    align-items: center;
  }
  .analysis-card {
    min-width: 320px;
    max-width: 98vw;
  }
}
</style>
