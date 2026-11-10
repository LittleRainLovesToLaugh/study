import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
    meta: { public: true, title: '登录' },
  },
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/views/DashboardView.vue'),
        meta: { title: '工作台' },
      },
      {
        path: 'products',
        name: 'products',
        component: () => import('@/views/ProductListView.vue'),
        meta: { title: '商品库' },
      },
      {
        path: 'collect-tasks',
        name: 'collect-tasks',
        component: () => import('@/views/CollectTaskListView.vue'),
        meta: { title: '采集任务' },
      },
      {
        path: 'channel-accounts',
        name: 'channel-accounts',
        component: () => import('@/views/ChannelAccountListView.vue'),
        meta: { title: '渠道账号' },
      },
      {
        path: 'publish-tasks',
        name: 'publish-tasks',
        component: () => import('@/views/PublishTaskListView.vue'),
        meta: { title: '刊登任务' },
      },
      {
        path: 'sync-logs',
        name: 'sync-logs',
        component: () => import('@/views/SyncLogListView.vue'),
        meta: { title: '同步日志' },
      },
      {
        path: 'system-configs',
        name: 'system-configs',
        component: () => import('@/views/SystemConfigView.vue'),
        meta: { title: '系统配置' },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to) => {
  const auth = useAuthStore()
  if (!to.meta.public && !auth.isLoggedIn) {
    return { name: 'login', query: { redirect: to.fullPath } }
  }
  if (to.name === 'login' && auth.isLoggedIn) {
    return { name: 'dashboard' }
  }
  return true
})

export default router
