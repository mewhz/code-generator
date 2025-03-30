### 更新记录
 - v1.3.0 -2025-03-30
   - 修复 Chrome 无法下载的问题
   - 修复下载时会出现时间戳偏差的问题
   - 新增自定义项目名，默认为 demo
   - 使用项目名作为生成后下载的文件夹名
 - v1.2.0 -2025-02-23
   - 修复不启用 Lombok 时，Controller 和 ServiceImpl 中依然调用了 Lombok 注解的问题
   - Lombok 默认启用
   - 新增是否启用 Result
   - 新增生成时附带启动类和配置文件
 - v1.1.0 -2025-02-23
   - 注销已经使用过的数据源
   - 新增是否启用 Lombok
   - 下载文件名修改为包含时间戳
   - 新增是否启用 Knife4j
 - v1.0.0 -2025-02-21
   - 根据数据库信息生成 CRUD
   - 默认使用 MyBatis-Plus、Lombok
   - 生成 Controller、Service、Mapper 和 Model
   - 生成 MyBatis-Plus 的 Config 配置文件