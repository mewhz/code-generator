package com.code.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.model.User;
import com.code.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * user 表对应的 Controller
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    public Page<User> queryPage(
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return userService.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public User queryById(
            @PathVariable Long id) {
        return userService.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    public Boolean insert(
            @RequestBody User user) {
        return userService.insert(user);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public Boolean update(
            @RequestBody User user) {
        return userService.update(user);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    public Boolean delete(
            @PathVariable Long id) {
        return userService.delete(id);
    }
} 