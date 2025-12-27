package com.dttlibrary.controller.admin;

import com.dttlibrary.model.*;
import com.dttlibrary.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookImageService bookImageService;
    private final FileStorageService fileStorageService;
    private final BookItemService bookItemService;

    public AdminBookController(BookService bookService,
                               CategoryService categoryService,
                               AuthorService authorService,
                               BookImageService bookImageService,
                               FileStorageService fileStorageService,
                               BookItemService bookItemService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookImageService = bookImageService;
        this.fileStorageService = fileStorageService;
        this.bookItemService = bookItemService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "admin/books/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "admin/books/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Book book = bookService.findByIdWithItems(id);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book not found.");
            return "redirect:/admin/books";
        }
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "admin/books/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Book bookFromForm,
                       @RequestParam Integer categoryId,
                       @RequestParam(required = false) Integer authorId,
                       @RequestParam(required = false) MultipartFile image,
                       @RequestParam(defaultValue = "1") Integer initialQuantity,
                       RedirectAttributes redirectAttributes) {

        boolean isNewBook = bookFromForm.getId() == null;

        try {
            Book bookToSave = isNewBook ? new Book() : bookService.findById(bookFromForm.getId());
            if (bookToSave == null) {
                 throw new RuntimeException("Book not found for update.");
            }

            bookToSave.setTitle(bookFromForm.getTitle());
            bookToSave.setDescription(bookFromForm.getDescription());
            bookToSave.setIsbn(bookFromForm.getIsbn());

            Category category = categoryService.findById(categoryId);
            bookToSave.setCategory(category);

            if (authorId != null) {
                Author author = authorService.findById(authorId);
                bookToSave.setAuthor(author);
            } else {
                bookToSave.setAuthor(null);
            }

            Book savedBook = bookService.save(bookToSave);

            if (isNewBook && initialQuantity > 0) {
                for (int i = 0; i < initialQuantity; i++) {
                    BookItem newItem = new BookItem();
                    newItem.setBook(savedBook);
                    newItem.setBarcode(savedBook.getIsbn() + "-" + UUID.randomUUID().toString().substring(0, 4));
                    newItem.setStatus(BookItem.Status.available);
                    bookItemService.save(newItem);
                }
            }

            if (image != null && !image.isEmpty()) {
                String fileName = fileStorageService.store(image);
                BookImage img = new BookImage();
                img.setBook(savedBook);
                img.setUrl("/uploads/" + fileName);
                img.setIsPrimary(true);
                bookImageService.save(img);
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Book saved successfully.");
            return "redirect:/admin/books/edit/" + savedBook.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not save book: " + e.getMessage());
            return "redirect:" + (isNewBook ? "/admin/books/create" : "/admin/books/edit/" + bookFromForm.getId());
        }
    }

    /**
     * Thêm nhiều bản sao cho một sách đã có
     */
    @PostMapping("/add-copies/{bookId}")
    public String addCopies(@PathVariable Integer bookId,
                            @RequestParam(defaultValue = "1") Integer quantityToAdd,
                            RedirectAttributes redirectAttributes) {
        try {
            Book book = bookService.findById(bookId);
            if (book == null) {
                throw new RuntimeException("Book not found.");
            }
            if (quantityToAdd > 0) {
                for (int i = 0; i < quantityToAdd; i++) {
                    BookItem newItem = new BookItem();
                    newItem.setBook(book);
                    newItem.setBarcode(book.getIsbn() + "-" + UUID.randomUUID().toString().substring(0, 4));
                    newItem.setStatus(BookItem.Status.available);
                    bookItemService.save(newItem);
                }
                redirectAttributes.addFlashAttribute("successMessage", "Added " + quantityToAdd + " new copies.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not add copies: " + e.getMessage());
        }
        return "redirect:/admin/books/edit/" + bookId;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            bookService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not delete book. It might be in use.");
        }
        return "redirect:/admin/books";
    }
}
