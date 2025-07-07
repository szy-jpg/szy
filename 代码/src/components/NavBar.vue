<template>
  <div class="nav-bar-gradient">
    <div class="nav-left">
      <span class="nav-title-blue">龙芯工业GIS监控系统</span>
    </div>
    <div class="nav-center">
      <div class="nav-circle-btn" :class="{active: page==='home'}" @click="$emit('nav','home')">
        <span class="nav-icon">🏠</span>
        <span class="nav-btn-text">首页</span>
      </div>
      <div class="nav-circle-btn" :class="{active: page==='dashboard'}" @click="$emit('nav','dashboard')">
        <span class="nav-icon">🖥️</span>
        <span class="nav-btn-text">主界面</span>
      </div>
      <div class="nav-circle-btn" :class="{active: page==='historyData'}" @click="$emit('nav','historyData')">
        <span class="nav-icon">📑</span>
        <span class="nav-btn-text">历史数据</span>
      </div>
      <div class="nav-circle-btn" :class="{active: page==='status'}" @click="$emit('nav','status')">
        <span class="nav-icon">🛠️</span>
        <span class="nav-btn-text">设备监控</span>
      </div>
      <div class="nav-circle-btn" :class="{active: page==='logout'}" @click="$emit('nav','logout')">
        <span class="nav-icon">⏻</span>
        <span class="nav-btn-text">用户退出</span>
      </div>
    </div>
    <div class="nav-right">
      <button class="nav-window-btn">-</button>
      <button class="nav-window-btn">+</button>
      <button class="nav-window-btn">×</button>
    </div>

</div>
</template>

<script setup>
defineProps({ page: String });
</script>

<style scoped>
.nav-bar-gradient {
  width: 100%;
  height: 88px;
  background: linear-gradient(90deg, #23272e 0%, #444 100%);
  display: flex;
  align-items: center;
  justify-content: flex-start;
  box-shadow: 0 2px 8px #0002;
  padding: 0 18px 0 8px;
}
.nav-left {
  display: flex;
  align-items: center;
  margin-right: 18px;
}
.nav-logo {
  height: 40px;
  margin-right: 0;
}
.nav-title-blue {
  color: #4fc3f7;
  font-size: 22px;
  font-weight: bold;
  margin-left: 12px;
  letter-spacing: 1px;
}
.nav-center {
  display: flex;
  gap: 24px;
  margin-left: 12px;
}
.nav-circle-btn {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  min-width: 80px;
  height: 44px;
  border-radius: 22px;
  background: transparent;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
  margin: 0 2px;
  border: 2px solid transparent;
  padding: 0 12px;
}
.nav-circle-btn .nav-icon {
  font-size: 22px;
  margin-right: 6px;
  margin-bottom: 0;
}
.nav-btn-text {
  white-space: nowrap;
}
.nav-circle-btn.active,
.nav-circle-btn:hover {
  background: #fff;
  color: #23272e;
  border: 2px solid #4fc3f7;
}
.nav-right {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-left: auto;
}
.nav-window-btn {
  width: 26px;
  height: 26px;
  border-radius: 50%;
  border: none;
  background: #444;
  color: #fff;
  font-size: 16px;
  margin-left: 4px;
  cursor: pointer;
  transition: background 0.2s, color 0.2s;
}
.nav-window-btn:hover {
  background: #ff4b69;
  color: #fff;
}
</style> 