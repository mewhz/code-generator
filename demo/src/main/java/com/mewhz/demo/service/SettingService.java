package com.mewhz.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.model.Setting;

/**
 * setting 表对应的 Service 接口
 */
public interface SettingService {
    
    /**
     * 分页查询列表
     * @param current 当前页码
     * @param size 每页大小
     */
    Page<Setting> queryPage(Long current, Long size);

    /**
     * 根据ID查询
     */
    Setting queryById(Long id);

    /**
     * 新增数据
     */
    boolean insert(Setting setting);

    /**
     * 修改数据
     */
    boolean update(Setting setting);

    /**
     * 删除数据
     */
    boolean delete(Long id);
} 