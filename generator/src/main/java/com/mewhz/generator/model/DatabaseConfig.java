package com.mewhz.generator.model;

import lombok.Data;

import java.util.List;

@Data
public class DatabaseConfig {
    private String dbType;
    private String url;
    private String username;
    private String password;
    private List<String> tables;
    
    // 代码生成配置
    private GeneratorConfig generatorConfig;
}