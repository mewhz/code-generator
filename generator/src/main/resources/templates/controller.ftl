package ${package}.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package}.model.${className};
import ${package}.service.${className}Service;
<#if config.enableResult>
import ${package}.common.Result;
</#if>
<#if config.enableLombok>
import lombok.RequiredArgsConstructor;
</#if>
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
<#if config.enableLombok>
@RequiredArgsConstructor
</#if>
<#if config.enableKnife4j>
@Tag(name = "${tableName} 接口")
</#if>
public class ${className}Controller {

    private final ${className}Service ${className?uncap_first}Service;

    <#if !config.enableLombok>
    public ${className}Controller(${className}Service ${className?uncap_first}Service) {
        this.${className?uncap_first}Service = ${className?uncap_first}Service;
    }
    </#if>

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    <#if config.enableKnife4j>
    @Operation(summary = "分页查询列表")
    </#if>
    <#if config.enableResult>
    public Result<Page<${className}>> queryPage(
    <#else>
    public Page<${className}> queryPage(
    </#if>
            <#if config.enableKnife4j>
            @Parameter(description = "当前页码") 
            </#if>
            @RequestParam(value = "current", defaultValue = "1") Long current,
            <#if config.enableKnife4j>
            @Parameter(description = "每页大小") 
            </#if>
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        <#if config.enableResult>
        return Result.success(${className?uncap_first}Service.queryPage(current, size));
        <#else>
        return ${className?uncap_first}Service.queryPage(current, size);
        </#if>
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    <#if config.enableKnife4j>
    @Operation(summary = "根据ID查询")
    </#if>
    <#if config.enableResult>
    public Result<${className}> queryById(
    <#else>
    public ${className} queryById(
    </#if>
            <#if config.enableKnife4j>
            @Parameter(description = "ID") 
            </#if>
            @PathVariable Long id) {
        <#if config.enableResult>
        return Result.success(${className?uncap_first}Service.queryById(id));
        <#else>
        return ${className?uncap_first}Service.queryById(id);
        </#if>
    }

    /**
     * 新增数据
     */
    @PostMapping
    <#if config.enableKnife4j>
    @Operation(summary = "新增数据")
    </#if>
    <#if config.enableResult>
    public Result<Boolean> insert(
    <#else>
    public Boolean insert(
    </#if>
            <#if config.enableKnife4j>
            @Parameter(description = "实体对象") 
            </#if>
            @RequestBody ${className} ${className?uncap_first}) {
        <#if config.enableResult>
        return Result.success(${className?uncap_first}Service.insert(${className?uncap_first}));
        <#else>
        return ${className?uncap_first}Service.insert(${className?uncap_first});
        </#if>
    }

    /**
     * 修改数据
     */
    @PutMapping
    <#if config.enableKnife4j>
    @Operation(summary = "修改数据")
    </#if>
    <#if config.enableResult>
    public Result<Boolean> update(
    <#else>
    public Boolean update(
    </#if>
            <#if config.enableKnife4j>
            @Parameter(description = "实体对象") 
            </#if>
            @RequestBody ${className} ${className?uncap_first}) {
        <#if config.enableResult>
        return Result.success(${className?uncap_first}Service.update(${className?uncap_first}));
        <#else>
        return ${className?uncap_first}Service.update(${className?uncap_first});
        </#if>
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    <#if config.enableKnife4j>
    @Operation(summary = "删除数据")
    </#if>
    <#if config.enableResult>
    public Result<Boolean> delete(
    <#else>
    public Boolean delete(
    </#if>
            <#if config.enableKnife4j>
            @Parameter(description = "ID") 
            </#if>
            @PathVariable Long id) {
        <#if config.enableResult>
        return Result.success(${className?uncap_first}Service.delete(id));
        <#else>
        return ${className?uncap_first}Service.delete(id);
        </#if>
    }
} 