package com.example.library.util;


import com.example.library.dto.JwtDTO;
import com.example.library.enums.ProfileRole;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JWTUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "1wddfewkfjjkfhkjh1jk1p2s7dksdakjshdkahsdkjahsdkahs7kjhaskjdh2skjdhadasdasg7fgdfgdfd";


    public static String encode(String email, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        jwtBuilder.signWith(secretKeySpec);

        jwtBuilder.claim("email", email);
        jwtBuilder.claim("role", role);

        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("Library");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();

        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        ProfileRole profileRole = role != null ? ProfileRole.valueOf(role) : null;

        return new JwtDTO(email, profileRole);
    }

    public static JwtDTO decodeForSpringSecurity(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        ProfileRole profileRole = ProfileRole.valueOf(role);
        return new JwtDTO(email, profileRole);
    }
}
