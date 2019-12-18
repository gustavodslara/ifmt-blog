package br.gus.ifmt.blog.sec;

import javax.validation.constraints.NotNull;
import java.util.Base64;

public class Login {
    public static final String USER = "adminifmt123";
    public static final String PASS = "adminifmt123";

    private Login() {}

    public static boolean isAuth(@NotNull String basicAuth) {
        return basicAuth.equals(authStatic());
    }

    public static String authStatic(){
        String token = USER + ":" + PASS;
        return "Basic " + new String(Base64.getEncoder().encode(token.getBytes()));
    }
}
