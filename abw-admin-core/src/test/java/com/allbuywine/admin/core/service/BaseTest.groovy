package com.allbuywine.admin.core.service

import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * hufeng on 14-4-14.
 * Description：
 *
 */
class BaseTest {
    def ctx = new ClassPathXmlApplicationContext("applicationContext-core-test.xml")

    def getBean(String bean) {
        return ctx.getBean(bean)
    }

    def getBean(Class beanType) {
        return ctx.getBean(beanType)
    }
}
