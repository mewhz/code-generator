package ${package}.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package}.model.${className};

/**
 * ${tableName} 表对应的 Service 接口
 */
public interface ${className}Service {
    
    /**
     * 分页查询列表
     * @param current 当前页码
     * @param size 每页大小
     */
    Page<${className}> queryPage(Long current, Long size);

    /**
     * 根据ID查询
     */
    ${className} queryById(Long id);

    /**
     * 新增数据
     */
    boolean insert(${className} ${className?uncap_first});

    /**
     * 修改数据
     */
    boolean update(${className} ${className?uncap_first});

    /**
     * 删除数据
     */
    boolean delete(Long id);
} 