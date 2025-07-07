/* eslint-disable */
<template>
  <div class="home-bg">
    <div class="home-title">欢迎使用龙芯工业GIS监控系统</div>
    <div class="home-datetime">{{ currentDateTime }}</div>
    <div class="home-subtitle">本系统用于GIS设备在线监测、数据分析与可视化</div>
    <ul class="home-features">
      <li>● 实时状态监控</li>
      <li>● 环境参数监测</li>
      <li>● 历史数据曲线与表格</li>
      <li>● 设备报警与预警</li>
      <li>● 数据可视化与导出</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      currentDateTime: this.getCurrentDateTime(),
      timer: null
    }
  },
  methods: {
    getCurrentDateTime() {
      const now = new Date();
      const year = now.getFullYear();
      const month = (now.getMonth() + 1).toString().padStart(2, '0');
      const day = now.getDate().toString().padStart(2, '0');
      const hour = now.getHours().toString().padStart(2, '0');
      const minute = now.getMinutes().toString().padStart(2, '0');
      const second = now.getSeconds().toString().padStart(2, '0');
      return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    }
  },
  mounted() {
    this.timer = setInterval(() => {
      this.currentDateTime = this.getCurrentDateTime();
    }, 1000);
  },
  beforeUnmount() {
    clearInterval(this.timer);
  }
}
</script>

<style scoped>
.home-bg {
  min-height: 100vh;
  background: #f6f8fa;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  padding-top: 60px;
}
.home-logo {
  width: 110px;
  margin-bottom: 24px;
}
.home-title {
  font-size: 44px;
  font-weight: bold;
  color: #1976d2;
  margin-bottom: 18px;
  letter-spacing: 3px;
}
.home-datetime {
  font-size: 28px;
  color: #1976d2;
  margin-bottom: 16px;
  font-weight: 500;
  letter-spacing: 1px;
}
.home-subtitle {
  font-size: 26px;
  color: #555;
  margin-bottom: 32px;
}
.home-features {
  list-style: none;
  padding: 0;
  margin: 0;
  color: #333;
  font-size: 24px;
  line-height: 2.2;
  text-align: left;
}
</style> 