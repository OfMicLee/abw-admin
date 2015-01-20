package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.bean.domain.Employee;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.core.mapper.EmployeeMapper;
import com.allbuywine.admin.core.util.MD5Util;
import com.allbuywine.admin.core.util.ParameterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * Created by MicLee on 2014/7/2.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有的用户列表
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Employee> listAll() {
        return employeeMapper.listAll();
    }

    /**
     * 根据用户编号查询员工信息
     * @param empNo 工号
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Employee get(String empNo) {
        return employeeMapper.getByNo(empNo);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public String getPass(String empNo)
    {
        return employeeMapper.getPassByNo(empNo);
    }

    /**
     * 用户登录处理 MicLee
     * @param employee 员工信息
     */
    public Employee loginCheck(String empNo, String password, String ip) throws AbwAdminException
    {
        Employee employee = this.get(empNo);
        if (employee != null)
        {
            String pass = this.getPass(empNo);
            if (MD5Util.md5Hex(password).equals(pass == null ? "" : pass))
            {
                //                最后登录时间显示上一次的
                //                String lastTime = DateUtil.getDateTime(Calendar.getInstance().getTime());
                employee.setLastIp(ip);
                update(employee);
                return employee;
            }
        }
        return null;
    }

    /**
     * 修改员工信息
     * @param employee
     * @throws AbwAdminException
     */
    public void update(Employee employee) throws AbwAdminException
    {
        employeeMapper.modify(employee);
    }

    public void updatePass(String pass, String empNo) throws AbwAdminException
    {
        employeeMapper.modifyPass(pass, empNo);
    }

    /**
     * 删除员工信息 MicLee
     * @param empNo 工号
     */
    public void delete(String empNo) throws AbwAdminException {

        // 删除员工信息
        int count = employeeMapper.delete(empNo);
        if (1 != count) {
            throw new AbwAdminException("删除员工信息失败");
        }

        // 删除员工与部门的关联
        employeeMapper.deleteDeptRel(empNo);

    }

    /**
     * 修改锁定状态 MicLee
     * @param empNo 工号
     * @param locked 锁定状态 0未锁定 1锁定
     */
    public void lock(String empNo, Integer locked) throws AbwAdminException {
        int count = employeeMapper.lock(empNo, locked);
        if (1 != count) {
            throw new AbwAdminException("修改员工锁定状态失败");
        }
    }

    /**
     * 获取员工所属的部门 MicLee
     * @param empNo 工号
     * @return 部门信息
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Dept getDept(String empNo) {
        return employeeMapper.getDept(empNo);
    }

    /**
     * 保存员工部门信息 MicLee
     * @param empNo 工号
     * @param deptId 部门编号
     * @throws AbwAdminException
     */
    public void saveDeptRel(String empNo, Integer deptId) throws AbwAdminException {

        // 查询是否已属于某个部门，如果是，进行更新，如果没有，新增
        Dept dept = employeeMapper.getDept(empNo);

        int count;
        if (null == dept) {
            // 新增员工与部门关系 MicLee
            count = employeeMapper.addDeptRel(empNo, deptId);
        } else {
            // 修改员工与部门关系 MicLee
            count = employeeMapper.updateDeptRel(empNo, deptId);
        }

        if (1 != count) {
            throw new AbwAdminException("保存员工部门信息失败");
        }

    }
}
