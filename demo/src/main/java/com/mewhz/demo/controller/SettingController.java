package com.mewhz.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.Setting;
import com.mewhz.demo.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * setting 表对应的 Controller
 */
@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
@Tag(name = "setting 接口")
public class SettingController {

    private final SettingService settingService;

    /**
     * 分页查询列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询列表")
    public Page<Setting> queryPage(
            @Parameter(description = "当前页码") 
            @RequestParam(value = "current", defaultValue = "1") Long current,
            @Parameter(description = "每页大小") 
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return settingService.queryPage(current, size);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询")
    public Setting queryById(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return settingService.queryById(id);
    }

    /**
     * 新增数据
     */
    @PostMapping
    @Operation(summary = "新增数据")
    public boolean insert(
            @Parameter(description = "实体对象") 
            @RequestBody Setting setting) {
        return settingService.insert(setting);
    }

    /**
     * 修改数据
     */
    @PutMapping
    @Operation(summary = "修改数据")
    public boolean update(
            @Parameter(description = "实体对象") 
            @RequestBody Setting setting) {
        return settingService.update(setting);
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除数据")
    public boolean delete(
            @Parameter(description = "ID") 
            @PathVariable Long id) {
        return settingService.delete(id);
    }
} 