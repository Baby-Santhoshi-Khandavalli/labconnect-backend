package com.labconnect.security;

import com.labconnect.repository.Identity.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
//@EntityScan()
public class JwtService {

    @Autowired
    private UserRepository userRepository; //to fetch the role

    private static final String SECRET="oiiqaf33puJC1nM1Qeq4PvIjygV2ddcJo7xJowZuF9v";

    public String generateToken(String username){
        Map<String, Object> claims=new HashMap<>();

        //fetch user from DB to get their role
        var user=userRepository.findByEmail(username);
        if(user!=null){
            claims.put("role", "ROLE_"+user.getRole().name().toUpperCase());
            claims.put("userId", user.getUserId());
        }
        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims=Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !extractClaim(token, Claims::getExpiration).before(new Date()));
    }
}
