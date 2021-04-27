package org.zyf.stateflowprocess.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author yanfengzhang
 * @description 业务任务管理表
 * @date 2021/4/26  23:57
 */
@Mapper
@Repository
public interface BusinessTaskManageMapper {

    int updateLastPingTime(@Param("queryTaskId") Integer queryTaskId, @Param("time") int time);
}
