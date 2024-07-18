package com.example.todo_app.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;

@Service
public class JwtService {
    //secret key is used to authenticate the integrity of the signature, generate a unique signature that no one can forge without it
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    //
    private Key getSigninKey(){
        //decode
        byte[] keyBites = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBites);
    }
    //extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() //build jwt parser
                .setSigningKey(getSigninKey()).build()//set signingkey
                .parseClaimsJws(token) //extract token to Jwt<Claims>
                .getBody();
    }
    public <T> T extractClaim(String token,Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token); //extract all claims
        return claimResolver.apply(claims); //take claims we want with claimResolver
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }//get username from token

    public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(),userDetails);
    } //generate a token for an user
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return buildToken(extraClaims,userDetails,jwtExpiration);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(),userDetails,refreshExpiration);
    }


    public String buildToken(Map<String,Object> extraClaims,UserDetails userDetails,long expiration){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSigninKey(),SignatureAlgorithm.HS256)
                .compact();
    }



    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        System.out.println(username);
        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date  date = extractClaim(token,Claims::getExpiration);
        return date.before(new Date());
    }

}
