<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { healthApi, runPipelineDemo, triggerPriceStockSync } from '@/api/modules'

const health = ref('检测中…')
const backendOk = ref(false)
const demoLoading = ref(false)
const syncLoading = ref(false)
const lastDemo = ref('')

onMounted(async () => {
  try {
    const data = await healthApi()
    health.value = `${data.app} / ${data.status}`
    backendOk.value = true
  } catch {
    health.value = '后端未连通（请先启动 backend + docker compose）'
    backendOk.value = false
  }
})

async function onDemo() {
  demoLoading.value = true
  try {
    const result = await runPipelineDemo({ targetLanguage: 'zh-TW', siteRegion: 'TW' })
    lastDemo.value = JSON.stringify(result, null, 2)
    ElMessage.success('演示流水线已跑通：采集 → 本地化 → 刊登')
  } finally {
    demoLoading.value = false
  }
}

async function onSync() {
  syncLoading.value = true
  try {
    const result = await triggerPriceStockSync()
    ElMessage.success(`价库同步完成：${JSON.stringify(result)}`)
  } finally {
    syncLoading.value = false
  }
}
</script>

<template>
  <div class="page-card">
    <div class="page-header">
      <div>
        <h2>工作台</h2>
        <p class="page-desc">一期全自动链路：采集 → 商品库 → 类目映射 → 本地化 → 虾皮刊登 → 价库同步</p>
      </div>
      <div class="actions">
        <el-button type="primary" :loading="demoLoading" :disabled="!backendOk" @click="onDemo">
          一键演示流水线
        </el-button>
        <el-button :loading="syncLoading" :disabled="!backendOk" @click="onSync">触发价库同步</el-button>
      </div>
    </div>

    <el-alert
      :title="backendOk ? '后端健康检查通过' : '后端暂不可用'"
      :type="backendOk ? 'success' : 'warning'"
      :description="health"
      show-icon
      :closable="false"
    />

    <el-row :gutter="16" class="cards">
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>合规约束</template>
          <p>仅 1688 / 虾皮官方接口 Stub；禁止未授权爬虫。</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>本地化</template>
          <p>按店铺站点/语言转换文案；zh-TW 走简易简繁，其他语言占位前缀。</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>调度</template>
          <p>价库同步：REST 手动触发 + Spring 定时占位；XXL-JOB 见 deploy 说明。</p>
        </el-card>
      </el-col>
    </el-row>

    <el-card v-if="lastDemo" class="demo-result" shadow="never">
      <template #header>最近一次演示结果</template>
      <pre>{{ lastDemo }}</pre>
    </el-card>
  </div>
</template>

<style scoped>
.actions {
  display: flex;
  gap: 8px;
}
.cards {
  margin-top: 16px;
}
p {
  margin: 0;
  color: #475467;
  line-height: 1.6;
  font-size: 13px;
}
.demo-result {
  margin-top: 16px;
}
pre {
  margin: 0;
  white-space: pre-wrap;
  word-break: break-all;
  font-size: 12px;
  color: #344054;
}
</style>
