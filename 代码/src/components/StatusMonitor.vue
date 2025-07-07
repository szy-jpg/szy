<template>
  <div class="status-container">
    <div class="charts-row">
      <div class="chart-box">
        <div class="card-border">
          <div class="chart-title">设备一：微水局放SF6气体温度实时变化:</div>
          <canvas ref="chart1"></canvas>
        </div>
        <button class="long-btn" @click="start">开始</button>
        <div class="data-card-border">
          <div class="data-title">设备一</div>
          <div class="realtime-box flex-box">
            <div class="item"><span class="label">微水</span><span class="value dew">{{ dew1 }}</span></div>
            <div class="item"><span class="label">局放</span><span class="value pd">{{ pd1 }}</span></div>
            <div class="item"><span class="label">SF6气体</span><span class="value sf6">{{ sf61 }}</span></div>
            <div class="item"><span class="label">温度</span><span class="value temp">{{ temp1 }}</span></div>
          </div>
        </div>
      </div>
      <div class="chart-box">
        <div class="card-border">
          <div class="chart-title">设备二：微水局放SF6气体温度实时变化:</div>
          <canvas ref="chart2"></canvas>
        </div>
        <button class="long-btn" @click="stop">暂停</button>
        <div class="data-card-border">
          <div class="data-title">设备二</div>
          <div class="realtime-box flex-box">
            <div class="item"><span class="label">微水</span><span class="value dew">{{ dew2 }}</span></div>
            <div class="item"><span class="label">局放</span><span class="value pd">{{ pd2 }}</span></div>
            <div class="item"><span class="label">SF6气体</span><span class="value sf6">{{ sf62 }}</span></div>
            <div class="item"><span class="label">温度</span><span class="value temp">{{ temp2 }}</span></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick, onUnmounted } from 'vue';
import axios from 'axios';
import * as echarts from 'echarts';

const chart1 = ref(null);
const chart2 = ref(null);
let chartInstance1 = null;
let chartInstance2 = null;
let timer = null;

// 实时数据
const dew1 = ref(0), pd1 = ref(0), sf61 = ref(0), temp1 = ref(0);
const dew2 = ref(0), pd2 = ref(0), sf62 = ref(0), temp2 = ref(0);

// 历史数据用于图表
const history1 = ref({ dew: [], pd: [], sf6: [], temp: [], time: [] });
const history2 = ref({ dew: [], pd: [], sf6: [], temp: [], time: [] });

function fetchData() {
  axios.get('http://localhost:8081/api/deviceStatus')
    .then(res => {
      const data = res.data;
      const now = new Date().toLocaleTimeString();
      if (data.device1) {
        dew1.value = Number(data.device1.dew);
        pd1.value = Number(data.device1.pd);
        sf61.value = Number(data.device1.sf6);
        temp1.value = Number(data.device1.temp);
        history1.value.dew.push(dew1.value);
        history1.value.pd.push(pd1.value);
        history1.value.sf6.push(sf61.value);
        history1.value.temp.push(temp1.value);
        history1.value.time.push(now);
        if (history1.value.time.length > 20) {
          history1.value.dew.shift();
          history1.value.pd.shift();
          history1.value.sf6.shift();
          history1.value.temp.shift();
          history1.value.time.shift();
        }
      }
      if (data.device2) {
        dew2.value = Number(data.device2.dew);
        pd2.value = Number(data.device2.pd);
        sf62.value = Number(data.device2.sf6);
        temp2.value = Number(data.device2.temp);
        history2.value.dew.push(dew2.value);
        history2.value.pd.push(pd2.value);
        history2.value.sf6.push(sf62.value);
        history2.value.temp.push(temp2.value);
        history2.value.time.push(now);
        if (history2.value.time.length > 20) {
          history2.value.dew.shift();
          history2.value.pd.shift();
          history2.value.sf6.shift();
          history2.value.temp.shift();
          history2.value.time.shift();
        }
      }
      updateCharts();
    })
    .catch(() => {});
}

