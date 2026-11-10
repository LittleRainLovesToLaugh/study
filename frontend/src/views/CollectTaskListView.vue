<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createCollectTask, fetchCollectTasks, runCollectTask } from '@/api/modules'
import type { CollectTask } from '@/types/api'

const loading = ref(false)
const runningId = ref<number | null>(null)
const rows = ref<CollectTask[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

const form = reactive({
  sourceOfferId: '',
  sourceUrl: '',
  priority: 0,
})

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

async function onCreate() {
  if (!form.sourceOfferId.trim()) {
    ElMessage.warning('请填写货源编号')
    return
  }
  await createCollectTask({
    sourceOfferId: form.sourceOfferId.trim(),
    sourceUrl: form.sourceUrl || undefined,
    priority: form.priority,
  })
  ElMessage.success('采集任务已创建')
  form.sourceOfferId = ''
  form.sourceUrl = ''
  await load()
}

async function onRun(row: CollectTask) {
  runningId.value = row.id
  try {
    const result = await runCollectTask(row.id)
    ElMessage.success(`采集完成，商品 ID：${result.productId}`)
    await load()
  } finally {
    runningId.value = null
  }
}

onMounted(load)
</script>

<template>
  <div class="page-card">
    <div class="page-header">
      <div>
        <h2>采集任务</h2>
        <p class="page-desc">创建后执行：调用 1688 官方客户端 Stub → 入库商品/规格/图片</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-form :inline="true" class="toolbar" @submit.prevent>
      <el-form-item label="货源编号">
        <el-input v-model="form.sourceOfferId" placeholder="1688 offerId" clearable />
      </el-form-item>
      <el-form-item label="链接">
        <el-input v-model="form.sourceUrl" placeholder="可选" clearable style="width: 260px" />
      </el-form-item>
      <el-form-item label="优先级">
        <el-input-number v-model="form.priority" :min="0" :max="100" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onCreate">创建任务</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无采集任务">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="sourceOfferId" label="货源编号" width="160" />
      <el-table-column prop="sourceUrl" label="货源链接" min-width="180" show-overflow-tooltip />
      <el-table-column prop="priority" label="优先级" width="90" />
      <el-table-column prop="status" label="状态" width="120" />
      <el-table-column prop="retryCount" label="重试" width="80" />
      <el-table-column prop="errorMessage" label="错误信息" min-width="140" show-overflow-tooltip />
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <el-button
            link
            type="primary"
            :loading="runningId === row.id"
            :disabled="row.status === 'RUNNING'"
            @click="onRun(row as CollectTask)"
          >
            执行采集
          </el-button>
        </template>
      </el-table-column>
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
.toolbar {
  margin-bottom: 12px;
}
.pager {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
