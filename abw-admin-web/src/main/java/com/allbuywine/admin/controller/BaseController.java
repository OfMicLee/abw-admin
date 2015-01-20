package com.allbuywine.admin.controller;

import com.allbuywine.admin.bean.exception.AbwAdminException;
import com.allbuywine.admin.util.IPUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * Controller基类，放置用到的公共资源
 * 比如数据格式转换公共方法、操作日志记录的方法
 * @author of753
 */
@Scope("request")
public class BaseController {

    protected final transient Logger logger = LoggerFactory.getLogger("inf");

    /**
     * The action execution was successful.
     */
    public static final String SUCCESS = "success";

    /**
     * The action execution was a fail.
     */
    public static final String FAIL = "fail";

    /**
     * The Remote execution was a error
     */
    public static final String ERROR = "error";

    /**
     * 成功的Status Code
     */
    private static final int RESCODE_OK = 200;
    /**
     * 失败的Status Code
     */
    private static final int RESCODE_FAIL = 201;

//    /**
//     * 通过config.properties文件中的zimg.server注入值
//     */
//    @Value("${zimg.server}")
//    private String zimgServer;

    /**
     * Jquery DataTable Data
     * @param totalCount
     * @param dataList
     * @return
     */
    protected Map<String, Object> dataTableJson(int totalCount, List<?> dataList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("iTotalDisplayRecords", totalCount);
        data.put("iTotalRecords", totalCount);
        data.put("aaData", dataList == null ? Collections.EMPTY_LIST : dataList);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("result", SUCCESS);
        map.put("data", data);
        return map;
    }

    protected Map<String, Object> dataTableJson(List<?> dataList) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("aaData", dataList == null ? Collections.EMPTY_LIST : dataList);
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("result", SUCCESS);
        map.put("data", data);
        return map;
    }

    protected Map<String, Object> data2json(List<?> data) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(data)) {
            map.put("result", ERROR);
        } else {
            map.put("result", SUCCESS);
        }
        map.put("data", data);
        return map;
    }

    protected Map<String, Object> data2json(Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (data == null) {
            map.put("result", ERROR);
        } else {
            map.put("result", SUCCESS);
        }
        map.put("data", data);
        return map;
    }

    protected void checkParamResult(BindingResult result) throws AbwAdminException {
        if (result.hasErrors()) {
            String msg = result.getFieldError().getDefaultMessage();
            throw new AbwAdminException(msg);
        }
    }

    /**
     * 获取默认ajax成功信息
     */
    protected Map<String, Object> getSuccessResult() {
        return getResult(true, RESCODE_OK, "操作成功！", Collections.EMPTY_MAP);
    }

    /**
     * 描述：获取成功结果
     *
     * @param obj
     * @return
     */
    protected Map<String, Object> getSuccessResult(Object obj) {
        return getResult(true, RESCODE_OK, "操作成功", obj);
    }

    /**
     * 描述：获取失败结果
     *
     * @param msg
     * @return
     */
    protected Map<String, Object> getFailResult(String msg) {
        return getResult(false, RESCODE_FAIL, msg, Collections.EMPTY_MAP);
    }

    /**
     * 描述：获取返回结果
     *
     * @param isOk
     * @param resCode
     * @param errorMsg
     * @param dataMap
     * @return
     */
    protected Map<String, Object> getResult(boolean isOk, int resCode, String errorMsg, Object obj) {
        return createJson(isOk, resCode, errorMsg, obj);
    }

    /**
     * 描述：组装json格式返回结果
     *
     * @param isOk
     * @param resCode
     * @param errorMsg
     * @param obj
     * @return
     */
    protected Map<String, Object> createJson(boolean isOk, int resCode, String errorMsg, Object obj) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("result", isOk ? "ok" : "fail");
        jsonMap.put("rescode", resCode);
        jsonMap.put("msg", errorMsg);
        jsonMap.put("data", obj);
        return jsonMap;
    }

    /**
     * 获取request
     *
     * @return
     */
    protected HttpServletRequest getRequestContext() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    /**
     * 获取session
     *
     * @return
     */
    protected HttpSession getSessionContext() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession(false);
    }

    /**
     * 获取请求来源ip
     *
     * @return
     */
    protected String getIp() {
        HttpServletRequest request = getRequestContext();
        return IPUtil.getIp(request);
    }

//    public String getZimgServer() {
//        return zimgServer;
//    }

    /**
     * 获取登陆用户工号
     *
     * @return
     */
    protected String getEmployeeId() {
        return (String) getSessionContext().getAttribute("empNo");
    }

    /**
     * 根据相对路径获取资源绝对路径
     *
     * @param path
     * @return
     */
    protected String getRealPath(String path) {
        ServletContext app = getSessionContext().getServletContext();
        if (null != app) {
            String root = app.getRealPath(String.valueOf(File.separatorChar));
            return root + path;
        }
        return path;
    }

    /***
     * 异常捕获
     * yujialin(of147)
     * @param ex
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        logger.error("{}失败", new Object[] {  ex });

        String msg = (ex instanceof AbwAdminException) ? ex.getMessage() : "系统错误";
        //如果是ajax请求返回json结果，普通的就返回至error.jsp
        return isAjax(request) ? new ModelAndView(new MappingJacksonJsonView(), getFailResult(msg))
                : new ModelAndView("error", "msg", msg);
    }

    /**
     * 判断请求是不是ajax请求
     * hufeng(of730)
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

//    /**
//     * 拼接图片的缩略图
//     * hufeng(of730)
//     * @param fileUrl
//     * @return 图片缩略图的全路径
//     */
//    protected String getThumbUrl(String fileUrl) {
//        //拼接图片全地址（缩略图）
//        List<String> fullImageSplitter = new ArrayList<String>(Arrays.asList(fileUrl.split("\\.")));
//        fullImageSplitter.add(1, "-50-50.");
//        String thumbImgUrl = StringUtils.join(fullImageSplitter, "");
//
//        return String.format("%s/%s", zimgServer, thumbImgUrl);
//    }
}
