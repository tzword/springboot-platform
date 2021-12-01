package com.platform.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.entity.AnnSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-22
 */
public interface AnnSourceMapper extends BaseMapper<AnnSource> {

    AnnSource getAnnSourceInfo(@Param("topicId") String topicId,@Param("textid") String textid,@Param("seccode") String seccode, @Param("annTableName") String annTableName);

}
