package Componte.WebSocket;

import JwtsManager.MainJwts;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final MainJwts mainJwts;

    public JwtHandshakeInterceptor(MainJwts mainJwts) {
        this.mainJwts = mainJwts;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {


        List<String> auth = request.getHeaders().get("Authorization");
        String token = null;
        if (auth != null && !auth.isEmpty()) {
            token = auth.get(0);
        }

        if (!StringUtils.hasText(token)) {
            URI uri = request.getURI();
            String t = UriComponentsBuilder.fromUri(uri).build().getQueryParams().getFirst("token");
            token = t;
             // اذا دز توكن كبارمس
        }

        if (!StringUtils.hasText(token)) {

            return false;
        }


        if (token.startsWith("Bearer ")) token = token.substring(7);

        boolean valid = false;
        try {
            valid = mainJwts.VerifyAccess(token);
        } catch (Exception e) {
            valid = false;
        }

        if (!valid) {
            return false;
        }


        try {
            String username = mainJwts.getUsernameFromAccessToken(token);
            if (StringUtils.hasText(username)) attributes.put("username", username);
        } catch (Exception ignored) {}

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }
}
// filter لل ويبسوكت ريكوست لانو جيتبلوي فيتلر عادي ميدعم مويبسكوت
