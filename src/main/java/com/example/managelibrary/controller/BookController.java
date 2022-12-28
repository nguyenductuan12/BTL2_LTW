package com.example.managelibrary.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.managelibrary.dto.UserDto;
import com.example.managelibrary.entity.Book;
import com.example.managelibrary.entity.Order;
import com.example.managelibrary.entity.Review;
import com.example.managelibrary.entity.User;
import com.example.managelibrary.service.BookService;
import com.example.managelibrary.service.OrderService;
import com.example.managelibrary.service.ReviewService;
import com.example.managelibrary.service.UserService;

@Controller
@SessionAttributes("userdto")
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired 
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private OrderService orderService;
	
	@ModelAttribute("userdto")
	public UserDto userDto() {
		return new UserDto();
	}

	@GetMapping("/books")
	public String listbooks(@ModelAttribute("userdto") UserDto userDto, Model model) {
		model.addAttribute("books", bookService.getAllBooks());
		return "books";
	}

	// view book
	@GetMapping("/books/view/{id}")
	public String viewBookForm(@ModelAttribute("userdto") UserDto userDto,@PathVariable Long id, Model model) {
		Book book = bookService.getBookById(id);
		model.addAttribute("book",book );
		if(userDto.getRole()!=null) {
			if(userDto.getRole().equals("ROLE_USER")) {
				Review review = new Review();
				review.setBook(book);	
				Order order = new Order();
				order.setBook(book);
				model.addAttribute("review",review);
				model.addAttribute("order",order);
				return "user_view_book";
			}
			else 
				return "view_book";
		}else {
			return "redirect:/login";
		}
		
	}

	// new book
	@GetMapping("/books/new")
	public String createBookForm(Model model) {
		Book book = new Book();
		model.addAttribute("book", book);
		return "save_book";
	}

	@PostMapping("/books")
	public String saveBook(@ModelAttribute("book") @Valid Book book, BindingResult result, @RequestParam(name = "fileImage",required = false) MultipartFile multipartFile)
			throws IOException {
		if (result.hasErrors()) {
            return "save_book";
        }

		if(!multipartFile.isEmpty()  ) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			book.setPhotos(fileName);
			bookService.saveBook(book);
			String uploadDir = "./book-photos/" + book.getId();
	
			Path uploadPath = Paths.get(uploadDir);
	
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
	
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save image file: " + fileName, ioe);
			}
		}else {
			bookService.saveBook(book);
		}
		return "redirect:/books";
	}

	@GetMapping("/book/image/{id}")
	public String deleteImage(@PathVariable Long id) {
		Book existingBook = bookService.getBookById(id);
		existingBook.setPhotos(null);
		bookService.updateBook(existingBook);
		return "redirect:/books/edit/"+existingBook.getId();
	}
	// update book
	@GetMapping("/books/edit/{id}")
	public String editBookForm(@PathVariable Long id, Model model) {
		model.addAttribute("book", bookService.getBookById(id));
		return "save_book";
	}

	@PostMapping("/books/{id}")
	public String updateBook(@PathVariable Long id, @ModelAttribute("book") @Valid Book book,BindingResult result,
			@RequestParam("fileImage") MultipartFile multipartFile, Model model) throws IOException, ParseException {
		if (result.hasErrors()) {
            return "save_book";
        }
		Book existingBook = bookService.getBookById(id);
		existingBook.setId(id);
		existingBook.setTitle(book.getTitle());
		existingBook.setAuthor(book.getAuthor());
		existingBook.setDescribes(book.getDescribes());
		existingBook.setReleaseDate(book.getReleaseDate());
		existingBook.setNumberOfPages(book.getNumberOfPages());
		existingBook.setCategory(book.getCategory());
		existingBook.setPrice(book.getPrice());
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			existingBook.setPhotos(fileName);
			String uploadDir = "./book-photos/" + existingBook.getId();
	
			Path uploadPath = Paths.get(uploadDir);
	
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save image file: " + fileName, ioe);
			}
		}
		bookService.updateBook(existingBook);
		return "redirect:/books";
	}

	@GetMapping("/books/{id}")
	public String deleteBook(@PathVariable Long id) {
		bookService.deleteBookById(id);
		return "redirect:/books";
	}
	
	@PostMapping("/book/review")
	public String sendReview(@ModelAttribute("review") Review review,@ModelAttribute("userdto") UserDto userDto) {
		review.setUser(userService.getUserbyEmail(userDto.getEmail()));
		review.setDatePost(new Date());
		reviewService.create(review);
		return "redirect:/books/view/"+ review.getBook().getId();
	}
	
	@PostMapping("book/order")
	public String orderBook(@ModelAttribute("order") Order order,@ModelAttribute("userdto") UserDto userDto) {
		if (order.getQuantity()==null||order.getQuantity() < 1) {
			return "redirect:/books/view/"+ order.getBook().getId()+"?error";
        }
		order.setUser(userService.getUserbyEmail(userDto.getEmail()));
		order.setPurchaseDate(new Date());
		orderService.save(order);
		return "redirect:/books/view/"+ order.getBook().getId()+"?success";
	}
	
	@GetMapping("book/view-orders")
	public String viewOrder(@ModelAttribute("userdto") UserDto userDto, Model model) {
		if(userDto.getRole()!=null) {
			if(userDto.getRole().equals("ROLE_USER")) {
				model.addAttribute("orders", orderService.getAllOrders());
				return "list_order_book";
			}
			else 
				return "redirect:/login";
		}else {
			return "redirect:/login";
		}
	}

	@GetMapping("/books/cancel-order/{id}")
	public String deleteItem(@PathVariable("id") Long id) {
		orderService.deleteOrderById(id);
		return "redirect:/book/view-orders";
	}

}
