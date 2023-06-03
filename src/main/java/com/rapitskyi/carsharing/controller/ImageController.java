package com.rapitskyi.carsharing.controller;

import com.rapitskyi.carsharing.dto.response.UserProfileResponse;
import com.rapitskyi.carsharing.entity.Car;
import com.rapitskyi.carsharing.entity.User;

import com.rapitskyi.carsharing.exception.BadRequestException;
import com.rapitskyi.carsharing.exception.ServerErrorException;
import com.rapitskyi.carsharing.mapper.UserProfileMapper;
import com.rapitskyi.carsharing.service.CarService;
import com.rapitskyi.carsharing.service.ImageService;
import com.rapitskyi.carsharing.entity.UserDetailsImpl;
import com.rapitskyi.carsharing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("carsharing/images")
public class ImageController {
    @Value("${image.url}")

    private String imageUrl;
    private final ImageService imageService;
    private final UserService userService;
    private final CarService carService;
    @PostMapping("/uploadUserImage")
    public ResponseEntity<?> uploadUserImage(@RequestParam("image") MultipartFile file) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        imageService.uploadImage(file);
        user.setImageData(imageService.getImageData(file.getOriginalFilename()));
        user.setImageURL(imageUrl + user.getImageData().getName());

        userService.save(user);

        UserProfileResponse userProfileResponse =  new UserProfileMapper().mapToUserProfile(user);
        return ResponseEntity.ok(userProfileResponse);
    }
    @PostMapping("/uploadCarImage")
    public ResponseEntity<?> uploadCarImage(@RequestParam("image") MultipartFile file,
                                            @RequestParam("id")Long id) throws IOException {
        Pattern pattern = Pattern.compile("\\p{InCyrillic}+");
        if (pattern.matcher(file.getOriginalFilename()).find()){
            throw new BadRequestException("Filename must contain only english letters");
        }

        Car car = carService.findById(id);
        try{
        car.setImageData(imageService.getImageData(file.getOriginalFilename()));
        car.setImageURL(imageUrl + car.getImageData().getName());
        carService.save(car);
        }catch (Exception e) {
            throw new ServerErrorException("Application can not insert this data to database");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName){
        byte[] imageData=imageService.getImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
