package com.task1.ecommerce.utils;


import org.springframework.stereotype.Component;

@Component
public class Verification {
    public static boolean verifyRegistrationEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return !email.matches(regex);
    }

    public static boolean verifyRegistrationPassword(String password) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\\S";
        return !password.matches(regex);
    }

    public static boolean verifyPhoneNumber(String phoneNumber) {
        String regex = "^(0)([789])([01])[0-9]{8}$";
        return !phoneNumber.matches(regex);
    }
    public static boolean verifyRegistrationName(String name) {
        String regex = "^[a-zA-Z\\s]+$";
        return !name.matches(regex);
    }

}


