package com.task1.ecommerce.utils;


import org.springframework.stereotype.Component;

@Component
public class Verification {
    public static boolean verifyRegistrationEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        return email.matches(regex);
    }

    public static boolean verifyRegistrationPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";
        return password.matches(regex);
    }

    public static boolean verifyPhoneNumber(String phoneNumber) {
        String regex = "^(0)([789])([01])[0-9]{8}$";
        return phoneNumber.matches(regex);
    }

    public static boolean verifyRegistrationName(String name) {
        String regex = "^[a-zA-Z\\s]+$";
        return name.matches(regex);
    }

    public static boolean verifyStoreName(String storeName) {
        String regex = "^[a-zA-Z0-9\\s]{3,50}$";
        return storeName.matches(regex);
    }

    public static boolean verifyStoreDescription(String storeDescription) {
        String regex = "^[a-zA-Z0-9\\s]{10,200}$";
        return storeDescription.matches(regex);
    }
}






