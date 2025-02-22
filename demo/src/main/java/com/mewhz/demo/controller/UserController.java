package com.mewhz.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.User;
import com.mewhz.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * user 表对应的 Controller
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user 接口")
public class UserController {

    private final UserService userService;

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询列表")
    public Page<User> queryPage(
            @Parameter(description = "当前页码") 
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @Parameter(description = "每页大小") 
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return userService.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public User queryById(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return userService.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    @Operation(summary = "新增数据")
    public boolean insert(
            @Parameter(description = "实体对象") 
            @RequestBody User user) {
        return userService.insert(user);
    }

    /**
     * 修改数据
     */
    @PutMapping
    @Operation(summary = "修改数据")
    public boolean update(
            @Parameter(description = "实体对象") 
            @RequestBody User user) {
        return userService.update(user);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除数据")
    public boolean delete(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return userService.delete(id);
    }
} 