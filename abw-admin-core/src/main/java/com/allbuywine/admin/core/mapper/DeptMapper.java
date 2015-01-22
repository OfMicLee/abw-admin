package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.Dept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * hufeng on 14-7-1.
 * Description：部门Mapper
 */
@Repository
public interface DeptMapper {

    /**
     * 分页查询部门列表
     *
     * @author of1081(yangxiaodong)
     * @param paramMap
     * @return
     */
    List<Dept> queryDepts (Map<String, Object> paramMap);

    /**
     * 查询全部部门列表，以ID倒序排
     *
     * @author of1081(yangxiaodong)
     * @return
     */
    List<Dept> queryAllDepts ();

    /**
     * 部门总数
     *
     * @author of837_jh
     * @param paramMap
     * @return
     */
    int countDepts (Map<String, Object> paramMap);

    /**
     * 添加部门
     *
     * hufeng(of730)
     * @param dept
     */
    void add (Dept dept);

    /**
     * 根据id删除部门
     *
     * hufeng(of730)
     * @param id
     */
    void delete (@Param("id") Integer id);


    /**
     * 更新部门信息
     *
     * hufeng(of730)
     * @param dept
     */
    void update (Dept dept);

    /**
     * 根据id查询部门信息
     *
     * hufeng(of730)
     * @param id
     * @return
     */
    Dept getById (@Param("id") Integer id);

    /**
     * 查询部门包含的员工工号列表 of583
     * @param id 部门编号
     */
    List<String> listEmployee (@Param("id") Integer id);

    /**
     * 查询部门名称的数量
     *
     * hufeng(of730)
     * @param id
     * @param name
     * @return
     */
    Integer countDeptName (@Param("id") Integer id, @Param("name") String name);
}
