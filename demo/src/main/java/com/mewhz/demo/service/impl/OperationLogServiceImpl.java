package com.mewhz.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.mapper.OperationLogMapper;
import com.mewhz.demo.model.OperationLog;
import com.mewhz.demo.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * operation_log 表对应的 Service 实现类
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {
    
    private final OperationLogMapper operationLogMapper;
    
    @Override
    public Page<OperationLog> queryPage(Long current, Long size) {
        // 创建分页对象
        Page<OperationLog> page = Page.of(current, size);
        // 执行分页查询
        return operationLogMapper.selectPage(page, null);
    }

    @Override
    public OperationLog queryById(Long id) {
        return operationLogMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(OperationLog operationLog) {
        return operationLogMapper.insert(operationLog) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(OperationLog operationLog) {
        return operationLogMapper.updateById(operationLog) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return operationLogMapper.deleteById(id) > 0;
    }
} 