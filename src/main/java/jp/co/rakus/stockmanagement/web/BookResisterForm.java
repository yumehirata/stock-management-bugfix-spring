package jp.co.rakus.stockmanagement.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 * 新規登録用のフォーム
 * 
 * @author yume.hirata
 *
 */
public class BookResisterForm {
	/** id */
	private Integer id;
	/** 書籍名 */
	@NotBlank(message = "書籍名を入力してください")
	private String name;
	/** 著者 */
	@NotBlank(message = "著者名を入力してください")
	private String author;
	/** 出版社 */
	@NotBlank(message = "出版社を入力してください")
	private String publisher;
	/** 価格 */
	@NotNull(message = "値を入力してください")
	private Integer price;
	/** ISBNコード */
	@NotBlank(message = "ISBNコードを入力してください")
	@Pattern(regexp = "^\\d{1}-\\d{4}-\\d{4}-\\d{1}$", message = "ハイフンを入れたISBNコードの形式で入力してください")
	private String isbncode;
	/** 発売日 */
	@NotBlank(message = "発売日を入力してください")
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "2000-01-01のような形式で入力してください")
	private String saledate;
	/** 説明 */
	@NotBlank(message = "説明を入力してください")
	private String explanation;
	/** 画像 */
	@NotNull(message = "画像を選択してください")
	private MultipartFile imageFile;
	/** 在庫数 */
	@NotNull(message = "在庫数を入力してください")
	private Integer stock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getIsbncode() {
		return isbncode;
	}

	public void setIsbncode(String isbncode) {
		this.isbncode = isbncode;
	}

	public String getSaledate() {
		return saledate;
	}

	public void setSaledate(String saledate) {
		this.saledate = saledate;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
