package Controllre.AuthControllre;

import JwtsManager.MainJwts;
import Reposteryes.TokenResposteyrs;
import Reposteryes.UserReposteryes;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@RequestMapping("/v1/")
public class Specfic {
    private UserReposteryes userReposteryes;
    private TokenResposteyrs tokenResposteyrs;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private MainJwts mainJwts;

    public Specfic(MainJwts mainJwts, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenResposteyrs tokenResposteyrs, UserReposteryes userReposteryes) {
        this.mainJwts = mainJwts;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenResposteyrs = tokenResposteyrs;
        this.userReposteryes = userReposteryes;
    }

    @PostMapping("/login/")
    public Map<String,Object> LoginAtro(@RequestParam("username") String user,@RequestParam("password") String password,@RequestParam("classId") Long idLong,@RequestParam("ClassABC") String classABC){
        if(userReposteryes.findByUsername(user).isEmpty()){
            Map<String,Object> n = Map.of("data",Map.of("message","username not found"),"status",302);
            return n;
        }
        if(userReposteryes.findByClassId(idLong).isEmpty()){
            Map<String,Object> n = Map.of("data",Map.of("message","class id not found"),"status",302);
            return n;
        }
        if(userReposteryes.findByClassABC(classABC).isEmpty()){
            Map<String,Object> n = Map.of("data",Map.of("message","class abc not found"),"status",302);
            return n;
        }
        try{
            var auth = new UsernamePasswordAuthenticationToken(user,password);
            var autha = authenticationManager.authenticate(auth);
            String acc = mainJwts.genAccessToken(user);
            String refresh= mainJwts.genRefreshTOken(user);
            return Map.of("data",Map.of("message","Login Successfully","access",acc,"refresh",refresh),"status",200);}
        catch (Exception e){
            return Map.of("data",Map.of("message","error"),"status",302);
        }

    }
    @PostMapping("/register/")
    public Map<String,Object> RegisterAtro(@RequestParam("username") String user,@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("classId") Long idLong,@RequestParam("ClassABC") String classABC){
        if(userReposteryes.findByUsername(user).isPresent()){

            return Map.of("data",Map.of("message","username  found"),"status",302);
        }
        if(userReposteryes.findByEmail(email).isPresent()){
            return  Map.of("data",Map.of("message","email  found"),"status",302);}
        model.User use = new model.User();
        use.setUsername(user);
        use.setEmail(email);
        use.setPassword(passwordEncoder.encode(password));
        use.setName(user);
        use.setClassABC(classABC);
        use.setClassId(idLong);
        use.setRole("USER");
        userReposteryes.save(use);
        return Map.of("data",Map.of("message","Register Successfully"),"status",200);
    }



}
