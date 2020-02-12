package com.ssafy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;

import com.ssafy.model.dto.user;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//JWT에서는 이 Claim JSON 문자열을 BASE64 인코딩을 통해서 하나의 문자열로 변환한다.


/*
JWT의 구조
JWT는 크게 세 부분(헤더header, 페이로드payload, 시그너처signature)으로 나눠져 있습니다. 

1) 헤더 : 이 토큰에 대한 설명
2) 페이로드 : Claim이라는 사용자에 대한 프로퍼티나 속성을 이야기 한다. 토큰자체가 정보를 가지고 있는 방식인데, JWT는 이 Claim을 JSON을 이용해서 정의한다.
3) 시그니처 : 토큰의 무결성을 검증하기 위한 시그니처 해쉬

*/
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

/*
 * 1. Issuer, Subject, Expiration, ID와 같은 토큰의 내부 권한claims를 정의합니다.
 * 2. JWT를 암호화 서명해서 JWS를 만듭니다. 
 * 3. JWT Compact Serialization 규칙에 따라 URL로 사용할 수 있도로규JWT를 압축합니다. 
 */
	
    private String secretKey = "cat";

 // 30분만 토큰 유효
    private long tokenValidMilisecond =  30 * 60 * 1000L; 

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    
    // Jwt 토큰 생성
    public String createToken(user User) {
    	Claims claims = Jwts.claims().setSubject(User.toString());//JWT payload에 저장되는 정보단위
//    	System.out.println(User.toString());
//    	claims.put("nickname", User.getUser_id());
    	Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
    	
		/*
		 * 
    	Map<String, Object> map = new HashMap<>();
        map.put("email", User.getUser_email());
        map.put("nickname", User.getUser_id());
        */
        Date now = new Date();
        return Jwts.builder()
        		.setHeader(headerMap)
        		.setClaims(claims)
                //.setClaims(map) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }
    
    

    /*
    //Token check
    public boolean checkJwt(String jwt) throws Exception {
    	try {
    		Claims claims = Jwts.parser().setSigningKey(secretKey)
    				.parseClaimsJws(jwt).getBody(); //수행 되면 정상
    		return true;
    	} catch (ExpiredJwtException exception) {
    		//토큰 만료
    		return false;
    	} catch (JwtException exception) {
    		//토큰 변조
    		return false;
    	} 
    	
    }*/
    
    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
    	Claims claim = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String str = claim.getSubject();
//        System.out.println(str);
        return str;
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
