<template>
  <div class="database-config">
    <el-container class="main-container">
      <app-header />
      
      <el-main>
        <el-card class="config-card">
          <template #header>
            <div class="card-header">
              <h2>数据库配置</h2>
            </div>
          </template>
          
          <el-form 
            ref="formRef"
            :model="formData"
            :rules="rules"
            label-width="120px"
            status-icon
          >
            <el-form-item label="数据库地址" required class="db-address-container">
              <el-row :gutter="20" class="db-address-row">
                <el-col :span="12" style="padding-left: 0px">
                  <el-form-item prop="host" class="mb-0">
                    <el-input v-model="formData.host" placeholder="主机地址">
                      <template #prepend>
                        <el-select 
                          v-model="formData.dbType" 
                          class="db-type-select" 
                          @change="updateUrl"
                          :popper-class="'db-type-select-dropdown'"
                        >
                          <el-option label="MySQL" value="mysql" />
                          <el-option label="PostgreSQL" value="postgresql" />
                        </el-select>
                      </template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item prop="port" class="mb-0">
                    <el-input v-model="formData.port" placeholder="端口号" @change="updateUrl" />
                  </el-form-item>
                </el-col>
                <el-col :span="8" style="padding-right: 0px">
                  <el-form-item prop="database" class="mb-0">
                    <el-input v-model="formData.database" placeholder="数据库名" @change="updateUrl" />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>

            <el-form-item label="用户名" prop="username">
              <el-input v-model="formData.username" placeholder="请输入数据库用户名" />
            </el-form-item>

            <el-form-item label="密码" prop="password">
              <el-input v-model="formData.password" type="password" placeholder="请输入数据库密码" show-password />
            </el-form-item>

            <el-form-item label="数据表" prop="tables">
              <el-select
                v-model="formData.tables"
                multiple
                filterable
                placeholder="请选择要生成代码的数据表"
                style="width: 100%"
              >
                <el-option
                  v-for="table in tableList"
                  :key="table"
                  :label="table"
                  :value="table"
                >
                  <span>{{ table }}</span>
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit">生成代码</el-button>
              <el-button 
                @click="testConnection" 
                :loading="loading"
              >
                测试连接
              </el-button>
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

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import AppHeader from '@/components/AppHeader.vue'

const formRef = ref<FormInstance>()

interface DatabaseConfig {
  dbType: string
  url: string
  username: string
  password: string
  tables: string[]
  host: string
  port: string
  database: string
}

const formData = reactive({
  dbType: 'mysql',
  url: '',
  username: '',
  password: '',
  tables: [] as string[],
  host: 'localhost',
  port: '3306',
  database: '',
})

const tableList = ref<string[]>([])

const rules = reactive<FormRules>({
  host: [
    { required: true, message: '请输入主机地址', trigger: 'blur' }
  ],
  port: [
    { required: true, message: '请输入端口号', trigger: 'blur' },
    { pattern: /^\d+$/, message: '端口号必须是数字', trigger: 'blur' }
  ],
  database: [
    { required: true, message: '请输入数据库名', trigger: 'blur' }
  ],
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  tables: [
    { required: true, message: '请选择至少一个数据表', trigger: 'change' },
    { type: 'array', min: 1, message: '请选择至少一个数据表', trigger: 'change' }
  ]
})

const loading = ref(false)

const updateUrl = () => {
  const { dbType, host, port, database } = formData
  if (dbType && host && port && database) {
    // 添加常用参数
    const params = new URLSearchParams({
      useUnicode: 'true',
      characterEncoding: 'utf-8',
      useSSL: 'false',
      serverTimezone: 'UTC'
    })
    
    formData.url = `jdbc:${dbType}://${host}:${port}/${database}?${params.toString()}`
  }
}

const onSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid, fields) => {
    if (valid) {
      try {
        // 确保URL是最新的
        updateUrl()
        
        // 从本地存储获取生成器配置
        const savedConfig = localStorage.getItem('generator-config')
        const generatorConfig = savedConfig ? JSON.parse(savedConfig) : {
          enableLombok: false,
          enableKnife4j: false,
          enableValidation: false,
          enableMapStruct: false
        }
        
        const config: DatabaseConfig = {
          dbType: formData.dbType,
          url: formData.url,
          username: formData.username,
          password: formData.password,
          tables: formData.tables,
          host: formData.host,
          port: formData.port,
          database: formData.database,
        }

        // 合并数据库配置和生成器配置
        const finalConfig = {
          ...config,
          generatorConfig
        }

        const response = await axios.post('/api/generator/generate', finalConfig);

        ElMessage.success('代码生成成功')
      } catch (error: any) {
        ElMessage.error('代码生成失败：' + (error.response?.data?.message || error.message))
      }
    }
  })
}

const testConnection = async () => {
  loading.value = true
  try {
    // 确保URL是最新的
    updateUrl()
    
    const config: DatabaseConfig = {
      dbType: formData.dbType,
      url: formData.url,
      username: formData.username,
      password: formData.password,
      tables: formData.tables,
      host: formData.host,
      port: formData.port,
      database: formData.database,
    }

    const response = await axios.post('/api/generator/test-connection', config)
    formData.tables = []
    tableList.value = response.data
    ElMessage.success(`数据库连接成功，获取到 ${tableList.value.length} 个表`)
  } catch (error: any) {
    tableList.value = []
    formData.tables = []
    ElMessage.error('数据库连接失败：' + (error.response?.data?.message || error.message))
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  if (!formRef.value) return
  formRef.value.resetFields()
  tableList.value = [];
}
</script>

<style scoped>
.database-config {
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

.el-row {
  width: 100%;
  margin-bottom: 0 !important;
}

.el-col {
  padding-bottom: 0;
}

.db-type-select {
  width: 110px;
  margin: 0px
}

.table-comment {
  color: #909399;
  font-size: 13px;
  /* margin-left: 10px; */
}

:deep(.el-select .el-input__wrapper) {
  width: 100%;
}

:deep(.el-form-item) {
  margin-bottom: 22px;
}

:deep(.el-input__wrapper) {
  box-shadow: 0 0 0 1px #dcdfe6 inset;
}

:deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #409EFF inset;
}

.el-footer {
  text-align: center;
  color: #909399;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, .06);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .el-main {
    padding: 10px;
  }
  
  .config-card {
    margin: 0;
  }
  
  .header-content {
    padding: 0 10px;
  }
  
  .header-content h1 {
    font-size: 20px;
  }
}

/* 调整输入框组的样式 */
:deep(.el-form-item__content) {
  display: flex;
  flex-direction: row;
  align-items: center;
}

:deep(.el-input-group__prepend) {
  padding: 0;
  background-color: transparent;
}

:deep(.no-label .el-form-item__label) {
  display: none;
}

:deep(.no-label .el-form-item__content) {
  margin-left: 0 !important;
}

/* 调整数据库地址容器 */
.db-address-container {
  margin-bottom: 22px !important;
}

.db-address-container :deep(.el-form-item__content) {
  flex: 1;
  margin-left: 0 !important;
  width: calc(100% - 120px) !important; /* 减去label的宽度 */
}

/* 调整数据库地址行的样式 */
.db-address-row {
  margin: 0 !important;
  width: 100%;
}

.db-address-row :deep(.el-form-item) {
  margin: 0;
  width: 100%;
}

.db-address-row :deep(.el-form-item__content) {
  width: 100%;
}

.db-address-row :deep(.el-input) {
  width: 100%;
}

/* 调整下拉选择器样式 */
.db-type-select {
  width: 150px !important;
}

:deep(.el-input-group__prepend) {
  padding: 0;
  background-color: transparent;
}

:deep(.el-select .el-input__wrapper) {
  box-shadow: none;
}

/* 确保所有表单项内容区域宽度一致 */
:deep(.el-form-item__content) {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: calc(100% - 120px) !important; /* 减去label的宽度 */
}

/* 确保验证消息不会导致布局混乱 */
:deep(.el-form-item__error) {
  position: absolute;
  top: 100%;
  left: 0;
  padding-top: 4px;
  margin-top: 2px;
}

/* 移除底部边距 */
.mb-0 {
  margin-bottom: 0 !important;
}

.el-divider {
  margin: 20px 0;
}

:deep(.el-divider__text) {
  font-size: 16px;
  font-weight: 500;
  color: #606266;
}
</style> 
