package com.by.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.by.autoconfig.TokenProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成token 验证token
 */
public class TokenFactory {

    TokenProperties properties;

    public TokenFactory(TokenProperties properties) {
        this.properties = properties;
    }

    /**
     * 生成token
     *
     * @param payload 用户信息
     * @return token字符串
     */
    public String generateToken(Map<String, Object> payload) {
        return JWT.create().withHeader(getHeader())
                .withPayload(payload)
                .withExpiresAt(getValdateDate())
                .sign(Algorithm.HMAC256(properties.getSecret()));
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public ValidResult verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(properties.getSecret())).build();
        //验证
        //验证token
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return new ValidResult(1, "令牌有效", decodedJWT.getClaims());
        } catch (TokenExpiredException e) {
            return new ValidResult(0, "token已过期！", null);
        } catch (Exception e) {
            return new ValidResult(0, "无效的token", null);
        }
    }

    /**
     * 头部信息
     *
     * @return
     */
    Map getHeader() {
        Map map = new HashMap();
        map.put("typ", "JWT");
        map.put("alg", "HMAC");
        return map;
    }

    /**
     * 返回一个有效期  默认30分钟
     *
     * @return
     */
    Date getValdateDate() {
        long begin = System.currentTimeMillis();
        return new Date(begin + properties.getValidTime());
    }


}
