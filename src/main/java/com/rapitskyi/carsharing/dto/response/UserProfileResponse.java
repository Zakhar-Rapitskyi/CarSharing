package com.rapitskyi.carsharing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String imageURL;
    private Boolean isVolunteer;

}
