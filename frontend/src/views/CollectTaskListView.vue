<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchCollectTasks } from '@/api/modules'
import type { CollectTask } from '@/types/api'

const loading = ref(false)
const rows = ref<CollectTask[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

async function load() {
  loading.value = true
  try {
    const data = await fetchCollectTasks(page.value, size.value)
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
        <h2>采集任务</h2>
        <p class="page-desc">1688 官方接口采集调度（骨架，对接 `/api/v1/collect-tasks`）</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无采集任务">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="sourceOfferId" label="货源编号" width="160" />
      <el-table-column prop="sourceUrl" label="货源链接" min-width="200" show-overflow-tooltip />
      <el-table-column prop="priority" label="优先级" width="90" />
      <el-table-column prop="status" label="状态" width="120" />
      <el-table-column prop="retryCount" label="重试" width="80" />
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
