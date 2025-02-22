package com.mewhz.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mewhz.demo.mapper.SettingMapper;
import com.mewhz.demo.model.Setting;
import com.mewhz.demo.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * setting 表对应的 Service 实现类
 */
@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    
    private final SettingMapper settingMapper;
    
    @Override
    public Page<Setting> queryPage(Long current, Long size) {
        // 创建分页对象
        Page<Setting> page = Page.of(current, size);
        // 执行分页查询
        return settingMapper.selectPage(page, null);
    }

    @Override
    public Setting queryById(Long id) {
        return settingMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Setting setting) {
        return settingMapper.insert(setting) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Setting setting) {
        return settingMapper.updateById(setting) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return settingMapper.deleteById(id) > 0;
    }
} 