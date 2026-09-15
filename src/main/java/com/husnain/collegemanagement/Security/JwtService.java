package com.husnain.collegemanagement.Security;

import com.husnain.collegemanagement.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry}")
    private long expiration;

    public String generateToken(User  user) {
        Instant now = Instant.now();

        JwtClaimsSet claims=JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiration))
                .subject(user.getUsername())
                .claim("role",user.getRole().name())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256)
                .build();
        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(jwsHeader,claims)
                ).getTokenValue();
    }

}
