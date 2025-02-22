package com.mewhz.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.Comment;
import com.mewhz.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * comment 表对应的 Controller
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "comment 接口")
public class CommentController {

    private final CommentService commentService;

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询列表")
    public Page<Comment> queryPage(
            @Parameter(description = "当前页码") 
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @Parameter(description = "每页大小") 
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return commentService.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Comment queryById(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return commentService.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    @Operation(summary = "新增数据")
    public boolean insert(
            @Parameter(description = "实体对象") 
            @RequestBody Comment comment) {
        return commentService.insert(comment);
    }

    /**
     * 修改数据
     */
    @PutMapping
    @Operation(summary = "修改数据")
    public boolean update(
            @Parameter(description = "实体对象") 
            @RequestBody Comment comment) {
        return commentService.update(comment);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除数据")
    public boolean delete(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return commentService.delete(id);
    }
} 