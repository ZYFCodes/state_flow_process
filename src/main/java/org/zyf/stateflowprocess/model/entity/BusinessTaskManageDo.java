package org.zyf.stateflowprocess.model.entity;

import lombok.Data;

/**
 * @author yanfengzhang
 * @description 任务执行数据存储实体
 * @date 2021/4/26  21:48
 */
@Data
public class BusinessTaskManageDo {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 查询条件
     */
    private String queryCondition;
    /**
     * 查询语句
     */
    private String queryStatement;
    /**
     * 查询语句的md5，用于校验是否有一样的查询存在
     */
    private String queryStatementMd5;
    /**
     * 查询创建者
     */
    private String createUserName;
    /**
     * 状态，1：新建；2：查询中；3：查询完成结果保存中；4：结果保存完 成；0：已取消
     */
    private int status;
    /**
     * 任务执行方案id
     */
    private String actionPlainId;
    /**
     * 场景id --- 一个任务只能对于指定的一个场景（场景是固定的）
     */
    private int scenesId;
    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 修改时间
     */
    private Integer updateTime;
    /**
     * 执行节点的标识，暂可以用1表示，非0既代表有节点在运行任务
     */
    private long execNodeId;
    /**
     * 执行节点最后一次心跳时间
     */
    private int lastPingTime;
    /**
     * 版本
     */
    private int version;

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof BusinessTaskManageDo) {
            BusinessTaskManageDo other = (BusinessTaskManageDo) o;
            return this.id.equals(other.id) && this.status == other.status;
        }
        return false;
    }
}
