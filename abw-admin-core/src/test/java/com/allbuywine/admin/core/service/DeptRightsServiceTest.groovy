package com.allbuywine.admin.core.service

import com.allbuywine.admin.bean.domain.DeptRights
import org.junit.Test

/**
 * hufeng on 14-7-2.
 * Description：
 *
 */
class DeptRightsServiceTest extends BaseTest{
    DeptRightsService deptRightsService = getBean("deptRightsService")

    @Test
    def void testAdd() {
        deptRightsService.batchAdd(35, [32, 33])
    }

    @Test
    def void testDelete() {
        deptRightsService.delete(new DeptRights(deptId: 35, rightsId: 29))
    }

    @Test
    def void testFindRightsByDeptId() {
        println deptRightsService.findRightsByDeptId(35)
    }

    @Test
    public void testBatchDelete() {
        deptRightsService.batchDelete(35, [30, 31])
    }
}
