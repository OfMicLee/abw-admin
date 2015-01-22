package com.allbuywine.admin.core.service

import org.junit.Test

/**
 * Created by MicLee on 12/15/14.
 */
class EmployeeServiceTest extends BaseTest {

    EmployeeService employeeService = getBean(EmployeeService.class);

    @Test
    def void testGetByNo() {
        println(employeeService.get("ABW002"))
    }


}
