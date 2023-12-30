package project.guakamole.global.auth.security;

public class Constants {
    /**
     * 권한제외 대상
     *
     * @see SecurityConfig
     */
    public static final String[] permitAllArray = new String[]{
            "/",
            "/auth/**",
    };

}