package ${package}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package}.model.${className};
import ${package}.service.${className}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
<#if config.enableKnife4j>
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
</#if>

/**
 * ${tableName} 表对应的 Controller
 */
@RestController
@RequestMapping("/${className?uncap_first}")
@RequiredArgsConstructor
<#if config.enableKnife4j>
@Tag(name = "${tableName} 接口")
</#if>
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    <#if config.enableKnife4j>
    @Operation(summary = "分页查询列表")
    </#if>
    public Page<${className}> queryPage(
            <#if config.enableKnife4j>
            @Parameter(description = "当前页码") 
            </#if>
            @RequestParam(value = "current", defaultValue = "1") Long current,
            <#if config.enableKnife4j>
            @Parameter(description = "每页大小") 
            </#if>
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return ${className?uncap_first}Service.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    <#if config.enableKnife4j>
    @Operation(summary = "根据ID查询")
    </#if>
    public ${className} queryById(
            <#if config.enableKnife4j>
            @Parameter(description = "ID") 
            </#if>
            @PathVariable Long id) {
        return ${className?uncap_first}Service.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    <#if config.enableKnife4j>
    @Operation(summary = "新增数据")
    </#if>
    public boolean insert(
            <#if config.enableKnife4j>
            @Parameter(description = "实体对象") 
            </#if>
            @RequestBody ${className} ${className?uncap_first}) {
        return ${className?uncap_first}Service.insert(${className?uncap_first});
    }

    /**
     * 修改数据
     */
    @PutMapping
    <#if config.enableKnife4j>
    @Operation(summary = "修改数据")
    </#if>
    public boolean update(
            <#if config.enableKnife4j>
            @Parameter(description = "实体对象") 
            </#if>
            @RequestBody ${className} ${className?uncap_first}) {
        return ${className?uncap_first}Service.update(${className?uncap_first});
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    <#if config.enableKnife4j>
    @Operation(summary = "删除数据")
    </#if>
    public boolean delete(
            <#if config.enableKnife4j>
            @Parameter(description = "ID") 
            </#if>
            @PathVariable Long id) {
        return ${className?uncap_first}Service.delete(id);
    }
} 