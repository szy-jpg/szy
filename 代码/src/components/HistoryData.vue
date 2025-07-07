<template>
  <div class="history-data-main">
    <div class="history-data-header">
      <div class="history-title">历史数据图表</div>
      <div class="history-user">
        <img src="@/assets/loge.png" class="user-logo" />
        <span class="user-label">用户名：</span>
        <span class="user-name">longxin</span>
      </div>
    </div>
    <div class="history-data-toolbar">
      <button class="hd-btn" @click="fetchData">显示</button>
      <button class="hd-btn" @click="clearTable">清除</button>
      <button class="hd-btn" @click="fetchData">刷新</button>
      <button class="hd-btn">查询</button>
      <select class="hd-select" v-model="device">
        <option value="">全部设备</option>
        <option value="1">设备1</option>
        <option value="2">设备2</option>
      </select>
      <select class="hd-select" v-model="field">
        <option value="">全部字段</option>
        <option value="temp">温度</option>
        <option value="humi">湿度</option>
        <option value="dew">微水</option>
        <option value="pd">局放</option>
        <option value="sf6">SF6气体</option>
      </select>
      <input class="hd-input" v-model="query" placeholder="请输入查询内容" />
    </div>
    <div class="history-data-table-wrap">
      <table class="history-data-table">
        <thead>
          <tr>
            <th>设备序号</th>
            <th>时间</th>
            <th v-if="field === '' || field === 'temp'">温度</th>
            <th v-if="field === '' || field === 'humi'">湿度</th>
            <th v-if="field === '' || field === 'dew'">微水</th>
            <th v-if="field === '' || field === 'pd'">局放</th>
            <th v-if="field === '' || field === 'sf6'">SF6气体</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="row in filteredData" :key="row.id">
            <td>{{ row.device }}</td>
            <td>{{ row.time }}</td>
            <td v-if="field === '' || field === 'temp'">{{ row.temp }}</td>
            <td v-if="field === '' || field === 'humi'">{{ row.humi }}</td>
            <td v-if="field === '' || field === 'dew'">{{ row.dew }}</td>
            <td v-if="field === '' || field === 'pd'">{{ row.pd }}</td>
            <td v-if="field === '' || field === 'sf6'">{{ row.sf6 }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
const tableData = ref([]);
const device = ref('');
const field = ref('');
const query = ref('');

const fetchData = async () => {
  // 假设后端接口为 /api/historyData，返回数组
  const res = await axios.get('http://localhost:8081/api/historyData');
  tableData.value = res.data;
};
const clearTable = () => {
  tableData.value = [];
};
const filteredData = computed(() => {
  let data = tableData.value;
  if (device.value) data = data.filter(row => String(row.device) === device.value);
  if (field.value && query.value) data = data.filter(row => String(row[field.value]).includes(query.value));
  return data;
});
onMounted(fetchData);
</script>

<style scoped>
.history-data-main {
  max-width: 1200px;
  margin: 32px auto;
  background: #f6f8fa;
  border-radius: 12px;
  box-shadow: 0 2px 16px #0001;
  padding: 32px 32px 24px 32px;
}
.history-data-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 18px;
}
.history-title {
  font-size: 38px;
  font-weight: bold;
  color: #222;
  letter-spacing: 2px;
}
.history-user {
  display: flex;
  align-items: center;
  gap: 8px;
}
.user-logo {
  width: 56px;
  height: 56px;
  border-radius: 8px;
  background: #fff;
  object-fit: contain;
}
.user-label {
  font-size: 18px;
  color: #333;
}
.user-name {
  font-size: 20px;
  color: #222;
  font-family: 'Consolas', monospace;
}
.history-data-toolbar {
  display: flex;
  align-items: center;
  gap: 32px;
  margin-bottom: 18px;
  margin-top: 8px;
}
.hd-btn {
  background: #444;
  color: #fff;
  border: none;
  border-radius: 8px;
  padding: 10px 38px;
  font-size: 18px;
  font-weight: bold;
  margin-right: 8px;
  cursor: pointer;
  transition: background 0.2s;
}
.hd-btn:hover {
  background: #1976d2;
}
.hd-select, .hd-input {
  font-size: 16px;
  padding: 8px 18px;
  border-radius: 6px;
  border: 1px solid #bbb;
  margin-right: 8px;
}
.history-data-table-wrap {
  background: #222;
  border-radius: 10px;
  overflow: auto;
  max-height: 420px;
  padding: 0;
}
.history-data-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  color: #fff;
  background: #222;
  border-radius: 10px;
  overflow: hidden;
}
.history-data-table th, .history-data-table td {
  border: 1px solid #444;
  padding: 12px 16px;
  text-align: center;
  font-size: 18px;
}
.history-data-table th {
  background: #333;
  color: #fff;
  font-weight: bold;
  position: sticky;
  top: 0;
  z-index: 2;
  box-shadow: 0 2px 8px #1118;
  border-bottom: 2px solid #444;
}
</style> 