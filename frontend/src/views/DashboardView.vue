<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { healthApi } from '@/api/modules'

const health = ref('检测中…')
const backendOk = ref(false)

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
</script>

<template>
  <div class="page-card">
    <div class="page-header">
      <div>
        <h2>工作台</h2>
        <p class="page-desc">一期全自动链路：采集 → 商品库 → 类目映射 → 本地化 → 虾皮刊登 → 价库同步</p>
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
          <p>仅 1688 / 虾皮官方接口；禁止未授权爬虫。</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>本地化</template>
          <p>按店铺站点/语言转换文案；目标语言非简体时不可跳过。</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <template #header>一期渠道</template>
          <p>仅虾皮渠道适配器；凭证加密存储。</p>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.cards {
  margin-top: 16px;
}

p {
  margin: 0;
  color: #475467;
  line-height: 1.6;
  font-size: 13px;
}
</style>
