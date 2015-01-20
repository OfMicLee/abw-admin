package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.MemberOffline;
import com.allbuywine.admin.core.mapper.OfflineMemberMapper;
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
 * 线下会员服务
 * Created by MicLee on 1/19/15.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OfflineMemberService
{
    @Autowired
    private OfflineMemberMapper memberMapper;
    
    /**
     * 分页查询会员列表
     *
     * @param member 会员样例，如果包含姓名，则模糊搜索
     * @param page 分页信息
     * @return PaginationSupport 返回会员分页列表
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PaginationSupport<MemberOffline> query(MemberOffline member, Pagination page) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("page", page);
        paramMap.put("member", member);

        //商品总数量，分页用
        int totalCount = memberMapper.countMembers(paramMap);

        List<MemberOffline> memberList;
        if (totalCount == 0) {
            memberList = new ArrayList<MemberOffline>();
        } else {
            memberList = memberMapper.queryMembers(paramMap);
        }

        return new PaginationSupport<MemberOffline>(memberList, totalCount, page.getiDisplayLength(), page.getiDisplayStart());
    }
}
