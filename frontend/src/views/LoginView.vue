<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const auth = useAuthStore()
const router = useRouter()
const route = useRoute()
const loading = ref(false)
const formRef = ref<FormInstance>()

const form = reactive({
  username: 'admin',
  password: 'Admin@123456',
})

const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

async function onSubmit() {
  await formRef.value?.validate()
  loading.value = true
  try {
    await auth.login({ username: form.username, password: form.password })
    ElMessage.success('登录成功')
    const redirect = (route.query.redirect as string) || '/dashboard'
    await router.replace(redirect)
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-page">
    <div class="login-panel">
      <h1>跨境运营后台</h1>
      <p class="hint">1688 官方采集 → 按站点本地化 → 虾皮自动刊登</p>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @keyup.enter="onSubmit">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="管理员账号" clearable />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="登录密码" />
        </el-form-item>
        <el-button type="primary" class="submit" :loading="loading" @click="onSubmit">登录</el-button>
      </el-form>
      <p class="tip">默认开发账号：admin / Admin@123456（请对接后端后修改）</p>
    </div>
  </div>
</template>

<style scoped lang="scss">
.login-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  background:
    radial-gradient(circle at 20% 20%, rgba(47, 111, 237, 0.18), transparent 40%),
    radial-gradient(circle at 80% 0%, rgba(16, 185, 129, 0.12), transparent 35%),
    linear-gradient(160deg, #eef3fb, #f7f8fa 55%, #edf2f7);
}

.login-panel {
  width: min(420px, 92vw);
  background: #fff;
  border-radius: 14px;
  padding: 32px 28px 24px;
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.08);
}

h1 {
  margin: 0;
  font-size: 24px;
}

.hint {
  margin: 8px 0 24px;
  color: #667085;
  font-size: 13px;
  line-height: 1.5;
}

.submit {
  width: 100%;
  margin-top: 8px;
}

.tip {
  margin: 16px 0 0;
  color: #98a2b3;
  font-size: 12px;
}
</style>
