package com.qianqian.edu.user.oauth.service;

import com.qianqian.edu.user.oauth.util.AuthToken;

/**
 * @author minsiqian
 * @date 2020/4/2 2:03
 */

public interface AuthService {

    /**
     * 申请AuthToken令牌，并将其存进Redis缓存
     * @param mobile 手机号
     * @param password 密码
     * @param clientId 客户端id
     * @param clientSecret 客户端密码
     * @return
     */
    AuthToken login(String mobile, String password, String clientId, String clientSecret);

    /**
     * 将jti短令牌存入Cookie
     * @param jtiToken 短令牌jti
     */
    void addCookie(String jtiToken);
}
