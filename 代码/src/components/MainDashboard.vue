/* eslint-disable */
<template>
  <div class="dashboard-bg">
    <div class="dashboard-card">
      <h2 class="dashboard-title">功能总览</h2>
      <div class="dashboard-desc">欢迎使用龙芯工业GIS监控系统，您可以在此快速访问各项核心功能。</div>
      <ul class="dashboard-list">
        <li><span class="dashboard-icon">📷</span> 状态监控</li>
        <li><span class="dashboard-icon">🖼️</span> 环境监测</li>
        <li><span class="dashboard-icon">📈</span> 历史曲线</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.dashboard-bg {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fafbfc;
  min-height: 80vh;
}
.dashboard-card {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 4px 24px #0001;
  padding: 64px 90px;
  min-width: 520px;
  text-align: center;
}
.dashboard-title {
  font-size: 52px;
  color: #1976d2;
  font-weight: bold;
  margin-bottom: 28px;
  letter-spacing: 2px;
  border-bottom: 2px solid #1976d2;
  display: inline-block;
  padding-bottom: 8px;
}
.dashboard-desc {
  font-size: 28px;
  color: #555;
  margin-bottom: 38px;
  margin-top: 10px;
}
.dashboard-list {
  list-style: none;
  padding: 0;
  margin: 0;
}
.dashboard-list li {
  font-size: 36px;
  color: #333;
  margin: 32px 0;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
}
.dashboard-icon {
  font-size: 44px;
}
</style> 