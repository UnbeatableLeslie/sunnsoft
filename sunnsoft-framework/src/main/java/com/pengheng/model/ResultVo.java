package com.pengheng.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.pengheng.util.Toolkits;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pengheng
 */
@Data
public class ResultVo implements Serializable {

    private static final long serialVersionUID = -7535228184843182772L;

    private boolean encrypt = false;

    /**
     * 返回的code
     */
    private String code;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * 返回的结果集合
     */
    private Object data;

    /**
     * 返回的分页集合
     */
    private Object pageInfo;

    private String timestamp = String.valueOf(System.currentTimeMillis());
    private String signature = "";
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(this.timestamp)));

    public ResultVo() {
    }

    public ResultVo(String code) {
        this(code, "");
    }

    public ResultVo(String code, String msg) {
        this(code, msg, null);
    }

    public ResultVo(String code, String message, Object data) {
        this.code = Optional.ofNullable(code).orElse("");
        this.msg = Optional.ofNullable(message).orElse("");
        this.data = data;

        //如果是列表的话 判断是否回传分页信息
        if (data instanceof List) {
            HttpServletRequest httpServletRequest = Toolkits.getHttpServletRequest();
            boolean usePageHelper = Boolean.parseBoolean(Toolkits.defaultString(httpServletRequest.getAttribute("usePageHelper"), "false"));

            if (usePageHelper) {
                Map<Object, Object> pageMap = Toolkits.getPageMap();
                long total = new PageInfo((List) data).getTotal();

                long pageSize = Long.parseLong(Toolkits.defaultString(pageMap.get("pageSize"), "20"));
                long totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize + 1);

                pageMap.put("total", total);
                pageMap.put("totalPage", totalPage);
                this.pageInfo = pageMap;
                httpServletRequest.setAttribute("usePageHelper", "false");
            }
        } else if (data instanceof IPage) {
            //如果传入的是MyBatisPlus 的分页对象
            Map<Object, Object> pageMap = new HashMap<>();

            IPage page = (IPage) data;
            long total = page.getTotal();
            long pageSize = page.getSize();
            long pageNum = page.getCurrent();
            long totalPage = page.getPages();
            pageMap.put("total", total);
            pageMap.put("pageNum", pageNum);
            pageMap.put("pageSize", pageSize);
            pageMap.put("totalPage", totalPage);
            this.pageInfo = pageMap;
            this.data = page.getRecords();
        }

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}