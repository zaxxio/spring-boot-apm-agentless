package org.wsd.app.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private Long id;
    private String username;
    private String email;
}