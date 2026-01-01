package JwtsManager;

import Reposteryes.TokenResposteyrs;
import Reposteryes.UserReposteryes;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.annotation.PostConstruct;
import model.RefreshTokens;
import model.User;
import org.hibernate.query.sqm.AliasCollisionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Component
public class MainJwts {

    private UserReposteryes userReposteryes;
    private TokenResposteyrs resposteyrs;

    @Autowired
    public MainJwts(UserReposteryes userReposteryes,TokenResposteyrs tokenResposteyrs) {
        this.userReposteryes = userReposteryes;
        this.resposteyrs =tokenResposteyrs;
    }



    private final SecretKey js = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private Set<String> blackAtro = new HashSet<String>();
    @Value("${jwt.security.atro}")
    private String rese;
    private SecretKey re;
    @PostConstruct
    public void keys(){
        try {

            this.re = Keys.hmacShaKeyFor(rese.getBytes());
        } catch (WeakKeyException e) {

            System.out.println("اترو يتصل بكه");
            this.re = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
    }
    public String genAccessToken(String username){
        User user = userReposteryes.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not found error"));
        long ex = 360000000;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ ex))
                .signWith(js)
                .compact();
    }
    public boolean VerifyAccess(String token){
        if(blackAtro.contains(token)){
            return false;
        }

        try{
            Jwts.parserBuilder().setSigningKey(js).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean AddToBlackList(String token){
        if(blackAtro.contains(token)){
            return true;
        }
        blackAtro.add(token);
        return true;
    }
    public String getUsernameFromAccessToken(String token){
        return Jwts.parserBuilder().setSigningKey(js).build().parseClaimsJws(token).getBody().getSubject();
    }
    public String genRefreshTOken(String username){
        User user = userReposteryes.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username Not found error"));
        RefreshTokens refreshTokens = new RefreshTokens();
        refreshTokens.setUser(user);
        refreshTokens.setToken(UUID.randomUUID().toString());
        refreshTokens.setEx(new Date(System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)).toInstant());
        resposteyrs.save(refreshTokens);
        return refreshTokens.getToken();
    }
    public boolean verifyRefsh(String token){
        RefreshTokens refreshTokens = resposteyrs.findByToken(token).orElseThrow(() -> new RuntimeException("error"));
        if(refreshTokens.getEx().isBefore(Instant.now())){
            resposteyrs.delete(refreshTokens);
            return false;

        }
        return true;
    }
    public String AccessTokenFromRefreshTOken(String token){
        if(verifyRefsh(token)){
            RefreshTokens refreshTokens = resposteyrs.findByToken(token).orElseThrow(() -> new RuntimeException("error"));

            return genAccessToken(String.valueOf(refreshTokens.getUser()));
        }else{
            return null;
        }

    }
    public Long getUserIdFromRefreshToken(String token){
        RefreshTokens refreshTokens = resposteyrs.findByToken(token).orElseThrow(() -> new RuntimeException("error"));
        return refreshTokens.getUser().getId();
    }
    public Long getUserIdFromAccessToken(String token){
        try{
            if(!VerifyAccess(token)){
                return null;
            }
           String useString = getUsernameFromAccessToken(token);

                User user = userReposteryes.findByUsername(useString).orElseThrow(() -> new RuntimeException("error"));
                return user.getId();
        }catch(Exception e){
            return null;
        }
        
    }


}
