package com.personal.searchsavvy.dto;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String userName;
    private Short userAge;
    private String userMobile;
    private String userEmail;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
