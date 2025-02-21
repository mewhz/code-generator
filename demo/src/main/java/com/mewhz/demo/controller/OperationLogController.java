package com.mewhz.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.OperationLog;
import com.mewhz.demo.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * operation_log 表对应的 Controller
 */
@RestController
@RequestMapping("/operationLog")
@RequiredArgsConstructor
public class OperationLogController {

    private final OperationLogService operationLogService;

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    public Page<OperationLog> queryPage(
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return operationLogService.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public OperationLog queryById(@PathVariable Long id) {
        return operationLogService.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    public boolean insert(@RequestBody OperationLog operationLog) {
        return operationLogService.insert(operationLog);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public boolean update(@RequestBody OperationLog operationLog) {
        return operationLogService.update(operationLog);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return operationLogService.delete(id);
    }
} 