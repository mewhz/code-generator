package com.mewhz.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mewhz.demo.model.Setting;
import org.apache.ibatis.annotations.Mapper;

/**
 * setting 表对应的 Mapper 接口
 */
@Mapper
public interface SettingMapper extends BaseMapper<Setting> {
} 