package org.rookie.model.dto;


import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDTO {
    Long id;
    String userName;
    String role;
    String avatar_url;
}
