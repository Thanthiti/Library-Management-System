package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryUserResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminReqDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminResDTO;
import com.example.thanthiti.Library.Management.System.Entity.Category;
import com.example.thanthiti.Library.Management.System.Exeption.GlobalException;
import com.example.thanthiti.Library.Management.System.Mapper.CategoryMapper;
import com.example.thanthiti.Library.Management.System.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
        private final CategoryRepository categoryRepository;
        public CategoryService(CategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }

    public CategoryAdminResponseDTO getAdminCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));

        return CategoryMapper.toAdminDTO(category);
    }

    public CategoryUserResponseDTO getUserCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));

        return CategoryMapper.toUserDTO(category);
    }

    public PostCategoryAdminResDTO addCategory(PostCategoryAdminReqDTO categoryAdminRequestDTO) {
            String name = categoryAdminRequestDTO.getName().trim();

            if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new GlobalException.CategoryAlreadyExistsException("Category already exists");
         }
            Category category = new Category();
            category.setName(categoryAdminRequestDTO.getName());
            categoryRepository.save(category);

            return CategoryMapper.toResDTO(category, "Category added successfully");
    }

    public PostCategoryAdminResDTO updateCategory(Long id, PostCategoryAdminReqDTO categoryAdminRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        category.setName(categoryAdminRequestDTO.getName());

        categoryRepository.save(category);

        return CategoryMapper.toResDTO(category,"Category updated successfully") ;
    }

    public PostCategoryAdminResDTO deleteCategory(Long id) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
            categoryRepository.delete(category);

        return CategoryMapper.toResDTO(category,"Category deleted successfully");
    }

}
