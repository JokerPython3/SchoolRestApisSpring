package Controllre;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class X {
    @GetMapping("/")
    public String requestMethodName() {
        return "index";
        
    }
    
    
}
