<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const active = computed(() => route.path)

const menus = [
  { path: '/dashboard', title: '工作台', icon: 'Odometer' },
  { path: '/products', title: '商品库', icon: 'Goods' },
  { path: '/collect-tasks', title: '采集任务', icon: 'Download' },
  { path: '/channel-accounts', title: '渠道账号', icon: 'Shop' },
  { path: '/publish-tasks', title: '刊登任务', icon: 'Upload' },
  { path: '/sync-logs', title: '同步日志', icon: 'Document' },
  { path: '/system-configs', title: '系统配置', icon: 'Setting' },
]

function onLogout() {
  auth.logout()
  router.push({ name: 'login' })
}
</script>

<template>
  <el-container class="layout">
    <el-aside width="220px" class="aside">
      <div class="brand">
        <div class="brand-title">跨境运营后台</div>
        <div class="brand-sub">1688 → 虾皮</div>
      </div>
      <el-menu :default-active="active" router background-color="#1b2430" text-color="#d7dde5" active-text-color="#ffffff">
        <el-menu-item v-for="item in menus" :key="item.path" :index="item.path">
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">{{ (route.meta.title as string) || '运营后台' }}</div>
        <div class="header-right">
          <span class="user">{{ auth.user?.displayName || auth.user?.username || '未登录' }}</span>
          <el-button link type="primary" @click="onLogout">退出</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped lang="scss">
.layout {
  min-height: 100vh;
}

.aside {
  background: var(--app-sidebar);
  color: var(--app-sidebar-text);
}

.brand {
  padding: 20px 16px 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
}

.brand-title {
  font-size: 16px;
  font-weight: 700;
  color: #fff;
}

.brand-sub {
  margin-top: 4px;
  font-size: 12px;
  opacity: 0.7;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #eef0f3;
}

.header-left {
  font-size: 16px;
  font-weight: 600;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user {
  color: #475467;
  font-size: 13px;
}

.main {
  padding: 20px;
}
</style>
