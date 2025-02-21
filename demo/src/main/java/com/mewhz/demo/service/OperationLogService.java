package com.mewhz.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.OperationLog;

/**
 * operation_log 表对应的 Service 接口
 */
public interface OperationLogService {
    
    /**
     * 分页查询列表
     * @param current 当前页码
     * @param size 每页大小
     */
    Page<OperationLog> queryPage(Long current, Long size);

    /**
     * 根据ID查询
     */
    OperationLog queryById(Long id);

    /**
     * 新增数据
     */
    boolean insert(OperationLog operationLog);

    /**
     * 修改数据
     */
    boolean update(OperationLog operationLog);

    /**
     * 删除数据
     */
    boolean delete(Long id);
} 