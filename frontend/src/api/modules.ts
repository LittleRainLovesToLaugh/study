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

export function createCollectTask(payload: { sourceOfferId: string; sourceUrl?: string; priority?: number }) {
  return postData<CollectTask>('/api/v1/collect-tasks', payload)
}

export function runCollectTask(id: number) {
  return postData<Record<string, unknown>>(`/api/v1/collect-tasks/${id}/run`)
}

export function fetchChannelAccounts() {
  return getData<ChannelAccount[]>('/api/v1/channel-accounts')
}

export function createChannelAccount(payload: {
  shopName: string
  shopId?: string
  siteRegion: string
  targetLanguage: string
  partnerId?: string
  partnerKey?: string
  accessToken?: string
}) {
  return postData<ChannelAccount>('/api/v1/channel-accounts', payload)
}

export function fetchPublishTasks(page = 1, size = 20) {
  return getData<PageResult<PublishTask>>('/api/v1/publish-tasks', { page, size })
}

export function createPublishTask(payload: { productId: number; channelAccountId: number }) {
  return postData<PublishTask>('/api/v1/publish-tasks', payload)
}

export function runPublishTask(id: number) {
  return postData<Record<string, unknown>>(`/api/v1/publish-tasks/${id}/run`)
}

export function fetchSyncLogs(page = 1, size = 20, syncType?: string) {
  return getData<PageResult<SyncLog>>('/api/v1/sync-logs', { page, size, syncType })
}

export function fetchSysConfigs() {
  return getData<SysConfig[]>('/api/v1/system/configs')
}

export function triggerPriceStockSync() {
  return postData<Record<string, unknown>>('/api/v1/sync/price-stock')
}

export function runPipelineDemo(payload?: { sourceOfferId?: string; targetLanguage?: string; siteRegion?: string }) {
  return postData<Record<string, unknown>>('/api/v1/pipeline/demo-run', payload || {})
}
