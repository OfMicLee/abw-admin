package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.MemberOffline;

import java.util.List;
import java.util.Map;

/**
 * 线下会员管理
 * Created by MicLee on 1/19/15.
 */
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

}
