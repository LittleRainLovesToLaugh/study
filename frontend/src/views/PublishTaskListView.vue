<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { createPublishTask, fetchPublishTasks, runPublishTask } from '@/api/modules'
import type { PublishTask } from '@/types/api'

const loading = ref(false)
const runningId = ref<number | null>(null)
const rows = ref<PublishTask[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

const form = reactive({
  productId: undefined as number | undefined,
  channelAccountId: undefined as number | undefined,
})

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

async function onCreate() {
  if (!form.productId || !form.channelAccountId) {
    ElMessage.warning('请填写商品 ID 与渠道账号 ID')
    return
  }
  await createPublishTask({
    productId: form.productId,
    channelAccountId: form.channelAccountId,
  })
  ElMessage.success('刊登任务已创建')
  await load()
}

async function onRun(row: PublishTask) {
  runningId.value = row.id
  try {
    const result = await runPublishTask(row.id)
    ElMessage.success(`刊登完成：${result.externalItemId}`)
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
        <h2>刊登任务</h2>
        <p class="page-desc">执行时：按店铺语言本地化 → 虾皮渠道适配器 Stub → 写同步日志</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-form :inline="true" class="toolbar" @submit.prevent>
      <el-form-item label="商品 ID">
        <el-input-number v-model="form.productId" :controls="false" placeholder="采集后的商品" />
      </el-form-item>
      <el-form-item label="渠道账号 ID">
        <el-input-number v-model="form.channelAccountId" :controls="false" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onCreate">创建刊登任务</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无刊登任务">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="productId" label="商品 ID" width="160" />
      <el-table-column prop="channelCode" label="渠道" width="100" />
      <el-table-column prop="targetLanguage" label="目标语言" width="110" />
      <el-table-column prop="status" label="状态" width="120" />
      <el-table-column prop="localizedTitle" label="本地化标题" min-width="180" show-overflow-tooltip />
      <el-table-column prop="externalItemId" label="虾皮商品编号" width="160" />
      <el-table-column prop="errorMessage" label="错误信息" min-width="140" show-overflow-tooltip />
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="{ row }">
          <el-button
            link
            type="primary"
            :loading="runningId === row.id"
            :disabled="row.status === 'SUCCESS' || row.status === 'PUBLISHING' || row.status === 'LOCALIZING'"
            @click="onRun(row as PublishTask)"
          >
            执行刊登
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
