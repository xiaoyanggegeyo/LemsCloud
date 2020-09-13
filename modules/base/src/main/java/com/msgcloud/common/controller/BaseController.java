package com.msgcloud.common.controller;

import com.msgcloud.components.R;
import com.msgcloud.config.jwt.JwtUtils;
import com.msgcloud.utils.DateUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础controller 所有controller都应该继承此 和一些公共的controller路由
 */
@RestController("AdminController")
@RequestMapping("/lems/admin")
public class BaseController {

    protected HttpServletRequest request;

    private HttpServletResponse response;


    /**
     * 在所有controller执行之前都执行一次
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        this.request = request;
        this.response = response;

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

    }


    //TODO 登录成功 就生成新token
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestBody Map<String, String> map) {
        String loginname = map.get("loginname");
        String password = map.get("password");
//        User admin = adminService.login(loginname, password);

//        if (admin == null) {
//            return R.error("用户名或者密码不正确!");
//        }
//
//        //如果登录成功就生成token令牌  以下每次请求都带着token(拦截器)
//        String jwt = JwtUtils.createJWT(admin.getId(), admin.getLoginname(), "admin");
//        Map<String, Object> rmap = new HashMap<>();//前端需要什么信息给它返回什么信息
//        rmap.put("token", jwt);
//        rmap.put("role", "admin");
//        rmap.put("loginname", admin.getLoginname());
//        response.setHeader("Authorization",rmap);    将token设置在响应头里面
        return R.ok("登录成功!", null);
    }

    protected int getIntParameter(String param) {
        int value = 0;
        String strPara = request.getParameter(param);
        if (strPara != null && !strPara.equals("")) {
            value = Integer.valueOf(strPara);
        }
        return value;
    }

    protected float getFloatParameter(String param) {
        float value = 0;
        String strPara = request.getParameter(param);
        if (strPara != null && !strPara.equals("")) {
            value = Float.valueOf(strPara);
        }
        return value;
    }

    protected Integer getIntegerParameter(String param) {
        Integer value = null;
        String strPara = request.getParameter(param);
        if (strPara != null && !strPara.equals("")) {
            value = Integer.valueOf(strPara);
        }
        return value;
    }

    protected String getStringParameter(String param) {
        String value = request.getParameter(param);
        if (value == null) {
            value = "";
        }
        return value.trim();
    }

    protected String[] getStringArraryParameter(String param) {
        String[] values = request.getParameterValues(param);
        if (values == null) {
            values = new String[]{};
        }
        return values;
    }

    protected int[] getIntArraryParameter(String param) {
        String[] values = request.getParameterValues(param);
        if (values == null) {
            values = new String[]{};
        }

        int[] valuesn = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals("")) {
                valuesn[i] = 0;
            } else {
                valuesn[i] = Integer.parseInt(values[i]);
            }
        }
        return valuesn;
    }

    protected int[] getIntArraryParameter(String param, int minlength) {
        int[] values = getIntArraryParameter(param);
        if (values.length >= minlength)
            return values;

        else {
            int[] valuesn = new int[minlength];
            System.arraycopy(values, 0, valuesn, 0, values.length);
            return valuesn;
        }
    }

    protected Integer[] getIntegerArraryParameter(String param) {
        String[] values = request.getParameterValues(param);
        if (values == null) {
            values = new String[]{};
        }

        Integer[] valuesn = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals("")) {
                valuesn[i] = 0;
            } else {
                valuesn[i] = Integer.parseInt(values[i]);
            }
        }
        return valuesn;
    }

    protected Date getDateParameter(String param) {
        String strtime = this.request.getParameter(param);
        Date time;

        if (strtime != null && !strtime.trim().equals("")) {
            time = DateUtil.getDateFormat(strtime);
        } else {
            time = null;
        }
        return time;
    }


    protected boolean getBooleanParameter(String param) {
        String bool = this.request.getParameter(param);

        if (bool != null && !bool.trim().equals("")) {
            return bool.toLowerCase().equals("true");
        }
        return false;
    }

    protected Map<String, String> getDateRangeParameter(String param) {
        Map<String, String> returnData = new HashMap<>();
        returnData.put("daterange", "");
        returnData.put("starttime", "");
        returnData.put("endtime", "");

        String dateRange = this.request.getParameter(param);
        if (dateRange != null && !dateRange.trim().equals("")) {
            try {
                String[] dates = dateRange.split(" - ");
                if (dates.length == 2) {
                    returnData.put("daterange", dateRange);
                    returnData.put("starttime", dates[0]);
                    returnData.put("endtime", dates[0]);
                }
            } catch (Exception e) {

            }
        }
        return returnData;
    }



}
