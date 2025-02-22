package com.mewhz.generator.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.mewhz.generator.model.dto.GeneratorConfig;
import lombok.extern.slf4j.Slf4j;
import org.anyline.data.datasource.DataSourceHolder;
import org.anyline.metadata.Column;
import org.anyline.metadata.Table;
import org.anyline.proxy.ServiceProxy;
import org.springframework.stereotype.Service;
import com.mewhz.generator.model.dto.DatabaseConfigDTO;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class GeneratorService {

    /**
     * 测试数据库连接并获取所有表信息
     */
    public List<String> testConnection(DatabaseConfigDTO config) {
        String tempDataSource = "temp_" + System.currentTimeMillis();
        try {
            // 注册数据源
            DataSourceHolder.reg(tempDataSource,
                    "com.zaxxer.hikari.HikariDataSource",
                    "com.mysql.cj.jdbc.Driver",
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword());

            // 获取所有表信息
            Map<String, Table> tables = ServiceProxy.service(tempDataSource).metadata().tables();

            List<String> result = new ArrayList<>();

            tables.forEach((name, table) -> result.add(name));

            return result;

        } catch (Exception e) {
            log.error("数据库连接测试失败", e);
            throw new RuntimeException("数据库连接失败：" + e.getMessage());
        } finally {
            try {
                // 销毁数据源
                DataSourceHolder.destroy(tempDataSource);
            } catch (Exception e) {
                log.error("关闭数据源失败", e);
            }
        }
    }

    /**
     * 生成代码并返回ZIP文件的字节数组
     */
    public byte[] generateCode(DatabaseConfigDTO config) {
        String tempDataSource = "temp_" + System.currentTimeMillis();
        try {
            // 注册数据源
            DataSourceHolder.reg(tempDataSource,
                    "com.zaxxer.hikari.HikariDataSource",
                    "com.mysql.cj.jdbc.Driver",
                    config.getUrl(),
                    config.getUsername(),
                    config.getPassword());

            // 创建一个临时目录存放生成的文件
            File tempDir = new File("temp_generated_code");
            tempDir.mkdir();

            // 获取数据库表结构信息并生成代码
            for (String tableName : config.getTables()) {
                Table<?> table = ServiceProxy.service(tempDataSource).metadata().table(tableName);
                generateCodeForTable(table, tempDir, config.getGeneratorConfig());
            }

            // 将生成的文件打包成ZIP
            byte[] zipFile = createZipFile(tempDir);

            // 清理临时文件
            deleteDirectory(tempDir);

            return zipFile;
        } catch (Exception e) {
            log.error("代码生成失败", e);
            throw new RuntimeException("代码生成失败：" + e.getMessage());
        } finally {
            try {
                // 销毁并移除数据源
                DataSourceHolder.destroy(tempDataSource);
            } catch (Exception e) {
                log.error("关闭数据源失败", e);
            }
        }
    }

    private void generateCodeForTable(Table<?> table, File outputDir, GeneratorConfig config) throws IOException {
        // 获取表的所有列信息并转换类型
        List<Dict> columns = new ArrayList<>();
        table.getColumns().values().forEach(column -> {

            Dict columnInfo = Dict.create()
                    .set("name", toCamelCase(column.getName()))
                    .set("fieldName", column.getName())
                    .set("javaType", getJavaType(column))
                    .set("comment", column.getComment())
                    .set("nullable", column.isNullable())
                    .set("primaryKey", column.isPrimaryKey() == 1)
                    .set("typeName", column.getTypeName());

            columns.add(columnInfo);
        });

        // 准备模板渲染所需的数据
        String basePackage = "com.mewhz.demo";
        String basePackagePath = basePackage.replace(".", "/");
        
        Dict dict = Dict.create()
                .set("package", basePackage)
                .set("className", convertToCamelCase(table.getName()))
                .set("tableName", table.getName())
                .set("columns", columns)
                .set("config", config);

        // 初始化模板引擎
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));

        // 定义文件生成配置
        Map<String, String> templateConfig = new HashMap<>();
        templateConfig.put("model.ftl", "/model");
        templateConfig.put("mapper.ftl", "/mapper");
        templateConfig.put("service.ftl", "/service");
        templateConfig.put("serviceImpl.ftl", "/service/impl");
        templateConfig.put("controller.ftl", "/controller");
        templateConfig.put("config.ftl", "/config");

        // 生成所有需要的文件
        for (Map.Entry<String, String> entry : templateConfig.entrySet()) {
            generateFile(engine, entry.getKey(), entry.getValue(), dict, table, outputDir, basePackagePath);
        }
    }

    private void generateFile(TemplateEngine engine, String templateName, String packagePath, 
                            Dict dict, Table<?> table, File outputDir, String basePackagePath) throws IOException {
        Template template = engine.getTemplate(templateName);
        String result = template.render(dict);

        System.out.println("result = " + result);

        String fileName = getFileName(templateName, table.getName());
        String targetDir = outputDir.getPath() + File.separator + basePackagePath + packagePath;
        
        new File(targetDir).mkdirs();
        String targetFile = targetDir + File.separator + fileName + ".java";
        
        try (FileWriter writer = new FileWriter(targetFile)) {
            writer.write(result);
        }
    }

    private byte[] createZipFile(File sourceDir) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            zipDirectory(sourceDir, sourceDir.getName(), zos);
        }
        return baos.toByteArray();
    }

    private void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            
            ZipEntry ze = new ZipEntry(parentFolder + "/" + file.getName());
            zos.putNextEntry(ze);
            
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
            }
            zos.closeEntry();
        }
    }

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    private String getFileName(String templateName, String tableName) {
        String fileName = convertToCamelCase(tableName);
        if (templateName.startsWith("service")) {
            if (templateName.contains("Impl")) {
                fileName += "ServiceImpl";
            } else {
                fileName += "Service";
            }
        } else if (templateName.equals("config.ftl")) {
            fileName = "MybatisPlusConfig";
        } else if (!templateName.equals("model.ftl")) {
            fileName += templateName.substring(0, templateName.indexOf(".")).substring(0, 1).toUpperCase() 
                    + templateName.substring(0, templateName.indexOf(".")).substring(1);
        }
        return fileName;
    }

    /**
     * 将下划线分隔的表名转换为驼峰命名格式
     * 例如：user_info -> UserInfo
     */
    private String convertToCamelCase(String tableName) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = true;

        for (char c : tableName.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(c);
                }
            }
        }

        return result.toString();
    }

    /**
     * 根据列信息获取对应的Java类型
     */
    private String getJavaType(Column column) {
        String type = column.getTypeName().toLowerCase();
        
        if (type.contains("varchar") || type.contains("text") || type.contains("char")) {
            return "String";
        } else if (type.contains("bigint")) {
            return "Long";
        } else if (type.contains("int")) {
            return "Integer";
        } else if (type.contains("datetime") || type.contains("timestamp")) {
            return "LocalDateTime";
        } else if (type.contains("date")) {
            return "LocalDate";
        } else if (type.contains("decimal") || type.contains("numeric")) {
            return "BigDecimal";
        } else if (type.contains("bit") || type.contains("boolean")) {
            return "Boolean";
        } else if (type.contains("float")) {
            return "Float";
        } else if (type.contains("double")) {
            return "Double";
        }
        return "String";  // 默认使用String类型
    }

    /**
     * 将下划线命名转换为驼峰命名（首字母小写）
     * 例如：user_name -> userName
     */
    private String toCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }

        return result.toString();
    }
}
