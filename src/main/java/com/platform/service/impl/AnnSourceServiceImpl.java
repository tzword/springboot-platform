package com.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.entity.AnnSource;
import com.platform.mapper.AnnSourceMapper;
import com.platform.service.IAnnSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-22
 */
@Service
public class AnnSourceServiceImpl extends ServiceImpl<AnnSourceMapper, AnnSource> implements IAnnSourceService {

    @Autowired
    private AnnSourceMapper annSourceMapper;

    @Override
    public AnnSource getAnnSourceInfo(Map<String, String> annMap) {
        String topicId = annMap.get("topicId");
        String textid = annMap.get("textid");
        String seccode = annMap.get("seccode");
        String annTableName = annMap.get("annTableName");
        return annSourceMapper.getAnnSourceInfo(topicId,textid,seccode,annTableName);
    }
}
