package com.allbuywine.admin.bean.domain;

/**
 * hufeng on 14-7-2.
 * Description：部门和权限的关联
 */
public class DeptRights extends BaseBean
{
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 权限id
     */
    private Integer rightsId;

    //~~~~~~~~~~~~~~~setter&&getter~~~~~~~~~~
    public Integer getDeptId ()
    {
        return deptId;
    }

    public void setDeptId (Integer deptId)
    {
        this.deptId = deptId;
    }

    public Integer getRightsId ()
    {
        return rightsId;
    }

    public void setRightsId (Integer rightsId)
    {
        this.rightsId = rightsId;
    }
}
