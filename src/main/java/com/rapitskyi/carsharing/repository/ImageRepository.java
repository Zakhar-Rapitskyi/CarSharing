package com.rapitskyi.carsharing.repository;


import com.rapitskyi.carsharing.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageData,Long> {
    Optional<List<ImageData>> findAllByName(String fileName);
}
