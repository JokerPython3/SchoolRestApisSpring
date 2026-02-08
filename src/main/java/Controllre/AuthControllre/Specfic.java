package Controllre.AuthControllre;

import JwtsManager.MainJwts;
import Reposteryes.TokenResposteyrs;
import Reposteryes.UserReposteryes;
import Service.UserDateilsService;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/v1/")
public class Specfic {

    private final UserDateilsService userDateilsService;
    private UserReposteryes userReposteryes;
    private TokenResposteyrs tokenResposteyrs;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private MainJwts mainJwts;

    public Specfic(MainJwts mainJwts, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenResposteyrs tokenResposteyrs, UserReposteryes userReposteryes, UserDateilsService userDateilsService) {
        this.mainJwts = mainJwts;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenResposteyrs = tokenResposteyrs;
        this.userReposteryes = userReposteryes;
        this.userDateilsService = userDateilsService;
    }

    @PostMapping("/login/")
    public ResponseEntity<Map<String,Object>> LoginAtro(@RequestParam("username") String user,@RequestParam("password") String password,@RequestParam("classId") Long idLong,@RequestParam("ClassABC") String classABC){
        if(userReposteryes.findByUsername(user).isEmpty()){
            Map<String,Object> n = Map.of("data",Map.of("message","username not found"),"status",302);
            return ResponseEntity.status(302).body(n);
        }
        if(userReposteryes.findByClassId(idLong).isEmpty()){
            Map<String,Object> n = Map.of("data",Map.of("message","class id not found"),"status",302);
            return ResponseEntity.status(302).body(n)   ;
        }
        if(userReposteryes.findByClassABC(classABC).isEmpty()){
            Map<String,Object> n = Map.of("data",Map.of("message","class abc not found"),"status",302);
            return ResponseEntity.status(302).body(n);
        }
        try{
            var auth = new UsernamePasswordAuthenticationToken(user,password);
            var autha = authenticationManager.authenticate(auth);
            String acc = mainJwts.genAccessToken(user);
            String refresh= mainJwts.genRefreshTOken(user);
            if(userReposteryes.findById(idLong).get().getRole().equals("TEACHER")){
                return ResponseEntity.ok(Map.of("data",Map.of("message","Login Successfully","access",acc,"refresh",refresh,"teacher",true,"id",idLong),"status",200));
            }
            return ResponseEntity.ok(Map.of("data",Map.of("message","Login Successfully","access",acc,"refresh",refresh,"teacher",false,"id",idLong),"status",200));
        }
        catch (Exception e){
            return ResponseEntity.status(302).body(Map.of("data",Map.of("message","error"),"status",302));
        }

    }
    @PostMapping("/register/")
    public ResponseEntity<Map<String,Object>> RegisterAtro(@RequestParam("username") String user,@RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("classId") Long idLong,@RequestParam("ClassABC") String classABC){
        if(userReposteryes.findByUsername(user).isPresent()){

            return ResponseEntity.status(302).body(Map.of("data",Map.of("message","username is alerady exists "),"status",302));
        }
        if(userReposteryes.findByEmail(email).isPresent()){
            return ResponseEntity.status(302).body(Map.of("data",Map.of("message","email is alerdy exists"),"status",302));
          }
        model.User use = new model.User();
        use.setUsername(user);
        use.setEmail(email);
        use.setPassword(passwordEncoder.encode(password));
        use.setName(user);
        use.setClassABC(classABC);
        use.setClassId(idLong);
        use.setRole("USER");
        userReposteryes.save(use);
        return ResponseEntity.ok(Map.of("data",Map.of("message","Register Successfully"),"status",200));
    }
    // @Role("TEACHER")
    @PostMapping("/get/techaer/users/")
    public ResponseEntity<List<model.User>> getTeacherUsers(@RequestHeader("atro-token") String token){
        if(token.equals("")){
            return ResponseEntity.status(302).build();
        }else if(token.equals("atro123")){ // بعدين نقيره
List<model.User> users = userReposteryes.findByRole("TEACHER");
        return ResponseEntity.ok(users);
        }
        else{
            return ResponseEntity.status(302).build();
        }
        
    }



}
