package com.mewhz.generator.controller;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.anyline.data.datasource.DataSourceHolder;
import org.anyline.metadata.Column;
import org.anyline.metadata.Table;
import org.anyline.proxy.ServiceProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mewhz.generator.service.GeneratorService;
import com.mewhz.generator.model.dto.DatabaseConfigDTO;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.HashMap;

@RequestMapping("/generator")
@RestController
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    @PostMapping("/test-connection")
    public List<String> testConnection(@RequestBody DatabaseConfigDTO config) {
        return generatorService.testConnection(config);
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateCode(@RequestBody DatabaseConfigDTO config) {
        byte[] zipFile = generatorService.generateCode(config);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "generated-code.zip");
        
        return new ResponseEntity<>(zipFile, headers, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/")
    public void generate() {
        // 配置数据库连接信息
        // 参数说明：数据库URL、用户名、密码，使用UTF-8编码，禁用SSL，使用UTC时区
        String url = "jdbc:mysql://localhost:3306/moments?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        // 注册数据源，使用HikariCP连接池
        DataSourceHolder.reg("crm",
                "com.zaxxer.hikari.HikariDataSource",
                "com.mysql.cj.jdbc.Driver",
                url,
                "root",
                "root");

        // 获取数据库表结构信息
        Table<?> table = ServiceProxy.service("crm").metadata().table("operation_log");
        // 获取表的所有列信息并转换类型
        List<Dict> columns = new ArrayList<>();
        table.getColumns().values().forEach(column -> {
            Dict columnInfo = Dict.create()
                    .set("name", toCamelCase(column.getName()))
                    .set("javaType", getJavaType(column))
                    .set("comment", column.getComment())
                    .set("nullable", column.isNullable())
                    .set("primaryKey", column.isPrimaryKey())
                    .set("typeName", column.getTypeName());
            columns.add(columnInfo);
        });

        // 准备模板渲染所需的数据
        String basePackage = "com.mewhz.demo";
        String basePackagePath = basePackage.replace(".", "/");
        String outputPath = "C:/Document/project/code-generator/demo/src/main/java/" + basePackagePath;
        
        Dict dict = Dict.create()
                .set("package", basePackage)
                .set("className", convertToCamelCase(table.getName()))
                .set("tableName", table.getName())
                .set("columns", columns);

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
            String templateName = entry.getKey();
            String packagePath = entry.getValue();
            
            // 生成文件内容
            Template template = engine.getTemplate(templateName);
            String result = template.render(dict);
            
            // 获取文件名（去掉.ftl后缀）
            String fileName = convertToCamelCase(table.getName());
            if (templateName.startsWith("service")) {
                if (templateName.contains("Impl")) {
                    fileName += "ServiceImpl";
                } else {
                    fileName += "Service";
                }
            } else if (templateName.equals("config.ftl")) {
                fileName = "MybatisPlusConfig";  // 配置类使用固定名称
            } else if (!templateName.equals("model.ftl")) {
                fileName += templateName.substring(0, templateName.indexOf(".")).substring(0, 1).toUpperCase() 
                        + templateName.substring(0, templateName.indexOf(".")).substring(1);
            }
            
            // 创建目标目录
            String targetDir = outputPath + packagePath;
            File dir = new File(targetDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 写入文件
            String targetFile = targetDir + "/" + fileName + ".java";
            FileWriter writer = new FileWriter(targetFile);
            writer.write(result);
            writer.close();
            System.out.println("Generated: " + targetFile);
        }
    }

    /**
     * 将下划线分隔的表名转换为驼峰命名格式
     * 例如：user_info -> UserInfo
     * @param tableName 数据库表名
     * @return 转换后的驼峰命名
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
     * @param column 数据库列信息
     * @return Java类型名称
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
     * @param name 下划线命名的字符串
     * @return 驼峰命名的字符串
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
