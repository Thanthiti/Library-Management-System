package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryUserResponseDTO;
import com.example.thanthiti.Library.Management.System.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryCTRL {

    private final CategoryService categoryService;
    public CategoryCTRL(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("admin/categories/{id}")
    public ResponseEntity<CategoryAdminResponseDTO> getAdminCategoryById(@PathVariable Long id) {
        CategoryAdminResponseDTO categoryResponseDTO = categoryService.getAdminCategoryById(id);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryUserResponseDTO> getUserCategoryById(@PathVariable Long id) {
        CategoryUserResponseDTO categoryResponseDTO = categoryService.getUserCategoryById(id);
        return ResponseEntity.ok(categoryResponseDTO);
    }

}
