package com.msgcloud.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msgcloud.utils.DateUtil;
import com.msgcloud.utils.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    @Value("${jwt.secret}")
    private static String secret;
    @Value("${jwt.expiretime}")
    private static long expiretime;


    /**
     * 生成token  参数是放入token的信息
     *
     * @param userid
     * @param username
     * @param account
     * @param roleids
     * @return
     */
    public static String sign(int userid, String username, String account, Integer[] roleids) {
        //当前时间(秒)
        long nowTime = new Date().getTime()/1000;
        Date expireDate = new Date(nowTime + expiretime);

        return JWT.create()
                .withClaim("userid", userid)
                .withClaim("username", username)
                .withClaim("account", account)
                .withArrayClaim("roleids", roleids)
                .withClaim("noncestr", StringUtil.getRandStr(32))     //随机三十二位字符
                .withClaim("timestamp", DateUtil.getTimestamp())                 //签发时间
                .withExpiresAt(expireDate)                                        //设置token过期时间====>当前时间+存活时长(yml中配置)
                .sign(Algorithm.HMAC256(secret));                                 //加密算法   secret为加盐字符
    }

    // 构建JWT Token
    public static String sign() {
        return sign(0, "", "", new Integer[0]);
    }

    // 刷新JWT Token
    public static String refresh(String token) throws Exception {
        Map<String, Object> tokenMap = JwtUtils.parse(token);
        return sign((int) tokenMap.get("userid"),
                (String) tokenMap.get("username"),
                (String) tokenMap.get("account"),
                (Integer[]) tokenMap.get("roleids"));
    }



    /**
     * 验证jwt  token
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        if (token == null || token.trim().equals("")) {
            return false;
        }

        // 执行认证 如果authToken非空 验证authToken 如果验证失败 返回false
        JWTVerifier jwtVerifier =   JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 解析JWT
     *
     * @param token ---其中就有id   subject   roles
     * @return
     */
    public static Map<String, Object> parse(String token) {
        Map<String, Object> returndata = new HashMap<>();

        //初始化token中存放的信息(变量)    故生成时候需要把这几个信息存放进去
        int userid = 0;
        String username = "";
        String account = "";
        Integer[] roleids = new Integer[0];

        if (!StringUtils.isEmpty(token)) {
            DecodedJWT decodedJWT = JWT.decode(token);
            userid = decodedJWT.getClaim("userid").asInt();
            username = decodedJWT.getClaim("username").asString();
            account = decodedJWT.getClaim("account").asString();
            roleids = decodedJWT.getClaim("roleids").asArray(Integer.class);
        }

        returndata.put("userid", userid);
        returndata.put("username", username);
        returndata.put("account", account);
        returndata.put("roleids", roleids);
        return returndata;
    }


    public static int parseInt(String token, String key) {
        Map<String, Object> returndata = parse(token);
        return (int) returndata.get(key);
    }



    public static String parseString(String token, String key) {
        Map<String, Object> returndata = parse(token);
        return (String) returndata.get(key);
    }

}
