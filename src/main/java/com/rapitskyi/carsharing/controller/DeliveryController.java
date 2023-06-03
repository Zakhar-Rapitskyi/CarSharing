package com.rapitskyi.carsharing.controller;

import com.rapitskyi.carsharing.dto.response.InquireResponse;
import com.rapitskyi.carsharing.entity.Inquire;
import com.rapitskyi.carsharing.entity.User;
import com.rapitskyi.carsharing.entity.UserDetailsImpl;
import com.rapitskyi.carsharing.service.InquireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carsharing/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final InquireService inquireService;
    @GetMapping
    public ResponseEntity<List<InquireResponse>> getInquiresNeedsDelivery(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        return ResponseEntity.ok(
                inquireService.findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue().stream()
                .filter(i -> i.getVolunteer().getId() != user.getId())
                .map((i)->new InquireResponse(
                        i.getId(),
                        i.getVolunteer().getFirstname(),
                        i.getVolunteer().getLastname(),
                        i.getOwner().getPhone(),
                        i.getCarType(),
                        i.getDescription())).toList());
    }

    @PutMapping("/confirm")
    public void confirm(@RequestParam Long id){
        Inquire inquire = inquireService.findById(id);
        inquire.setNeedsDelivery(false);
        inquireService.save(inquire);
    }
}
