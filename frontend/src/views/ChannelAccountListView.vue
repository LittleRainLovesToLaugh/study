<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchChannelAccounts } from '@/api/modules'
import type { ChannelAccount } from '@/types/api'

const loading = ref(false)
const rows = ref<ChannelAccount[]>([])

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

onMounted(load)
</script>

<template>
  <div class="page-card">
    <div class="page-header">
      <div>
        <h2>渠道账号</h2>
        <p class="page-desc">一期仅虾皮店铺；展示站点/地区与目标语言（凭证密文不下发）</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

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
