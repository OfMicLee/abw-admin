package com.allbuywine.admin.core.service;

import com.allbuywine.admin.bean.domain.OperateLog;
import com.allbuywine.admin.core.mapper.OperateLogMapper;
import com.allbuywine.admin.core.util.Pagination;
import com.allbuywine.admin.core.util.PaginationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MicLee on 14-7-1.
 * 操作日志记录service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperateLogService {

    /**
     * 日志Mapper
     */
    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * MicLee 操作记录日志查询
     * @param operateLog
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public PaginationSupport<OperateLog> query(OperateLog operateLog, String startTime, String endTime, Pagination page) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("operateLog", operateLog);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("page", page);
        int totalCount = operateLogMapper.queryCount(paramMap);

        List<OperateLog> operateLogList = null;
        if (totalCount == 0) {
            operateLogList = new ArrayList<OperateLog>();
        } else {
            operateLogList = operateLogMapper.query(paramMap);
        }


        return new PaginationSupport<OperateLog>(operateLogList, totalCount, page.getiDisplayLength(), page.getiDisplayStart());
    }

    /**
     * MicLee 操作记录日志增加
     * @param operateLog
     * @return
     */
    @Async
    public void add(OperateLog operateLog) {
         operateLogMapper.add(operateLog);
    }
}
