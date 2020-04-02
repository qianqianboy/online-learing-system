package com.qianqian.edu.member.center.controller;

import com.qianqian.edu.common.vo.R;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author minsiqian
 * @date 2020/3/16 15:33
 */
@RestController
//@RequestMapping("/oauth")
public class AuthController {

    private AuthRequest getAuthRequest(){
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId("c4db9658cf625494e39f")
                .clientSecret("116c207a249ceea1061959d7e5cef95425407216")
                .redirectUri("http://localhost:9500/edu-member-center/oauth/callback/github")
                .build());
    }

    @RequestMapping("/render/github")
    public void renderAuth( HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize());
    }

    @RequestMapping("/callback/github")
    public R login(AuthCallback callback){
        AuthRequest authRequest = getAuthRequest();
        AuthResponse response = authRequest.login(callback);
        return R.ok().data("userInfo",response);
    }

    @RequestMapping("/revoke/github/{token}")
    public Object revokeAuth(@PathVariable("token") String token) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.revoke(AuthToken.builder().accessToken(token).build());
    }

    @RequestMapping("/refresh")
    public Object refreshAuth( String token){
        AuthRequest authRequest = getAuthRequest();
        return authRequest.refresh(AuthToken.builder().refreshToken(token).build());
    }
}