function updateCharts() {
  if (chartInstance1 && history1.value.time.length > 0) {
    chartInstance1.setOption({
      xAxis: { data: history1.value.time },
      yAxis: { type: 'value', name: '数值' },
      legend: { data: ['微水', '局放', 'SF6气体', '温度'] },
      series: [
        { name: '微水', data: history1.value.dew, type: 'line' },
        { name: '局放', data: history1.value.pd, type: 'line' },
        { name: 'SF6气体', data: history1.value.sf6, type: 'line' },
        { name: '温度', data: history1.value.temp, type: 'line' }
      ]
    });
  }
  if (chartInstance2 && history2.value.time.length > 0) {
    chartInstance2.setOption({
      xAxis: { data: history2.value.time },
      yAxis: { type: 'value', name: '数值' },
      legend: { data: ['微水', '局放', 'SF6气体', '温度'] },
      series: [
        { name: '微水', data: history2.value.dew, type: 'line' },
        { name: '局放', data: history2.value.pd, type: 'line' },
        { name: 'SF6气体', data: history2.value.sf6, type: 'line' },
        { name: '温度', data: history2.value.temp, type: 'line' }
      ]
    });
  }
}

function start() {
  stop();
  fetchData();
  timer = setInterval(fetchData, 2000);
}
function stop() {
  if (timer) clearInterval(timer);
  timer = null;
}

onMounted(() => {
  nextTick(() => {
    if (!chart1.value || !chart2.value) return;
    chart1.value.width = 500;
    chart1.value.height = 420;
    chart2.value.width = 500;
    chart2.value.height = 420;
    chartInstance1 = echarts.init(chart1.value);
    chartInstance2 = echarts.init(chart2.value);
    fetchData();
  });
});
onUnmounted(() => {
  stop();
  if (chartInstance1) chartInstance1.dispose();
  if (chartInstance2) chartInstance2.dispose();
});
</script>

<style scoped>
.status-container {
  width: 100vw;
  padding-left: 40px;
  box-sizing: border-box;
}
.charts-row {
  display: flex;
  justify-content: flex-start;
}
.chart-box {
  width: 500px;
  height: 520px;
  margin: 0 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}
canvas { display: block; }
.btn-row-split {
  display: flex;
  justify-content: center;
  margin: 24px 0 18px 0;
  gap: 32px;
}
.long-btn {
  width: 100%;
  max-width: none;
  margin: 18px 0 10px 0;
  padding: 16px 0;
  border: none;
  border-radius: 12px;
  background: linear-gradient(90deg, #4fc3f7 0%, #1976d2 100%);
  color: #fff;
  font-size: 22px;
  font-weight: bold;
  box-shadow: 0 2px 8px #e3e3e3;
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s;
  text-align: center;
  letter-spacing: 2px;
}
.long-btn:hover {
  background: linear-gradient(90deg, #1976d2 0%, #4fc3f7 100%);
  box-shadow: 0 4px 16px #b3e5fc;
}
.realtime-row {
  display: flex;
  gap: 20px;
  width: 100%;
  margin-top: 18px;
  justify-content: center;
}
.realtime-box.flex-box {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 28px;
  background: transparent !important;
  box-shadow: none !important;
  border-radius: 0 !important;
}
.item {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 70px;
}
.label {
  font-size: 15px;
  color: #888;
  margin-bottom: 2px;
}
.value {
  font-size: 22px;
  font-weight: bold;
}
.value.dew { color: #1976d2; }
.value.pd { color: #43a047; }
.value.sf6 { color: #ffb300; }
.value.temp { color: #e57373; }
.card-border {
  border: 2px solid #000;
  border-radius: 16px;
  padding: 24px 24px 12px 24px;
  margin-bottom: 18px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}
.data-card-border {
  border: 2px solid #000;
  border-radius: 16px;
  padding: 16px 0 8px 0;
  margin-top: 12px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.data-title {
  font-size: 16px;
  font-weight: bold;
  color: #1976d2;
  margin-bottom: 8px;
  text-align: center;
  width: 100%;
}
.chart-title {
  font-size: 18px;
  font-weight: bold;
  color: #1976d2;
  margin-bottom: 8px;
  text-align: center;
  width: 100%;
}
</style> 