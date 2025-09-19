package com.example.mechat.util;

public class ValidationUtil {
    public static boolean isInputValid(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean arePasswordsMatching(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}