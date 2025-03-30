package com.mewhz.generator.model.dto;

import lombok.Data;

@Data
public class GeneratorConfig {
    private String projectName;  // 添加项目名字段
    private Boolean enableLombok;
    private Boolean enableKnife4j;
    private Boolean enableValidation;
    private Boolean enableResult;
} 