<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchPublishTasks } from '@/api/modules'
import type { PublishTask } from '@/types/api'

const loading = ref(false)
const rows = ref<PublishTask[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

async function load() {
  loading.value = true
  try {
    const data = await fetchPublishTasks(page.value, size.value)
    rows.value = data.records || []
    total.value = data.total || 0
  } catch {
    rows.value = []
    total.value = 0
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
        <h2>刊登任务</h2>
        <p class="page-desc">自动刊登队列（刊登前须本地化；对接 `/api/v1/publish-tasks`）</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无刊登任务">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="productId" label="商品 ID" width="160" />
      <el-table-column prop="channelCode" label="渠道" width="100" />
      <el-table-column prop="targetLanguage" label="目标语言" width="110" />
      <el-table-column prop="status" label="状态" width="120" />
      <el-table-column prop="localizedTitle" label="本地化标题" min-width="180" show-overflow-tooltip />
      <el-table-column prop="externalItemId" label="虾皮商品编号" width="160" />
      <el-table-column prop="errorMessage" label="错误信息" min-width="160" show-overflow-tooltip />
    </el-table>

    <div class="pager">
      <el-pagination
        v-model:current-page="page"
        v-model:page-size="size"
        layout="total, prev, pager, next"
        :total="total"
        @current-change="load"
      />
    </div>
  </div>
</template>

<style scoped>
.pager {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
