<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { fetchProducts } from '@/api/modules'
import type { Product } from '@/types/api'

const loading = ref(false)
const rows = ref<Product[]>([])
const total = ref(0)
const page = ref(1)
const size = ref(20)

async function load() {
  loading.value = true
  try {
    const data = await fetchProducts(page.value, size.value)
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
        <h2>商品库</h2>
        <p class="page-desc">标准化入库商品列表（骨架，对接 `/api/v1/products`）</p>
      </div>
      <el-button @click="load">刷新</el-button>
    </div>

    <el-table v-loading="loading" :data="rows" stripe empty-text="暂无商品">
      <el-table-column prop="id" label="ID" width="160" />
      <el-table-column prop="sourceOfferId" label="货源编号" width="160" />
      <el-table-column prop="title" label="标题" min-width="220" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="120" />
      <el-table-column prop="sourceCategoryId" label="源类目" width="140" />
      <el-table-column prop="createdAt" label="创建时间" width="180" />
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
