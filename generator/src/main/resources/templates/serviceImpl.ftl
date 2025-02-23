package ${package}.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package}.mapper.${className}Mapper;
import ${package}.model.${className};
import ${package}.service.${className}Service;
<#if config.enableLombok>
import lombok.RequiredArgsConstructor;
</#if>
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${tableName} 表对应的 Service 实现类
 */
@Service
<#if config.enableLombok>
@RequiredArgsConstructor
</#if>
public class ${className}ServiceImpl implements ${className}Service {
    
    private final ${className}Mapper ${className?uncap_first}Mapper;
    
    <#if !config.enableLombok>
    public ${className}ServiceImpl(${className}Mapper ${className?uncap_first}Mapper) {
        this.${className?uncap_first}Mapper = ${className?uncap_first}Mapper;
    }
    </#if>
    
    @Override
    public Page<${className}> queryPage(Long current, Long size) {
        // 创建分页对象
        Page<${className}> page = Page.of(current, size);
        // 执行分页查询
        return ${className?uncap_first}Mapper.selectPage(page, null);
    }

    @Override
    public ${className} queryById(Long id) {
        return ${className?uncap_first}Mapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(${className} ${className?uncap_first}) {
        return ${className?uncap_first}Mapper.insert(${className?uncap_first}) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(${className} ${className?uncap_first}) {
        return ${className?uncap_first}Mapper.updateById(${className?uncap_first}) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return ${className?uncap_first}Mapper.deleteById(id) > 0;
    }
} 