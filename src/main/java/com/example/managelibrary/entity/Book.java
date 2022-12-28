package com.example.managelibrary.entity;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Request to enter title")
	@Column(name = "title", nullable = false)
	private String title;

	@NotBlank(message = "Request to enter author")
	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "describes", nullable = false)
	private String describes;

	@NotBlank(message = "Request to enter release date")
	@Column(name = "release_date", nullable = false)
	private String releaseDate;

	@Min(value = 1 , message = "Value should be greater then then equal to 1")
	@Column(name = "number_of_pages", nullable = false)
	private int numberOfPages;

	@Column(name = "category", nullable = false)
	private String category;

	@Column(nullable = true, length = 64)
	private String photos;
	
	@Min(value = 1 , message = "Value should be greater then then equal to 1")
	@NotNull
	@Column(name="price", nullable = false)
	private Float price;
	
	@OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "book")
    private List<Order> orders = new ArrayList<>();
	
	
	
	
	public Book() {
		super();
	}

	public Book(Long id, @NotBlank(message = "Request to enter title") String title,
			@NotBlank(message = "Request to enter author") String author, String describes,
			@NotBlank(message = "Request to enter release date") Date releaseDate, int numberOfPages, String category,
			String photos, List<Review> reviews, List<Order> orders, Float price) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.describes = describes;
		this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").format(releaseDate);
		this.numberOfPages = numberOfPages;
		this.category = category;
		this.photos = photos;
		this.reviews = reviews;
		this.orders = orders;
		this.price = price;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Transient
	public String getPhotosImagePath() {
		if (photos == null || id == null)
			return null;

		return "/book-photos/" + id + "/" + photos;
	}
}
