<template>
  <el-header>
    <div class="header-content">
      <h1>代码生成器</h1>
      <el-menu 
        mode="horizontal" 
        :router="true"
        :default-active="activeMenu"
        :ellipsis="false"
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/settings">设置</el-menu-item>
      </el-menu>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const activeMenu = ref('/')

onMounted(() => {
  activeMenu.value = route.path
})
</script>

<style scoped>
.el-header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12);
  padding: 0;
  height: 60px;
  position: relative;
  z-index: 10;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-content h1 {
  margin: 0;
  font-size: 24px;
  color: #409EFF;
}

:deep(.el-menu) {
  border-bottom: none;
}

:deep(.el-menu-item) {
  font-size: 14px;
  padding: 0 20px;
}
</style> 