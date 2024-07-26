package com.example.todo_app.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
    private static final String SECRET_KEY = "sDb4E6sciye6yPSfaICxiqUWi7BZGF+eH117yKP+Lry75qyIKOuWUR898tlb1IcnJayB/2hLiUDVFSJOOe5POA==";
    //
    private Key getSigninKey(){
        //decode
        byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBites);
    }
    //extract all claims
    public Claims extractAllClaims(String token) {
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

    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts.builder() //start generate process
                .setClaims(extraClaims) //set claims provide to Jwt as Map<String,Object>
                .setSubject(userDetails.getUsername())//subject of Jwt = user username
                .setIssuedAt(new Date(System.currentTimeMillis())) //set release date for Jwt at current time
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30)) //expired in 30 minutes
                .signWith(getSigninKey(), SignatureAlgorithm.HS256) //signing the Jwt by using HS256 encryption algorithm and using the signing key
                .compact();//complete
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    } //generate a token for an user


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=extractUsername(token);
//        return username.equals(userDetails.getUsername()) && isTokenExpired(token);
        return username.equals(userDetails.getUsername());
    }

    public boolean isTokenExpired(String token) {
        Date  date = extractClaim(token,Claims::getExpiration);
        return date.before(new Date());
    }

}
