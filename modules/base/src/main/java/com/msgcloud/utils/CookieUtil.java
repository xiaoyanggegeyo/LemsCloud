package com.msgcloud.utils;
/***
 * @author LZL
 * COOKIE 操作的工具类
 */
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/***
	 * 取得COOKIE 值通过COOKIE NAME
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies == null) {
			return "";
		}		
		for (Cookie c : cookies) {
			if (name.trim().equals(c.getName().trim())) {
				return c.getValue().trim();
			}
		}
		return "";
	}
	/***
	 * 增加一个COOKIE， 默认PATH “/”
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie newCookie = new Cookie(name, value);        
        // 指定服务器的所有页面都应该收到该cookie
        newCookie.setPath("/");
        newCookie.setMaxAge(maxAge);
        newCookie.setHttpOnly(true);
		response.addCookie(newCookie);
	}
	/****
	 * 
	 * @param response
	 * @param request
	 * @param name
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		 Cookie[] cookies = request.getCookies();  
		 if (null==cookies) {  
//             System.out.println("没有cookie==============");
         } else {  
             for(Cookie cookie : cookies){  
                 if(cookie.getName().equals(name)){  
                     cookie.setValue(null);  
                     cookie.setMaxAge(0);// 立即销毁cookie  
                     cookie.setPath("/");  
//                     System.out.println("被删除的cookie名字为:"+cookie.getName());
                     response.addCookie(cookie);  
                     break;  
                 }  
             }  
         }  
	}
}
