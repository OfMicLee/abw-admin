package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.bean.domain.Employee;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工表数据库操作接口
 * Created by MicLee on 2014/7/2.
 */
@Repository
public interface EmployeeMapper
{

    /**
     * 查询所有员工记录 MicLee
     */
    List<Employee> listAll ();

    /**
     * 根据工号查询员工信息 MicLee
     *
     * @param empNo 工号
     * @return 员工信息
     */
    Employee getByNo (@Param("empNo") String empNo);

    /**
     * 根据工号查询员工密码 MicLee
     *
     * @param empNo
     * @return
     */
    String getPassByNo (@Param("empNo") String empNo);

    /**
     * 新增员工信息 MicLee
     */
    void add (Employee employee);

    /**
     * 更新员工信息 MicLee
     */
    int modify (Employee employee);

    /**
     * 更新密码 MicLee
     */
    int modifyPass (@Param("password") String password, @Param("empNo") String empNo);

    /**
     * 删除员工信息 MicLee
     *
     * @param empNo 工号
     */
    int delete (@Param("empNo") String empNo);

    /**
     * 删除员工与部门的关联 MicLee
     *
     * @param empNo 工号
     */
    int deleteDeptRel (@Param("empNo") String empNo);

    /**
     * 修改锁定状态 MicLee
     *
     * @param empNo  工号
     * @param locked 锁定状态 1未锁定 0锁定
     */
    int lock (@Param("empNo") String empNo, @Param("locked") Integer locked);

    /**
     * 获取员工所属的部门 MicLee
     *
     * @param empNo 工号
     * @return 部门信息
     */
    Dept getDept (@Param("empNo") String empNo);

    /**
     * 新增员工与部门关系 MicLee
     *
     * @param empNo  工号
     * @param deptId 部门编号
     */
    int addDeptRel (@Param("empNo") String empNo, @Param("deptId") Integer deptId);

    /**
     * 修改员工与部门关系 MicLee
     *
     * @param empNo  工号
     * @param deptId 部门编号
     */
    int updateDeptRel (@Param("empNo") String empNo, @Param("deptId") Integer deptId);
}
