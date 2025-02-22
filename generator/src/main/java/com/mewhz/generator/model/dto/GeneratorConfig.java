package com.mewhz.generator.model.dto;

import lombok.Data;

@Data
public class GeneratorConfig {
    private boolean enableLombok = true;  // 默认启用 Lombok
    private boolean enableKnife4j = false; // 默认不启用 Knife4j
} 