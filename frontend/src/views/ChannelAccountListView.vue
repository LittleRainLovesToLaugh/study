<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createChannelAccount, fetchChannelAccounts } from '@/api/modules'
import type { ChannelAccount } from '@/types/api'

const loading = ref(false)
const rows = ref<ChannelAccount[]>([])

const form = reactive({
  shopName: '台湾演示店',
  shopId: 'SHOP-TW-001',
  siteRegion: 'TW',
  targetLanguage: 'zh-TW',
})

async function load() {
  loading.value = true
  try {
    rows.value = (await fetchChannelAccounts()) || []
  } catch {
    rows.value = []
  } finally {
    loading.value = false
  }
}

async function onCreate() {
  if (!form.shopName || !form.siteRegion || !form.targetLanguage) {
    ElMessage.warning('请填写店铺、站点与目标语言')
    return
  }
  await createChannelAccount({
    ...form,
    partnerId: 'demo-partner',
    partnerKey: 'demo-partner-key',
    accessToken: 'demo-access-token',
  })
  ElMessage.success('渠道账号已创建（凭证已加密入库）')
  await load()
}

onMounted(load)
</script>

<template>
  <div class="page-card">
    <div class="page-header">
      <div>
        <h2>渠道账号</h2>
        <p class="page-desc">一期仅虾皮；站点/地区 + 目标语言决定本地化；凭证密文不下发</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-form :inline="true" class="toolbar" @submit.prevent>
      <el-form-item label="店铺名">
        <el-input v-model="form.shopName" />
      </el-form-item>
      <el-form-item label="店铺编号">
        <el-input v-model="form.shopId" />
      </el-form-item>
      <el-form-item label="站点">
        <el-select v-model="form.siteRegion" style="width: 100px">
          <el-option label="TW" value="TW" />
          <el-option label="MY" value="MY" />
          <el-option label="SG" value="SG" />
          <el-option label="PH" value="PH" />
          <el-option label="VN" value="VN" />
          <el-option label="TH" value="TH" />
          <el-option label="ID" value="ID" />
        </el-select>
      </el-form-item>
      <el-form-item label="语言">
        <el-select v-model="form.targetLanguage" style="width: 120px">
          <el-option label="zh-TW" value="zh-TW" />
          <el-option label="en" value="en" />
          <el-option label="vi" value="vi" />
          <el-option label="th" value="th" />
          <el-option label="zh-CN" value="zh-CN" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onCreate">创建账号</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无渠道账号">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="channelCode" label="渠道" width="100" />
      <el-table-column prop="shopName" label="店铺名称" min-width="160" />
      <el-table-column prop="shopId" label="店铺编号" width="140" />
      <el-table-column prop="siteRegion" label="站点/地区" width="120" />
      <el-table-column prop="targetLanguage" label="目标语言" width="120" />
      <el-table-column prop="status" label="状态" width="90">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">
            {{ row.status === 1 ? '启用' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.toolbar {
  margin-bottom: 12px;
}
</style>
