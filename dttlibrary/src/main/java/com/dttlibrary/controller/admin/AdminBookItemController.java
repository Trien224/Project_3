package com.dttlibrary.controller.admin;

import com.dttlibrary.model.Book;
import com.dttlibrary.model.BookItem;
import com.dttlibrary.service.BookItemService;
import com.dttlibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/book-items")
public class AdminBookItemController {

    private final BookItemService itemService;
    private final BookService bookService;

    public AdminBookItemController(BookItemService itemService,
                                   BookService bookService) {
        this.itemService = itemService;
        this.bookService = bookService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "admin/book-items/list";
    }

    @GetMapping("/create")
    public String create(@RequestParam(required = false) Integer bookId, Model model) {
        BookItem item = new BookItem();
        if (bookId != null) {
            Book book = bookService.findById(bookId);
            if (book != null) {
                item.setBook(book);
            }
        }
        model.addAttribute("item", item);
        model.addAttribute("books", bookService.findAll());
        return "admin/book-items/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        BookItem item = itemService.findById(id);
        if (item == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book Item not found.");
            return "redirect:/admin/book-items";
        }
        model.addAttribute("item", item);
        model.addAttribute("books", bookService.findAll());
        return "admin/book-items/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BookItem item,
                       @RequestParam("bookId") Integer bookId,
                       RedirectAttributes redirectAttributes) {

        Book book = bookService.findById(bookId);
        if (book == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not save: Book not found for ID " + bookId);
            redirectAttributes.addFlashAttribute("item", item);
            return "redirect:/admin/book-items/create";
        }

        item.setBook(book);

        if (item.getStatus() == null) {
            item.setStatus(BookItem.Status.available);
        }

        try {
            itemService.save(item);
            redirectAttributes.addFlashAttribute("successMessage", "Book Item saved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not save book item. Barcode might already exist.");
            redirectAttributes.addFlashAttribute("item", item);
            if (item.getId() == null) {
                return "redirect:/admin/book-items/create?bookId=" + bookId;
            } else {
                return "redirect:/admin/book-items/edit/" + item.getId();
            }
        }
        // Chuyển hướng về trang sửa sách gốc
        return "redirect:/admin/books/edit/" + bookId;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, @RequestParam(required = false) Integer bookId, RedirectAttributes redirectAttributes) {
        try {
            itemService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book Item deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not delete book item. It might be in use.");
        }
        if (bookId != null) {
            return "redirect:/admin/books/edit/" + bookId;
        }
        return "redirect:/admin/book-items";
    }
}
