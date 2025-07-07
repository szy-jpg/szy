<template>
  <div class="env-main-bg">
    <div class="env-main-wrap">
      <div class="card-border">
        <div class="env-top-cards">
          <div class="env-card temp">温度：{{ temp }}°C</div>
          <div class="env-card humi">湿度：{{ humi }}%RH</div>
          <div class="env-card gas">可燃气体浓度：{{ gas }} e-3 ppm</div>
          <div class="env-card warn">预警：<span :style="{color: warn ? '#43a047' : '#e57373'}">{{ warn ? 'ON' : 'OFF' }}</span></div>
          <div class="env-card threshold">
            <div style="font-size:16px; font-weight:bold; margin-bottom:4px;">监测阈值说明</div>
            <div style="font-size:14px; line-height:1.7;">
              温度：20~40°C<br>
              湿度：50~100%RH<br>
              气体浓度：&lt; 80000 e-3 ppm
            </div>
          </div>
        </div>
      </div>
      <div class="card-border">
        <div class="env-table-card">
          <div class="alarm-table">
            <table>
              <thead>
                <tr><th>设备序号</th><th>时间</th><th>温度</th><th>湿度</th></tr>
              </thead>
              <tbody>
                <tr v-for="item in alarms" :key="item.id">
                  <td>{{ item.id }}</td>
                  <td>{{ item.time }}</td>
                  <td>{{ item.temp }}</td>
                  <td>{{ item.humi }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="card-border">
        <div class="env-bottom-cards-vertical">
          <div class="env-card device device-bottom">
            <span class="dev-label">设备一：</span>
            <span class="data-item">温度 <input :value="temp" readonly /> °C</span>
            <span class="data-item">湿度 <input :value="humi" readonly /> %RH</span>
            <span class="data-item">可燃气体浓度 <input class="gas-input" :value="gas" readonly /> e-3 ppm</span>
          </div>
          <div class="env-card device device-bottom">
            <span class="dev-label">设备二：</span>
            <span class="data-item">温度 <input :value="temp" readonly /> °C</span>
            <span class="data-item">湿度 <input :value="humi" readonly /> %RH</span>
            <span class="data-item">可燃气体浓度 <input class="gas-input" :value="gas" readonly /> e-3 ppm</span>
          </div>
        </div>
      </div>
      <!-- 新增报警记录卡片 -->
      <div class="card-border" style="margin-top: 24px;">
        <div class="env-table-card">
          <div style="font-size:20px;font-weight:bold;margin-bottom:10px;">报警记录：</div>
          <div class="alarm-table">
            <table>
              <thead>
                <tr>
                  <th>设备序号</th>
                  <th>时间</th>
                  <th>温度</th>
                  <th>湿度</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in alarmHistory" :key="item.id + item.time">
                  <td>{{ item.id }}</td>
                  <td>{{ item.time }}</td>
                  <td>{{ item.temp }}</td>
                  <td>{{ item.humi }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import axios from 'axios';

const temp = ref(0), humi = ref(0), gas = ref(0), warn = ref(false);
const alarms = ref([]);
let timer = null;

const alarmHistory = ref([
  { id: 1, time: '2024-07-06 22:52:32', temp: 30, humi: 50 },
  { id: 2, time: '2024-07-06 22:53:10', temp: 29, humi: 48 },
  { id: 3, time: '2024-07-06 22:54:01', temp: 31, humi: 52 }
]);

// eslint-disable-next-line no-unused-vars
function fetchEnv() {
  axios.get('http://localhost:8081/api/envStatus').then(res => {
    const data = res.data;
    console.log('envStatus返回', data);
    console.log('alarms', data.alarms);
    temp.value = data.temp;
    humi.value = data.humi;
    gas.value = data.gas;
    warn.value = data.warn;
    alarms.value = data.alarms || [];
  });
}

onMounted(() => {
  fetchEnv();
  timer = setInterval(fetchEnv, 2000);
});
onUnmounted(() => {
  if (timer) clearInterval(timer);
});
</script>

<style scoped>
.env-main-bg {
  background: #f6f8fa;
  min-height: 100vh;
  padding: 12px 0;
}
.env-main-wrap {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 24px;
}
.env-top-cards {
  display: flex;
  gap: 24px;
  margin-bottom: 8px;
  justify-content: center;
}
.env-card {
  min-width: 200px;
  background: linear-gradient(135deg, #e3f2fd 0%, #fff 100%);
  border-radius: 16px;
  box-shadow: 0 2px 12px #e3e3e3;
  padding: 18px 32px;
  font-size: 22px;
  font-weight: bold;
  display: flex;
  align-items: center;
  justify-content: center;
}
.env-card.temp { color: #e57373; background: linear-gradient(135deg, #ffeaea 0%, #fff 100%);}
.env-card.humi { color: #1976d2; background: linear-gradient(135deg, #e3f2fd 0%, #fff 100%);}
.env-card.gas { color: #ffb300; background: linear-gradient(135deg, #fff8e1 0%, #fff 100%);}
.env-card.warn { color: #43a047; background: linear-gradient(135deg, #e8f5e9 0%, #fff 100%);}
.env-card.threshold {
  color: #555;
  background: linear-gradient(135deg, #f0f4f8 0%, #fff 100%);
  min-width: 220px;
  align-items: flex-start;
  box-shadow: 0 2px 12px #e3e3e3;
}
.env-table-card {
  background: linear-gradient(135deg, #fff 0%, #f6f8fa 100%);
  border-radius: 16px;
  box-shadow: 0 2px 12px #e3e3e3;
  padding: 18px 24px;
  margin-bottom: 24px;
  max-width: 100%;
}
.alarm-table table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  background: #fafbfc;
  border-radius: 12px;
  overflow: hidden;
}
.alarm-table th, .alarm-table td {
  border: 1px solid #eee;
  padding: 10px 18px;
  text-align: center;
  font-size: 18px;
}
.alarm-table th {
  background: #f0f4f8;
  color: #1976d2;
  font-weight: bold;
}
.env-bottom-cards-vertical {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 24px;
}
.env-card.device.device-bottom {
  width: 100%;
  max-width: 1200px;
  min-width: 350px;
  margin: 0 auto;
  background: linear-gradient(135deg, #fff 0%, #f6f8fa 100%);
  border-radius: 16px;
  box-shadow: 0 2px 12px #e3e3e3;
  padding: 18px 32px;
  font-size: 18px;
  font-weight: bold;
  display: grid;
  grid-template-columns: 1.2fr 1fr 1.2fr 1.8fr 2fr;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
  justify-content: flex-start;
  box-sizing: border-box;
}
.data-item {
  display: flex;
  align-items: center;
  gap: 4px;
  white-space: nowrap;
}
.dev-label {
  font-weight: bold;
  margin-right: 8px;
}
input[readonly] {
  background: #f5f7fa;
  border: 1px solid #e0e0e0;
  border-radius: 6px;
  padding: 2px 8px;
  font-size: 16px;
  color: #1976d2;
  font-weight: bold;
  width: 60px;
  text-align: center;
}
input.gas-input[readonly] {
  width: 90px;
}
.card-border {
  border: 2px solid #000;
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 24px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}
.env-table-card,
.env-card.device.device-bottom {
  background: transparent !important;
  box-shadow: none !important;
  border-radius: 0 !important;
}
</style> 