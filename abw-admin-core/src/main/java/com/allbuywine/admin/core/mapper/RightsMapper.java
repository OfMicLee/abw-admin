package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.Rights;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * hufeng on 14-6-30.
 * Description：权限的mapper接口
 */
@Repository
public interface RightsMapper {
    
    
    /**
     * 删除权限
     *
     * hufeng(of730)
     * @param id
     */
    void delete (@Param("id") Integer id);

    /**
     * 保存权限
     *
     * hufeng(of730)
     * @param rights
     *  权限pojo
     */
    void save (Rights rights);


    /**
     * 更新权限
     *
     * hufeng(of730)
     * @param rights
     *  权限pojo
     */
    void update (Rights rights);

    /**
     * 单个权限节点的查询
     *
     * hufeng(of730)
     * @param id
     * @return
     */
    Rights getById (@Param("id") Integer id);


    /**
     * 按父子关系查出所有权限
     * @author of1081
     * @return
     */
    List<Rights> listAll ();

    /**
     * 根据父查询子权限数量
     *
     * hufeng(of730)
     * @param id
     * @return
     */
    Integer countChildByParentId (@Param("id") Integer id);

    /**
     * 查询权限名称是否存在
     *
     * @param id
     * @param name
     * @return
     */
    Integer countName (@Param("id") Integer id, @Param("name") String name);
}
