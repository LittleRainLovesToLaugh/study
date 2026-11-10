import { getData, postData } from '@/api/http'
import type {
  ChannelAccount,
  CollectTask,
  LoginPayload,
  LoginResult,
  PageResult,
  Product,
  PublishTask,
  SyncLog,
  SysConfig,
} from '@/types/api'

export function loginApi(payload: LoginPayload) {
  return postData<LoginResult>('/api/v1/auth/login', payload)
}

export function meApi() {
  return getData<{ userId: number; username: string; roleCode: string }>('/api/v1/auth/me')
}

export function healthApi() {
  return getData<{ status: string; app: string }>('/api/v1/health')
}

export function fetchProducts(page = 1, size = 20) {
  return getData<PageResult<Product>>('/api/v1/products', { page, size })
}

export function fetchCollectTasks(page = 1, size = 20) {
  return getData<PageResult<CollectTask>>('/api/v1/collect-tasks', { page, size })
}

export function fetchChannelAccounts() {
  return getData<ChannelAccount[]>('/api/v1/channel-accounts')
}

export function fetchPublishTasks(page = 1, size = 20) {
  return getData<PageResult<PublishTask>>('/api/v1/publish-tasks', { page, size })
}

export function fetchSyncLogs(page = 1, size = 20, syncType?: string) {
  return getData<PageResult<SyncLog>>('/api/v1/sync-logs', { page, size, syncType })
}

export function fetchSysConfigs() {
  return getData<SysConfig[]>('/api/v1/system/configs')
}
