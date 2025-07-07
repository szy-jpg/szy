/* eslint-disable */
<template>
  <div class="login-bg d-flex align-items-center justify-content-center">
    <div class="card shadow-lg p-4 login-card">
      <div class="text-center mb-4">
        <h2 class="login-title">龙芯中科</h2>
        <div class="login-subtitle mb-2">LOONGSON TECHNOLOGY</div>
        <div class="login-mode mb-3">{{ isRegister ? '注册账号' : '登录系统' }}</div>
      </div>
      <div v-if="!isRegister">
        <div class="mb-3">
          <input v-model="username" type="text" class="form-control login-input" placeholder="用户名" />
        </div>
        <div class="mb-3 input-group">
          <input :type="showPwd ? 'text' : 'password'" v-model="password" class="form-control login-input" placeholder="密码" />
          <button class="btn btn-outline-secondary" type="button" @click="showPwd=!showPwd">
            <span v-if="showPwd">🙈</span><span v-else>👁️</span>
          </button>
        </div>
        <button class="btn btn-primary w-100 mb-2 login-btn" @click="login">登录</button>
        <div class="text-center">
          <a href="#" class="login-link" @click.prevent="isRegister=true">没有账号？注册</a>
        </div>
      </div>
      <div v-else>
        <div class="mb-3">
          <input v-model="regUsername" type="text" class="form-control login-input" placeholder="用户名" />
        </div>
        <div class="mb-3">
          <input :type="showRegPwd ? 'text' : 'password'" v-model="regPassword" class="form-control login-input" placeholder="密码" />
        </div>
        <div class="mb-3">
          <input :type="showRegPwd ? 'text' : 'password'" v-model="regPassword2" class="form-control login-input" placeholder="确认密码" />
        </div>
        <div class="mb-2 form-check">
          <input type="checkbox" class="form-check-input" id="showRegPwd" v-model="showRegPwd">
          <label class="form-check-label" for="showRegPwd">显示密码</label>
        </div>
        <button class="btn btn-primary w-100 mb-2 login-btn" @click="register">注册</button>
        <div class="text-center">
          <a href="#" class="login-link" @click.prevent="isRegister=false">已有账号？登录</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
const isRegister = ref(false);
const username = ref('');
const password = ref('');
const showPwd = ref(false);
const regUsername = ref('');
const regPassword = ref('');
const regPassword2 = ref('');
const showRegPwd = ref(false);
// eslint-disable-next-line no-undef
const emit = defineEmits(['login-success']);
const login = () => {
  if (username.value && password.value) {
    emit('login-success');
  } else {
    alert('请输入用户名和密码');
  }
};
const register = () => {
  if (!regUsername.value || !regPassword.value || !regPassword2.value) {
    alert('请填写完整信息');
    return;
  }
  if (regPassword.value !== regPassword2.value) {
    alert('两次输入的密码不一致');
    return;
  }
  // 这里可以加后端注册逻辑，当前直接注册成功并自动登录
  username.value = regUsername.value;
  password.value = regPassword.value;
  isRegister.value = false;
  emit('login-success');
};
</script>

<style scoped>
@import 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css';
.login-bg {
  min-height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, #f8fafc 0%, #e0e7ef 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.login-card {
  width: 460px;
  border-radius: 26px;
  box-shadow: 0 8px 36px #0001;
  padding: 48px 44px 40px 44px;
}
.login-logo {
  width: 72px;
  height: 72px;
  border-radius: 18px;
  background: #fff;
  box-shadow: 0 2px 8px #0002;
  object-fit: contain;
  margin-bottom: 8px;
}
.login-title {
  font-size: 32px;
  color: #ff6b81;
  font-weight: bold;
  margin-bottom: 2px;
  letter-spacing: 2px;
}
.login-subtitle {
  font-size: 18px;
  color: #888;
  margin-bottom: 2px;
  letter-spacing: 1px;
}
.login-mode {
  font-size: 22px;
  color: #222;
  font-weight: 500;
  margin-bottom: 18px;
}
.login-input {
  border-radius: 12px !important;
  font-size: 18px;
  padding: 10px 16px;
}
.login-btn {
  border-radius: 12px;
  font-size: 20px;
  padding: 10px 0;
}
.login-link {
  color: #1976d2;
  font-size: 16px;
  text-decoration: underline;
  cursor: pointer;
}
</style> 