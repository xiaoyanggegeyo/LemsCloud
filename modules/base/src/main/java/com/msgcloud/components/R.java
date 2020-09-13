package com.msgcloud.components;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应结构体
 */
public class R extends HashMap<String, Object> {
    public R() {
        put("error", 0);
        put("msg", "成功");
        put("data", new HashMap<>());
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> data) {
        R r = new R();
        r.put("data", data);
        return r;
    }

    public static R ok(String msg, Map<String, Object> data) {
        R r = new R();
        r.put("msg", msg);
        r.put("data", data);
        return r;
    }

    public static R error(int errCode, String msg) {
        R r = new R();
        r.put("error", errCode);
        r.put("msg", "");
        return r;
    }

    public static R error() {
        R r = new R();
        r.put("error", 1);
        r.put("msg", "");
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.put("error", 1);
        r.put("msg", msg);
        return r;
    }

    public static R error(String msg, Map<String, Object> data) {
        R r = new R();
        r.put("error", 1);
        r.put("msg", msg);
        r.put("data", data);
        return r;
    }

    public R putData(String key, Object value){
        HashMap<String,Object> data = (HashMap<String,Object>)get("data");
        data.put(key,value);
        put("data", data);
        return this;
    }

    public int getError() {
        return Integer.parseInt(get("error").toString());
    }

    public String getMsg() {
        return get("msg").toString();
    }

    public Object getData() {
        return get("data");
    }
}
