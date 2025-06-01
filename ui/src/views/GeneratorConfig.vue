<template>
  <div class="generator-config">
    <el-container class="main-container">
      <app-header />
      
      <el-main>
        <el-card class="config-card">
          <template #header>
            <div class="card-header">
              <h2>代码生成器设置</h2>
            </div>
          </template>
          
          <el-form 
            ref="formRef"
            :model="formData"
            label-width="120px"
            status-icon
          >
            <el-form-item label="项目名称" prop="projectName">
              <el-input 
                v-model="formData.projectName" 
                placeholder="请输入项目名称，例如：demo"
                style="width: 315px"
              />
            </el-form-item>

            <el-form-item label="项目包名" prop="packageName">
              <el-row :gutter="10">
                <el-col :span="18">
                  <el-input 
                    v-model="formData.packageName" 
                    placeholder="请输入项目包名，例如：com.code"
                  />
                </el-col>
                <el-col :span="6">
                  <el-checkbox v-model="formData.enablePackagePath">生成包路径</el-checkbox>
                </el-col>
              </el-row>
            </el-form-item>

            <el-row :gutter="20">
              <el-col :span="4">
                <el-form-item label="Lombok">
                  <el-switch
                    v-model="formData.enableLombok"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="开箱即用">
                  <el-switch
                    v-model="formData.enableReadyToUse"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="Result">
                  <el-switch
                    v-model="formData.enableResult"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="4">
                <el-form-item label="Knife4j">
                  <el-switch
                    v-model="formData.enableKnife4j"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item>
              <el-button type="primary" @click="saveConfig">保存设置</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
      
      <el-footer>
        <p>Copyright © 2025 Code Generator</p>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElMessage } from 'element-plus'
import AppHeader from '@/components/AppHeader.vue'

const formRef = ref<FormInstance>()
const STORAGE_KEY = 'generator-config'

const defaultFormData = {
  projectName: 'demo',
  packageName: 'com.code',
  enablePackagePath: true,
  enableLombok: true,
  enableKnife4j: false,
  enableResult: true,
  enableReadyToUse: true
}

const formData = reactive({
  ...defaultFormData
})

// 从本地存储加载配置
onMounted(() => {
  const savedConfig = localStorage.getItem(STORAGE_KEY)
  if (savedConfig) {
    const config = JSON.parse(savedConfig)
    Object.assign(formData, config)
  }
})

const saveConfig = async () => {
  try {
    // 保存到本地存储
    localStorage.setItem(STORAGE_KEY, JSON.stringify(formData))
    ElMessage.success('设置保存成功')
  } catch (error: any) {
    ElMessage.error('保存失败：' + error.message)
  }
}

const resetForm = () => {
  if (!formRef.value) return
  formRef.value.resetFields()
  Object.assign(formData, defaultFormData)
  localStorage.removeItem(STORAGE_KEY)
  ElMessage.success('设置已重置')
}
</script>

<style scoped>
.generator-config {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-color: #f5f7fa;
}

.main-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.el-main {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e7ed 100%);
}

.config-card {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  border-radius: 8px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 15px;
}

.card-header h2 {
  margin: 0;
  font-size: 18px;
  color: #303133;
  font-weight: 600;
}

.el-footer {
  text-align: center;
  color: #909399;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, .06);
}
</style> 