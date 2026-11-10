<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchSysConfigs } from '@/api/modules'
import type { SysConfig } from '@/types/api'

const loading = ref(false)
const rows = ref<SysConfig[]>([])

async function load() {
  loading.value = true
  try {
    rows.value = (await fetchSysConfigs()) || []
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
        <h2>系统配置</h2>
        <p class="page-desc">汇率 / 加价 / 翻译提供方等配置（骨架只读列表）</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无配置">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="configKey" label="配置键" min-width="180" />
      <el-table-column prop="configValue" label="配置值" min-width="180" show-overflow-tooltip />
      <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
    </el-table>
  </div>
</template>
