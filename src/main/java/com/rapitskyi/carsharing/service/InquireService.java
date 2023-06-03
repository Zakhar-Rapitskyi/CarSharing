package com.rapitskyi.carsharing.service;

import com.rapitskyi.carsharing.entity.Inquire;
import com.rapitskyi.carsharing.exception.BadRequestException;
import com.rapitskyi.carsharing.repository.InquireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InquireService {
    private final InquireRepository inquireRepository;

    public Inquire findById(Long id){
        return inquireRepository.findById(id).orElseThrow(
                ()->new BadRequestException("There is no inquire with id " + id));}

    public Inquire save(Inquire inquire){
        return inquireRepository.save(inquire);
    }

    public List<Inquire> findAllByOwnerIsNull(){
        return inquireRepository.findAllByOwnerIsNull();
    }
    public List<Inquire> findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue(){
        return inquireRepository.findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue();
    }

}
