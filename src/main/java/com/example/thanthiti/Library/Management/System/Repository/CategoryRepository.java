package com.example.thanthiti.Library.Management.System.Repository;

import com.example.thanthiti.Library.Management.System.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
