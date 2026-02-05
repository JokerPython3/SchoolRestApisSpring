package Componte.JwtSFilter;

import JwtsManager.MainJwts;
import Service.UserDateilsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtsFilters extends OncePerRequestFilter {
    private MainJwts mainJwts;
    private UserDateilsService userDateilsService;
    public JwtsFilters(MainJwts mainJwts, UserDateilsService userDateilsService) {
        this.userDateilsService = userDateilsService;
        this.mainJwts = mainJwts;
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        String path = request.getRequestURI();
//
//        if (path.equals("/login/") || path.equals("/register/")) {
//
//            return true;
//        }
//        return false;
//
//
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        // System.out.println(request.getRequestURL());
        if(auth != null && auth.startsWith("Bearer ")){
            String token = auth.substring(7);
            if(mainJwts.VerifyAccess(token) && !mainJwts.isBlacklisted(token)){
            
                System.out.print(mainJwts.VerifyAccess(token));
                String username = mainJwts.getUsernameFromAccessToken(token);
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails =userDateilsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    // secuirty context holder بي شرط فوق نتاكد هل مستخدم مصادق لو لا اذا مو مصادق سويله مصادقه وطبله
                }

            }
        }

        filterChain.doFilter(request,response);

    }
}
