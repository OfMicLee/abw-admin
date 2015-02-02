package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.MemberOffline;
import com.allbuywine.admin.bean.enums.MemberTypeEnum;
import com.allbuywine.admin.bean.exception.ExceptionHandler;
import com.allbuywine.admin.core.mapper.OfflineMemberMapper;
import com.allbuywine.admin.core.util.CommonUtil;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OfflineMemberService
{
    @Autowired
    private OfflineMemberMapper memberMapper;

    /**
     * 分页查询会员列表
     *
     * @param member 会员样例，如果包含姓名，则模糊搜索
     * @param page   分页信息
     * @return PaginationSupport 返回会员分页列表
     */
    public PaginationSupport<MemberOffline> query (MemberOffline member, String startTime, String endTime, Pagination page)
    {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("page", page);
        paramMap.put("member", member);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);

        //商品总数量，分页用
        int totalCount = memberMapper.countMembers(paramMap);

        List<MemberOffline> memberList;
        if (totalCount == 0)
        {
            memberList = new ArrayList<MemberOffline>();
        }
        else
        {
            memberList = memberMapper.queryMembers(paramMap);
        }

        return new PaginationSupport<MemberOffline>(memberList, totalCount, page.getiDisplayLength(), page.getiDisplayStart());
    }

    /**
     * 根据会员编号获取会员信息
     *
     * @param id
     * @return
     */
    public MemberOffline getById (String id)
    {
        return memberMapper.getById(id);
    }

    /**
     * 增加会员
     *
     * @param member
     */
    @Transactional(rollbackFor = Exception.class)
    public void add (MemberOffline member)
    {
        Integer maxIdInteger = memberMapper.getMaxId();
        int maxId = maxIdInteger == null ? 0 : maxIdInteger;
        String id = CommonUtil.createMemberId(++maxId, MemberTypeEnum.OFFLINE);
        member.setIdCode(maxId);
        member.setId(id);
        memberMapper.add(member);
    }

    /**
     * 修改会员信息
     *
     * @param member
     */
    @Transactional(rollbackFor = Exception.class)
    public void update (MemberOffline member)
    {
        memberMapper.update(member);
    }

    /**
     * 删除会员
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete (String id)
    {
        memberMapper.delete(id);
    }
}
