import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Date;

/**
 * @author minsiqian
 * @date 2020/3/21 18:23
 */
public class TokenTest {
    @Test
    public void createToken(){
        //1.获取Jwt构建器
        JwtBuilder builder = Jwts.builder();
        //令牌颁发者
        builder.setIssuer("迁迁学院");
        //颁发时间
        builder.setIssuedAt(new Date());
        //设置主体
        builder.setSubject("生成JWT令牌");
        //设置过期时间(当前系统时间+10秒)
        builder.setExpiration(new Date(System.currentTimeMillis()+100000));
        //设置签名算法和密钥
        builder.signWith(SignatureAlgorithm.HS256,"qianqianstudy");
        System.out.println(builder.compact());
    }

    @Test
    public void parseToken(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLov4Hov4HlrabpmaIiLCJpYXQiOjE1ODQ3ODc3MTMsInN1YiI6IueUn-aIkEpXVOS7pOeJjCIsImV4cCI6MTU4NDc4NzgxM30.AWjS3O_HbklKaryhob33Du8IJ5hP2p_MffGLpg1Ro7g";
        Claims body = Jwts.parser().setSigningKey("qianqianstudy").parseClaimsJws(token).getBody();
        System.out.println(body);
    }

    @Test
    public void base64(){
//        byte[] encodedKey = Base64.encodeBase64(JwtUtil.KEY.getBytes());
//        System.out.println(new String(encodedKey));
        String pw = BCrypt.hashpw("123456", BCrypt.gensalt());
        System.out.println(pw);

    }
}
