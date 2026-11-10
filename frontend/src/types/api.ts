/** 统一后端响应 */
export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

/** MyBatis-Plus 分页 */
export interface PageResult<T> {
  records: T[]
  total: number
  size: number
  current: number
  pages?: number
}

export interface LoginPayload {
  username: string
  password: string
}

export interface LoginResult {
  token: string
  userId: number
  username: string
  displayName: string
  roleCode: string
}

export interface Product {
  id: number
  sourceOfferId: string
  title: string
  status: string
  mainImageUrl?: string
  sourceCategoryId?: string
  createdAt?: string
  updatedAt?: string
}

export interface CollectTask {
  id: number
  sourceUrl?: string
  sourceOfferId?: string
  priority: number
  status: string
  errorMessage?: string
  retryCount: number
  createdAt?: string
}

export interface ChannelAccount {
  id: number
  channelCode: string
  shopName: string
  shopId?: string
  siteRegion: string
  targetLanguage: string
  status: number
}

export interface PublishTask {
  id: number
  productId: number
  channelAccountId: number
  channelCode: string
  targetLanguage: string
  status: string
  externalItemId?: string
  localizedTitle?: string
  errorMessage?: string
  createdAt?: string
}

export interface SyncLog {
  id: number
  syncType: string
  bizId?: string
  channelCode?: string
  requestSummary?: string
  resultSummary?: string
  success: number
  costMs?: number
  createdAt?: string
}

export interface SysConfig {
  id: number
  configKey: string
  configValue?: string
  remark?: string
}
