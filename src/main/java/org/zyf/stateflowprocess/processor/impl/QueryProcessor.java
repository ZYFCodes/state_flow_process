package org.zyf.stateflowprocess.processor.impl;

import org.zyf.stateflowprocess.common.annotation.Dispatch;
import org.zyf.stateflowprocess.common.utils.ZyfBeanFactoryUtils;
import org.zyf.stateflowprocess.model.entity.BusinessTaskManageDo;
import org.zyf.stateflowprocess.processor.AbstractProcessor;
import org.zyf.stateflowprocess.service.BusinessTaskManageService;

/**
 * @author yanfengzhang
 * @description
 * @date 2021/4/26  22:40
 */
@Dispatch(table = -1, status = {1})
public class QueryProcessor extends AbstractProcessor {
    private BusinessTaskManageService businessTaskManageService;

    public QueryProcessor(BusinessTaskManageDo value) {
        super(value);
        businessTaskManageService = ZyfBeanFactoryUtils.getBean(BusinessTaskManageService.class);
    }

    @Override
    public void actualProcess(BusinessTaskManageDo value) {
        //businessTaskManageService.startQueryBusinessTaskManage(value.getId());
    }

    @Override
    protected void end(BusinessTaskManageDo value) {

    }
}
