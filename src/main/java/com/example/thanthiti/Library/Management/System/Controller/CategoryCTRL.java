package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryUserResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminReqDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminResDTO;
import com.example.thanthiti.Library.Management.System.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

//    Admin Add Update Delete Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/categories")
    public ResponseEntity<PostCategoryAdminResDTO> addCategory(@Valid @RequestBody PostCategoryAdminReqDTO categoryAdminRequestDTO) {
        PostCategoryAdminResDTO categoryAdminResponseDTO = categoryService.addCategory(categoryAdminRequestDTO);
        return ResponseEntity.status(201).body(categoryAdminResponseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("admin/categories/{id}")
    public ResponseEntity<PostCategoryAdminResDTO> updateCategory(@PathVariable Long id, @RequestBody PostCategoryAdminReqDTO categoryAdminRequestDTO) {
        PostCategoryAdminResDTO categoryAdminResponseDTO = categoryService.updateCategory(id, categoryAdminRequestDTO);
        return ResponseEntity.ok(categoryAdminResponseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("admin/categories/{id}")
    public ResponseEntity<PostCategoryAdminResDTO> deleteCategory(@PathVariable Long id) {
        PostCategoryAdminResDTO deletedCategory = categoryService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategory);
    }

}
