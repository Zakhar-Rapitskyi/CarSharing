package com.rapitskyi.carsharing.repository;

import com.rapitskyi.carsharing.entity.Inquire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InquireRepository extends JpaRepository<Inquire,Long> {
    List<Inquire> findAllByOwnerIsNull();
    List<Inquire> findAllByOwnerIsNotNullAndNeedsDeliveryIsTrue();
}
