package com.labconnect.DTORequest.Identity;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}