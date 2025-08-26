package com.example.thanthiti.Library.Management.System.Seeder;

import com.example.thanthiti.Library.Management.System.Entity.Book;
import com.example.thanthiti.Library.Management.System.Entity.Category;
import com.example.thanthiti.Library.Management.System.Repository.BookRepository;
import com.example.thanthiti.Library.Management.System.Repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;

@Configuration
public class DataSeeder {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final CategoryRepository categoriesRepository;
    private final BookRepository bookRepository;

    public DataSeeder(CategoryRepository categoriesRepository, BookRepository bookRepository) {
        this.categoriesRepository = categoriesRepository;
        this.bookRepository = bookRepository;
    }

    @Bean
    CommandLineRunner initBookAndCategoryMockDatabase() {
        return args -> {
            if (categoriesRepository.count() == 0) {
                List<Category> categories = List.of(
                        new Category("Science Fiction"),
                        new Category("Fantasy"),
                        new Category("Mystery"),
                        new Category("Romance"),
                        new Category("Horror"),
                        new Category("Non-Fiction"),
                        new Category("Historical Fiction"),
                        new Category("Thriller"),
                        new Category("Biography"),
                        new Category("Self-Help")
                );
                categoriesRepository.saveAll(categories);
            }

            if (bookRepository.count() == 0) {
                List<Category> categories = categoriesRepository.findAll();
                for (int i = 0; i < 50; i++) {
                    String title = faker.book().title() + " " + faker.number().digits(5);
                    String author = faker.book().author();
                    String description = faker.lorem().sentence(15);
                    Category category = categories.get(random.nextInt(categories.size()));
                    Book book = new Book(title, author,description ,category, true);
                    bookRepository.save(book);
                }
            }
        };
    }

}
