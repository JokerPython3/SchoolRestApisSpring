package Componte.JwtSFilter;

import JwtsManager.MainJwts;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final MainJwts mainJwts;

    public JwtHandshakeInterceptor(MainJwts mainJwts) {
        this.mainJwts = mainJwts;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {


//        String authHeader = request.getHeaders().getFirst("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            if (mainJwts.VerifyAccess(token)) {
//
//                String username = mainJwts.getUsernameFromAccessToken(token);
//                attributes.put("username", username);
//                return true;
//            }
//        }
//
//
//        response.setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
//        return false;
        String query = request.getURI().getQuery(); // token=XXXX&t=...
        String token = null;
        if(query != null) {
            for(String param : query.split("&")) {
                if(param.startsWith("token=")) {
                    token = param.substring(6);
                    break;
                }
            }
        }

        if(token != null && mainJwts.VerifyAccess(token)) {
            String username = mainJwts.getUsernameFromAccessToken(token);
            attributes.put("username", username);
            return true;
        }

        response.setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);
        return false;

    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {

    }
    // filter عادي مالتنا ميقبل ws بس http عادي فسيونا هذا فلتر
}
