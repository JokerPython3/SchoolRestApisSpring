package Controllre.AuthControllre.Access;

import JwtsManager.MainJwts;
import Reposteryes.TokenResposteyrs;
import Reposteryes.UserReposteryes;
import model.RefreshTokens;
import model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/v1/authroztion/access/atro")
public class Acc {
    private MainJwts mainJwts;
    private TokenResposteyrs tokenResposteyrs;
    private UserReposteryes userReposteryes;
    public Acc(MainJwts mainJwts, TokenResposteyrs tokenResposteyrs,UserReposteryes userReposteryes) {
        this.mainJwts = mainJwts;
        this.tokenResposteyrs = tokenResposteyrs;
        this.userReposteryes = userReposteryes;
    }
    @PostMapping("/access/from/refresh/")
    public Map<String,Object> accessFromRefresh(@RequestParam("token") String refreshToken,@RequestHeader("Authorization") String ss){
        RefreshTokens refreshTokens = tokenResposteyrs.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Refresh Token Not found"));
        System.out.print("done refresh");
        String accessToken = mainJwts.genAccessToken(refreshTokens.getUser().getUsername());
        String ww = ss.replace("Bearer ","");
        mainJwts.AddToBlackList(ww);

        return Map.of(
                "data",Map.of(
                        "accessToken",accessToken
                ),                "message","Access Token Generated Successfully",
                "status",200

        );
    }
    @PostMapping("/verify/access/")
    public Map<String,Object> verifyAccess(@RequestHeader("Authorization") String accessToken){
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
            if(mainJwts.VerifyAccess(accessToken)){
                return Map.of(
                        "data",Map.of(
                                "valid",true
                        ),
                        "message","Access Token is valid",
                        "status",200
                );
        }




    }
        return Map.of(
                "data",Map.of(
                        "valid",false
                ),
                "message","Access Token is invalid",
                "status",401
        );

    }
    @PostMapping("/verify/refresh/")
    public Map<String,Object> verifyRefresh(@RequestParam("token") String refreshToken){
        RefreshTokens refreshTokens = tokenResposteyrs.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Refresh Token Not found"));
        return Map.of(
                "data",Map.of(
                        "valid",true
                ),
                "message","Refresh Token is valid",
                "status",200
        );


    }
    @PostMapping("/blacklist/access/")
    public Map<String,Object> blacklistAccess(@RequestHeader("Authorization") String accessToken){
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
            mainJwts.AddToBlackList(accessToken);
            return Map.of(
                    "data",Map.of(
                            "blacklisted",true
                    ),
                    "message","Access Token is blacklisted",
                    "status",200
            );
        }
        return Map.of(
                "data",Map.of(
                        "blacklisted",false
                ),
                "message","Access Token is invalid",
                "status",401
        );
    }
    @PostMapping("/blacklist/refresh/")
    public Map<String,Object> blacklistRefresh(@RequestParam("token") String refreshToken){
        RefreshTokens refreshTokens = tokenResposteyrs.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Refresh Token Not found"));
        tokenResposteyrs.delete(refreshTokens);
        return Map.of(
                "data",Map.of(
                        "blacklisted",true
                ),
                "message","Refresh Token is blacklisted",
                "status",200
        ); }
    @PostMapping("/user/account/logout/")
    public Map<String,Object> logoutUser(@RequestHeader("Authorization") String accessToken){
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
            String username = mainJwts.getUsernameFromAccessToken(accessToken);
            Optional<User> user = userReposteryes.findByUsername(username);
            RefreshTokens refreshTokens = tokenResposteyrs.findByUserUsername(user).orElseThrow(()->new RuntimeException("Refresh Token Not found"));
            tokenResposteyrs.delete(refreshTokens);
            mainJwts.AddToBlackList(accessToken);
            return Map.of(
                    "data",Map.of(
                            "loggedOut",true
                    ),
                    "message","User logged out successfully",
                    "status",200
            );
        }
        return Map.of(
                "data",Map.of(
                        "loggedOut",false
                ),
                "message","Access Token is invalid",
                "status",401
        );
    }
    @PostMapping("/get/user/from/access/")
    public Map<String,Object> getUserFromAccess(@RequestHeader("Authorization") String accessToken){
        if(accessToken.startsWith("Bearer ")){
            accessToken = accessToken.substring(7);
            String username = mainJwts.getUsernameFromAccessToken(accessToken);
            return Map.of(
                    "data",Map.of(
                            "username",username
                    ),
                    "message","Username retrieved successfully",
                    "status",200
            );
        }
        return Map.of(
                "data",Map.of(
                        "username",null
                ),
                "message","Access Token is invalid",
                "status",401
        );
    }
    
    
    

}
