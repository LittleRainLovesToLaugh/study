<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchSyncLogs } from '@/api/modules'
import type { SyncLog } from '@/types/api'

const loading = ref(false)
const rows = ref<SyncLog[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

async function load() {
  loading.value = true
  try {
    const data = await fetchSyncLogs(page.value, size.value)
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
        <h2>同步日志</h2>
        <p class="page-desc">价格 / 库存 / 刊登 / 本地化 / 采集日志骨架</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无同步日志">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="syncType" label="类型" width="120" />
      <el-table-column prop="bizId" label="业务主键" width="140" />
      <el-table-column prop="channelCode" label="渠道" width="100" />
      <el-table-column prop="success" label="结果" width="90">
        <template #default="{ row }">
          <el-tag :type="row.success === 1 ? 'success' : 'danger'" size="small">
            {{ row.success === 1 ? '成功' : '失败' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="costMs" label="耗时(ms)" width="100" />
      <el-table-column prop="requestSummary" label="请求摘要" min-width="160" show-overflow-tooltip />
      <el-table-column prop="resultSummary" label="结果摘要" min-width="160" show-overflow-tooltip />
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
