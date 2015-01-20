package com.allbuywine.admin.core.service

import com.allbuywine.admin.bean.domain.Rights
import org.junit.Test

/**
 * hufeng on 14-6-30.
 * Description：权限service的测试
 *
 */
class RightServiceTest extends BaseTest {
    RightsService rightsService = getBean("rightsService")

    @Test
    def void testSave() {
        def rights = new Rights(parentId: 1, name: "test12", url: '/test', description: "desc")
        rightsService.save(rights)
    }

    @Test
    def void testUpdate() {
        def rights = rightsService.getById(1L)
        println rights

        rights.name = "test2"
        rights.parentId=1L
        rights.url = '/test/test'

        print rights

        rightsService.update(rights)

    }

    @Test
    def void testDelete() {
        rightsService.delete(1L)
    }

    @Test
    def void testListAll() {
        print(rightsService.listAll())
    }
}
