package com.exam.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.SystemConfig;
import com.exam.mapper.SystemConfigMapper;
import com.exam.service.SystemConfigService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {
    @Override
    public String getConfig(String key) {
        SystemConfig config = getOne(new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, key));
        return config != null ? config.getConfigValue() : null;
    }
    @Override
    public void setConfig(String key, String value) {
        SystemConfig config = getOne(new LambdaQueryWrapper<SystemConfig>().eq(SystemConfig::getConfigKey, key));
        if (config == null) { config = new SystemConfig(); config.setConfigKey(key); }
        config.setConfigValue(value);
        saveOrUpdate(config);
    }
    @Override
    public Map<String, List<SystemConfig>> getConfigByGroup() {
        List<SystemConfig> list = list();
        return list.stream().collect(Collectors.groupingBy(c -> c.getConfigGroup() != null ? c.getConfigGroup() : "default"));
    }
}
