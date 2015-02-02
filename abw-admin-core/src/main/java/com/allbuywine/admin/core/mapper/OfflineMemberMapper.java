package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.MemberOffline;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 线下会员管理
 * Created by MicLee on 1/19/15.
 */
@Repository
public interface OfflineMemberMapper
{
    /**
     * 查询会员总数
     * @param paramMap
     * @return
     */
    int countMembers(Map<String, Object> paramMap);

    /**
     * 分页查询会员列表
     * @param paramMap
     * @return
     */
    List<MemberOffline> queryMembers(Map<String, Object> paramMap);

    /**
     * 根据会员编号获取会员信息
     * @param id
     * @return
     */
    MemberOffline getById (@Param("id") String id);

    /**
     * 新增会员
     * @param member
     */
    void add (MemberOffline member);

    /**
     * 修改会员
     * @param member
     */
    void update (MemberOffline member);

    /**
     * 获取最大会员编码
     * @return
     */
    Integer getMaxId ();

    /**
     * 删除会员
     * @param id
     */
    void delete (String id);

}
