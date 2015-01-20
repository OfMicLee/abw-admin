package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.Rights;
import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.core.mapper.DeptRightsMapper;
import com.allbuywine.admin.core.mapper.RightsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * hufeng on 14-6-30.
 * Description：权限service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RightsService {

    @Autowired
    private RightsMapper rightsMapper;

    @Autowired
    private DeptRightsMapper deptRightsMapper;

    /**
     * 递归查询所有权限列表
     * @author of1081
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Rights> listAll() throws AbwAdminException
    {
        return  rightsMapper.listAll();
    }
    /**
     * 保存权限
     *
     * hufeng(of730)
     * @param rights
     *  权限pojo
     */
    public void save(Rights rights) {
        rightsMapper.save(rights);
    }

    /**
     * 更新权限
     *
     * hufeng(of730)
     * @param rights
     *  权限pojo
     */
    public void update(Rights rights) {
        rightsMapper.update(rights);
    }

    /**
     * 删除权限
     *
     * hufeng(of730)
     * @param id
     *  权限id
     */
    public void delete(Integer id) throws AbwAdminException {
        Integer count = deptRightsMapper.countRights(id);
        Integer childCount = rightsMapper.countChildByParentId(id);
        if (count > 0) {
            throw new AbwAdminException("菜单已关联部门，不能删除");
        } else if (childCount > 0) {
            throw new AbwAdminException("菜单包含子菜单，不能删除");
        } else {
            rightsMapper.delete(id);
        }
    }

    /**
     * 查询当个权限节点信息
     *
     * hufeng(of730)
     * @param id
     *  权限id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Rights getById(Integer id) {
        return rightsMapper.getById(id);
    }

    /**
     * 判断权限名称是否存在
     *
     * @param id
     * @param name
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean isSameUrlName(Integer id, String name) {
        return rightsMapper.countName(id, name) != 0;
    }
}
