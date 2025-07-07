<template>
  <div class="main-layout">
    <UserLogin v-if="!isLogin" @login-success="onLoginSuccess" />
    <template v-else>
      <aside class="sidebar">
        <img src="@/assets/loge.png" class="side-logo-img" />
        <ul>
          <li :class="{active: page==='status'}" @click="page='status'">
            <div class="side-menu-icon">📷</div>
            <span>状态监控</span>
          </li>
          <li :class="{active: page==='env'}" @click="page='env'">
            <div class="side-menu-icon">🖼️</div>
            <span>环境监测</span>
          </li>
          <li :class="{active: page==='history'}" @click="page='history'">
            <div class="side-menu-icon">🗂️</div>
            <span>历史曲线</span>
          </li>
          <li :class="{active: page==='historyData'}" @click="page='historyData'">
            <div class="side-menu-icon">📑</div>
            <span>历史数据</span>
          </li>
          <li :class="{active: page==='analysis'}" @click="page='analysis'">
            <div class="side-menu-icon">📊</div>
            <span>智能分析</span>
          </li>
        </ul>
      </aside>
      <div class="main-content">
        <NavBar :page="page" @nav="onNav" @logout="logout" />
        <div class="content">
          <HomePage v-if="page==='home'" />
          <MainDashboard v-else-if="page==='dashboard'" />
          <StatusMonitor v-else-if="page==='status'" />
          <EnvMonitor v-else-if="page==='env'" />
          <HistoryChart v-else-if="page==='history'" />
          <HistoryData v-else-if="page==='historyData'" />
          <IntelligentAnalysis v-else-if="page==='analysis'" />
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import StatusMonitor from './components/StatusMonitor.vue';
import EnvMonitor from './components/EnvMonitor.vue';
import HistoryChart from './components/HistoryChart.vue';
import HomePage from './components/HomePage.vue';
import MainDashboard from './components/MainDashboard.vue';
import UserLogin from './components/UserLogin.vue';
import NavBar from './components/NavBar.vue';
import HistoryData from './components/HistoryData.vue';
import IntelligentAnalysis from './components/IntelligentAnalysis.vue';
const isLogin = ref(false);
const page = ref('login');
function onLoginSuccess() {
  isLogin.value = true;
  page.value = 'dashboard';
}
function logout() {
  isLogin.value = false;
  page.value = 'login';
}
function onNav(val) {
  if(val==='logout') { logout(); return; }
  page.value = val;
}
</script>

<style scoped>
.main-layout { display: flex; height: 100vh; }
.sidebar {
  width: 120px;
  min-width: 120px;
  background: #222;
  color: #fff;
  position: relative;
  padding-top: 100px;
  margin-top: 0;
}
.side-logo { padding: 24px 0; text-align: center; font-weight: bold; color: #4fc3f7; }
.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
.sidebar li {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 18px 0;
  cursor: pointer;
  color: #888;
  font-size: 16px;
  transition: background 0.2s, color 0.2s;
}
.side-menu-icon {
  font-size: 28px;
  margin-bottom: 6px;
  color: #888;
  transition: color 0.2s;
}
.sidebar li.active,
.sidebar li:hover {
  background: #222;
  color: #4fc3f7;
}
.sidebar li.active .side-menu-icon,
.sidebar li:hover .side-menu-icon {
  color: #4fc3f7;
}
.main-content { flex: 1; display: flex; flex-direction: column; }
.topbar { background: #f5f5f5; padding: 10px; display: flex; gap: 10px; }
.content { flex: 1; padding: 24px; background: #fafbfc; overflow: auto; }
.side-logo-img {
  width: 120px;
  height: 88px;
  max-width: 120px;
  border-radius: 18px;
  background: #fff;
  box-shadow: 0 2px 8px #0002;
  position: absolute;
  top: 8px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  padding: 0;
  box-sizing: border-box;
}
</style> 