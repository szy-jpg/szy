<template>
  <div class="history-main-bg">
    <div class="history-main-wrap">
      <div class="history-top-card">
        <div class="chart-title">历史曲线</div>
      </div>
      <div class="history-chart-card">
        <canvas ref="chart"></canvas>
      </div>
      <div class="switch-row">
        <label><input type="checkbox" v-model="showDew" /> 微水</label>
        <label><input type="checkbox" v-model="showPd" /> 局放</label>
        <label><input type="checkbox" v-model="showSf6" /> SF6气体</label>
        <label><input type="checkbox" v-model="showTemp" /> 温度</label>
        <button @click="refresh" class="refresh-btn">刷新</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import Chart from 'chart.js/auto';

const chart = ref(null);
let chartInstance = null;

const showDew = ref(true);
const showPd = ref(true);
const showSf6 = ref(true);
const showTemp = ref(true);

// 模拟历史数据
const labels = ['08:00', '08:10', '08:20', '08:30', '08:40', '08:50', '09:00', '09:10', '09:20', '09:30'];
const dewData = [12, 15, 14, 18, 17, 16, 19, 20, 18, 17];
const pdData = [30, 28, 35, 33, 32, 31, 36, 38, 37, 35];
const sf6Data = [60, 62, 61, 65, 64, 63, 66, 68, 67, 65];
const tempData = [25, 26, 27, 28, 27, 26, 29, 30, 28, 27];

const getDatasets = () => {
  const sets = [];
  if (showDew.value) sets.push({ label: '微水', data: dewData, borderColor: '#1976d2', fill: false });
  if (showPd.value) sets.push({ label: '局放', data: pdData, borderColor: '#ffb300', fill: false });
  if (showSf6.value) sets.push({ label: 'SF6气体', data: sf6Data, borderColor: '#43a047', fill: false });
  if (showTemp.value) sets.push({ label: '温度', data: tempData, borderColor: '#e57373', fill: false });
  return sets;
};

const renderChart = () => {
  if (chartInstance) chartInstance.destroy();
  chartInstance = new Chart(chart.value, {
    type: 'line',
    data: {
      labels,
      datasets: getDatasets()
    },
    options: {
      responsive: true,
      plugins: {
        legend: { display: true }
      }
    }
  });
};

const refresh = () => {
  renderChart();
};

onMounted(() => {
  renderChart();
});

watch([showDew, showPd, showSf6, showTemp], renderChart);
</script>

<style scoped>
.history-main-bg {
  background: #f6f8fa;
  min-height: 100vh;
  padding: 32px 0;
}
.history-main-wrap {
  max-width: 900px;
  margin: 0 auto;
  padding: 0 24px;
}
.history-top-card {
  background: linear-gradient(135deg, #e3f2fd 0%, #fff 100%);
  border-radius: 16px;
  box-shadow: 0 2px 12px #e3e3e3;
  border: 2px solid #000;
  padding: 18px 0 8px 0;
  margin-bottom: 18px;
  text-align: center;
}
.chart-title {
  font-size: 26px;
  font-weight: bold;
  color: #1976d2;
  letter-spacing: 2px;
}
.history-chart-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 12px #e3e3e3;
  border: 2px solid #000;
  padding: 24px 18px;
  margin-bottom: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.switch-row {
  margin-top: 0;
  display: flex;
  align-items: center;
  gap: 24px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px #e3e3e3;
  border: 2px solid #000;
  padding: 12px 24px;
  width: 100%;
  justify-content: space-between;
}
.switch-row label {
  font-size: 18px;
  color: #1976d2;
  font-weight: bold;
}
.refresh-btn {
  background: linear-gradient(135deg, #ff6b81 0%, #ffb6b9 100%);
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 8px 28px;
  font-size: 18px;
  font-weight: bold;
  box-shadow: 0 2px 8px #e3e3e3;
  cursor: pointer;
  transition: background 0.2s;
}
.refresh-btn:hover {
  background: linear-gradient(135deg, #ff4b69 0%, #ffb6b9 100%);
}
</style> 