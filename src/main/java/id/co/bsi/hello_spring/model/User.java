package id.co.bsi.hello_spring.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class User {
    private String fullName;
    private String email;
    private String passwordHash;
    private BigInteger phone;
    private String token;
}
