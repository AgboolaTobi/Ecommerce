package com.task1.ecommerce.utils;


import com.task1.ecommerce.exceptions.InvalidPhoneNumberException;
import org.springframework.stereotype.Component;

@Component
public class Verification {
    public static boolean verifyEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    public static boolean verifyPassword(String password) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\\S";
        return password.matches(regex);
    }

    public static boolean verifyPhoneNumber(String request) throws InvalidPhoneNumberException {
        if (request.length() != 11) {
            throw new InvalidPhoneNumberException("Phone number must be exactly 11 characters");
        }
        String regex = "^(0)([789])([01])[0-9]{8}$";
        return request.matches(regex);
    }
    public static boolean verifyName(String name) {
        String regex = "^[a-zA-Z\\s]+$";
        return name.matches(regex);
    }

}


