package com.allbuywine.admin.core.service

import com.allbuywine.admin.bean.domain.Dept
import org.junit.Test

/**
 * hufeng on 14-7-1.
 * Description：部门服务Groovy测试
 *
 */
class DeptServiceTest extends BaseTest {
    DeptService deptService = getBean("deptService")


    @Test
    def void testAdd() {
        def dept = new Dept(name: "test", description: "test")
        deptService.add(dept)
    }

    @Test
    def void testUpdate() {
        def dept = deptService.getById(36)
        println dept
        dept.name ='研发部'
        dept.description = "研发中心"
        deptService.update(dept)
    }

    @Test
    def void tetDelete() {
        deptService.delete(37)
    }

    /**
     * 测试 查询所有部门
     */
    @Test
    def void testqueryAllDepts() {
        List<Dept> depts = deptService.queryAllDepts();

    }
}
