package com.allbuywine.admin.core.mapper;

import com.allbuywine.admin.bean.domain.OperateLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 日志操作
 */
@Repository
public interface OperateLogMapper {

    public int add (OperateLog operateLog);

    public int queryCount (Map<String, Object> paramMap);

    public List<OperateLog> query (Map<String, Object> paramMap);
}
