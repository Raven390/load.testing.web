package ru.develonica.load.testing.web.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenIdLogoutHandler implements LogoutHandler {
    private static final String OPEN_ID_LOGOUT_ENDPOINT = "/protocol/openid-connect/logout";
    private static final Logger LOG = LoggerFactory.getLogger(OpenIdLogoutHandler.class);
    public static final String ID_TOKEN_HINT = "id_token_hint";
    private final RestTemplate restTemplate;

    public OpenIdLogoutHandler() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        openIdLogout((OidcUser) authentication.getPrincipal());
    }

    private void openIdLogout(OidcUser user) {
        String endSessionEndpoint = user.getIssuer() + OPEN_ID_LOGOUT_ENDPOINT;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(endSessionEndpoint)
                .queryParam(ID_TOKEN_HINT, user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);

        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            LOG.info("User {} successfully logged out from openId provider", user.getPreferredUsername());
        } else {
            LOG.error("Could not propagate logout from openId provider: {}", logoutResponse.getBody());
        }
    }
}
