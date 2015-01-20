package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.Dept;
import com.allbuywine.admin.core.mapper.DeptMapper;
import com.allbuywine.admin.core.mapper.DeptRightsMapper;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.PaginationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * hufeng on 14-7-1.
 * Description：部门service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeptService
{
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private DeptRightsMapper deptRightsMapper;

    /**
     * 分页查询部门列表
     *
     * @author of1081(yangxiaodong)
     * @param dept 部门样例，如果包含部门名称，则模糊搜索
     * @param page 分页信息
     * @return PaginationSupport 返回部门分页列表
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PaginationSupport<Dept> query(Dept dept, Pagination page) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("page", page);
        paramMap.put("dept", dept);

        //商品总数量，分页用
        int totalCount = deptMapper.countDepts(paramMap);

        List<Dept> deptList;
        if (totalCount == 0) {
            deptList = new ArrayList<Dept>();
        } else {
            deptList = deptMapper.queryDepts(paramMap);
        }

        return new PaginationSupport<Dept>(deptList, totalCount, page.getiDisplayLength(), page.getiDisplayStart());
    }

    /**
     * 查询全部部门列表，以部门ID倒序排列
     *
     * @author of1081(yangxiaodong)
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Dept> queryAllDepts() {
        List<Dept>   deptList = deptMapper.queryAllDepts();
        return deptList;
    }


    /**
     * 添加部门
     *
     * hufeng(of730)
     * @param dept
     */
    public void add(Dept dept) {
        deptMapper.add(dept);
    }

    /**
     * 更新部门
     *
     * hufeng(of730)
     * @param dept
     */
    public void update(Dept dept) {
        deptMapper.update(dept);
    }

    /**
     * 删除部门
     *
     * hufeng(of730)
     * @param id
     */
    public void delete(Integer id) throws RuntimeException
    {
        Integer count = deptRightsMapper.countDepts(id);
        if (count > 0) {
            throw new RuntimeException("部门已关联权限不能删除");
        }
        deptMapper.delete(id);
    }

    /**
     * 根据id查询部门信息
     *
     * hufeng(of730)
     * @param id
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    /**
     * 查询部门包含的员工工号列表 of583
     * @param id 部门编号
     */
    public List<String> listEmployee(Integer id) {
        return deptMapper.listEmployee(id);
    }

    /**
     * 判断部门名称是否重复
     *
     * hufeng(of730)
     * @param id
     * @param name
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean isSameName(Integer id, String name) {
        return deptMapper.countDeptName(id, name) != 0;
    }
}
