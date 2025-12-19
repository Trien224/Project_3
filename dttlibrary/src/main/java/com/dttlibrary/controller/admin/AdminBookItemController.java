package com.dttlibrary.controller.admin;

import com.dttlibrary.model.BookItem;
import com.dttlibrary.service.BookItemService;
import com.dttlibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("content", "admin/book-items/list");
        return "admin/admin-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("item", new BookItem());
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("content", "admin/book-items/form");
        return "admin/admin-layout";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer bookId,
                       @ModelAttribute BookItem item) {

        item.setBook(bookService.findById(bookId));

        // nếu status null thì set mặc định (ENUM)
        if (item.getStatus() == null) {
            item.setStatus(BookItem.Status.available);
        }

        itemService.save(item);
        return "redirect:/admin/book-items";
    }

}

