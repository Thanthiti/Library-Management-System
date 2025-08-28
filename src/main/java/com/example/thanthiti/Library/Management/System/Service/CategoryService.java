package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryUserResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminReqDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminResDTO;
import com.example.thanthiti.Library.Management.System.Entity.Category;
import com.example.thanthiti.Library.Management.System.Exeption.GlobalException;
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

        CategoryAdminResponseDTO categoryResponseDTO = new CategoryAdminResponseDTO();
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setBookCount(category.getBooks().size());

        List<BookResponseDTO> bookResponseDTOs = category.getBooks().stream()
                .map(book -> {
                    BookResponseDTO bookResponseDTO = new BookResponseDTO();
                    bookResponseDTO.setTitle(book.getTitle());
                    bookResponseDTO.setAuthor(book.getAuthor());
                    bookResponseDTO.setDescription(book.getDescription());
                    return bookResponseDTO;
                    })
                    .toList();

        categoryResponseDTO.setBooks(bookResponseDTOs);

        return categoryResponseDTO;
    }

    public CategoryUserResponseDTO getUserCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));

        CategoryUserResponseDTO categoryResponseDTO = new CategoryUserResponseDTO();
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setBookCount(category.getBooks().size());

        return categoryResponseDTO;
    }

    public PostCategoryAdminResDTO addCategory(PostCategoryAdminReqDTO categoryAdminRequestDTO) {
            String name = categoryAdminRequestDTO.getName().trim();

            if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new GlobalException.CategoryAlreadyExistsException("Category already exists");
         }
            Category category = new Category();
            category.setName(categoryAdminRequestDTO.getName());
            categoryRepository.save(category);

            PostCategoryAdminResDTO categoryResponseDTO = new PostCategoryAdminResDTO(
                    category.getId(),
                    category.getName(),
                    "Category added successfully"
            );
            categoryResponseDTO.setId(category.getId());
            categoryResponseDTO.setName(category.getName());
            categoryResponseDTO.setMessage("Category addedqq successfully");
            return categoryResponseDTO;
    }

    public PostCategoryAdminResDTO updateCategory(Long id, PostCategoryAdminReqDTO categoryAdminRequestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        category.setName(categoryAdminRequestDTO.getName());

        categoryRepository.save(category);

        PostCategoryAdminResDTO categoryResponseDTO = new PostCategoryAdminResDTO(
                category.getId(),
                category.getName(),
                "Category updated successfully"
        );
        categoryResponseDTO.setId(category.getId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setMessage("Category updatedwe successfully");

        return categoryResponseDTO ;
    }

    public PostCategoryAdminResDTO deleteCategory(Long id) {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
            categoryRepository.delete(category);
        PostCategoryAdminResDTO categoryResponseDTO = new PostCategoryAdminResDTO(
                category.getId(),
                category.getName(),
                "Category deleted successfully"
        );
        return categoryResponseDTO;
    }

}
