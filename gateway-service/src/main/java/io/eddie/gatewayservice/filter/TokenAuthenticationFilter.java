package io.eddie.gatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eddie.gatewayservice.dto.TokenAuthorizationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Optional;


@Component
public class TokenAuthenticationFilter extends AbstractGatewayFilterFactory<TokenAuthenticationFilter.Config> {

    private final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    private final ObjectMapper om = new ObjectMapper();

    public static class Config {


    }

    public TokenAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if ( !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)
              && !request.getCookies().containsKey("KNU-TOKEN") ) {
                log.error("Token is empty or cookie is empty");

                return response.writeWith(
                        Flux.just(
                                writeUnauthorizedResponseBody(response)
                        )
                );
            }

            Optional<String> tokenOptional = resolveToken(request);

//            tokenOptional.orElseThrow(() -> new IllegalStateException("Token is empty"));
            if ( tokenOptional.isEmpty() ) {
                return response.writeWith(
                        Flux.just(
                                writeUnauthorizedResponseBody(response)
                        )
                );
            }

            String knuToken = tokenOptional.get();




            return chain.filter(exchange);
        };

    }

    private DataBuffer writeUnauthorizedResponseBody(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

        TokenAuthorizationResponse body = new TokenAuthorizationResponse("인증이 필요합니다.");

        byte[] bytes = writeResponseBody(body);

        return response.bufferFactory().wrap(bytes);
    }

    private byte[] writeResponseBody(TokenAuthorizationResponse body) {
        try {
            return om.writeValueAsBytes(body);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize response body : {}", body);
            return new byte[0];
        }
    }

    private Optional<String> resolveToken(ServerHttpRequest request) {

        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
            return Optional.of(bearerToken.substring(7));
        }

        HttpCookie knuTokenCookie = request.getCookies().getFirst("KNU-TOKEN");
        if ( knuTokenCookie != null ) {
            return Optional.of(knuTokenCookie.getValue());
        }

        return Optional.empty();
    }












}
