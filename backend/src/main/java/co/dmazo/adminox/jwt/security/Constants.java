package co.dmazo.adminox.jwt.security;

public class Constants {
    // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    // JWT
    public static final String ISSUER_INFO = "http://localhost:9528";
    public static final String SUPER_SECRET_KEY = "4dM1n0x";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

}
