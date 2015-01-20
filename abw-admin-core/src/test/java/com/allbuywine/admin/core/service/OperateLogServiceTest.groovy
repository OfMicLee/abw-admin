package com.allbuywine.admin.core.service

import com.allbuywine.admin.bean.domain.OperateLog
import com.allbuywine.admin.core.util.Pagination
import com.allbuywine.admin.core.util.PaginationSupport
import org.junit.Test

/**
 * hufeng on 14-7-2.
 * Description：
 *
 */
class OperateLogServiceTest extends BaseTest{

    OperateLogService operateLogService = getBean("operateLogService");

    @Test
    public void testQuery() {

        Pagination page =new Pagination();
        page.setiDisplayStart(0);
        page.setiDisplayLength(10);
        OperateLog operateLog = new OperateLog();
        operateLog.setActionName('test');
        PaginationSupport<OperateLog>  paginationSupport = operateLogService.query(operateLog,"","",page);
        System.out.println(paginationSupport.totalCount);

    }

    @Test
    def void testAdd() {
        OperateLog operateLog =new OperateLog (actionName:'testactionname', actionUrl:'testactionurl', params:'testparams', operatorId:'testoperatorid', operatorName:'testoperatorname', operateTime:'2014-07-02 10:40:05', operateIp:'127.0.0.1');
        operateLogService.add(operateLog);
    }
}
