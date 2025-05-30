package com.mewhz.generator.model;

import lombok.Data;

@Data
public class GeneratorConfig {
    private String projectName;  // 添加项目名字段
    private String packageName;
    private Boolean enableLombok;
    private Boolean enableKnife4j;
//    private Boolean enableValidation;
    private Boolean enableResult;
} 