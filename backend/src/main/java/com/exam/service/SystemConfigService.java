package com.exam.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.SystemConfig;
import java.util.List;
import java.util.Map;
public interface SystemConfigService extends IService<SystemConfig> {
    String getConfig(String key);
    void setConfig(String key, String value);
    Map<String, List<SystemConfig>> getConfigByGroup();
}
