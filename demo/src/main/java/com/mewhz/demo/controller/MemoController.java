package com.mewhz.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.Memo;
import com.mewhz.demo.service.MemoService;
import com.mewhz.demo.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * memo 表对应的 Controller
 */
@RestController
@RequestMapping("/memo")
@RequiredArgsConstructor
@Tag(name = "memo 接口")
public class MemoController {

    private final MemoService memoService;


    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询列表")
    public Result<Page<Memo>> queryPage(
            @Parameter(description = "当前页码") 
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @Parameter(description = "每页大小") 
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return Result.success(memoService.queryPage(current, size));
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Result<Memo> queryById(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return Result.success(memoService.queryById(id));
    }

    /**
     * 新增数据
     */
    @PostMapping
    @Operation(summary = "新增数据")
    public Result<Boolean> insert(
            @Parameter(description = "实体对象") 
            @RequestBody Memo memo) {
        return Result.success(memoService.insert(memo));
    }

    /**
     * 修改数据
     */
    @PutMapping
    @Operation(summary = "修改数据")
    public Result<Boolean> update(
            @Parameter(description = "实体对象") 
            @RequestBody Memo memo) {
        return Result.success(memoService.update(memo));
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除数据")
    public Result<Boolean> delete(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return Result.success(memoService.delete(id));
    }
} 