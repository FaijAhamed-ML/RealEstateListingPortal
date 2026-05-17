package org.sliitprojectspring.common.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class PasswordEncoder {


    public static  String hashPassword ( String plainPassword) {

        return BCrypt.hashpw(plainPassword, BCrypt.gensalt())
;
    }
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }





}
