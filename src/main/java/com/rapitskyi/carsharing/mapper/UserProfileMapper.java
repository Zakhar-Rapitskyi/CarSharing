package com.rapitskyi.carsharing.mapper;

import com.rapitskyi.carsharing.dto.response.UserProfileResponse;
import com.rapitskyi.carsharing.entity.User;


public class UserProfileMapper {

    public static UserProfileResponse mapToUserProfile(User user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setPhone(user.getPhone());
        userProfileResponse.setFirstName(user.getFirstname());
        userProfileResponse.setLastName(user.getLastname());
        if(user.getImageData()!=null)
            userProfileResponse.setImageURL(user.getImageURL());
        userProfileResponse.setIsVolunteer(user.isVolunteer());
        return userProfileResponse;
    }
}
