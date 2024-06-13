package com.lps.ldtracker.model;

public record UpdatePasswordRequest(String password, String email, String otp) {

}
