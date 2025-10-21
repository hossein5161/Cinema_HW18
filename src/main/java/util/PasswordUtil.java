package util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static String encode(String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verify(String password, String hashPassword){
        return BCrypt.verifyer().verify(password.toCharArray(), hashPassword.toCharArray()).verified;
    }
}
