package com.example.testLibrary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.testLibrary.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
Optional<Image> findById(Long id);
}
