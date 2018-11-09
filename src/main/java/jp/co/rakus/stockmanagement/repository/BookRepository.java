package jp.co.rakus.stockmanagement.repository;

import java.util.Date;
import java.util.List;

import jp.co.rakus.stockmanagement.domain.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * booksテーブル操作用のリポジトリクラス.
 * @author igamasayuki
 */
@Repository
public class BookRepository {
	/**
	 * ResultSetオブジェクトからBookオブジェクトに変換するためのクラス実装&インスタンス化
	 */
	private static final RowMapper<Book> BOOK_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String author = rs.getString("author");
		String publisher = rs.getString("publisher");
		Integer price = rs.getInt("price");
		String isbncode = rs.getString("isbncode");
		Date saledate = rs.getDate("saledate");
		String explanation = rs.getString("explanation");
		String image = rs.getString("image");
		Integer stock = rs.getInt("stock");
		return new Book(id, name, author, publisher, price, isbncode, saledate, explanation, image, stock);
	};
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<Book> findAll() {
		List<Book> books = jdbcTemplate.query(
				"SELECT id,name,author,publisher,price,isbncode,saledate,explanation,image,stock FROM books ORDER BY name", 
				BOOK_ROW_MAPPER);
		return books;
	}
	
	public Book findOne(Integer id) {
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("id",id);
		Book book = jdbcTemplate.queryForObject(
				"SELECT id,name,author,publisher,price,isbncode,saledate,explanation,image,stock FROM books WHERE id=:id", 
				param, 
				BOOK_ROW_MAPPER);
		return book;
	}
	
	/**
	 * 書籍の在庫数をアップデートする.
	 * 
	 * @param book	反映させる書籍情報
	 * @return	反映させた書籍情報
	 */
	public Book update(Book book) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		if (book.getId() == null) {
			throw new NullPointerException();
		} 
		jdbcTemplate.update(
				"UPDATE books SET stock=:stock WHERE id=:id",
				param);
		return book;
	}
	
	
	
	/**
	 * 新規書籍を一覧に追加する.
	 * 
	 * @param book	新規追加する書籍情報
	 * @return	追加した書籍情報
	 */
	public Book insert(Book book) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(book);
		jdbcTemplate.update("INSERT INTO books(id,name,author,publisher,price,isbncode,saledate,explanation,image,stock)"
				+ " VALUES(:id,:name,:author,:publisher,:price,:isbncode,:saledate,:explanation,:image,:stock)", param);
		
		return book;
	}
	
	/**
	 * DB上のIDの最大値を検索する.
	 * 
	 * @return	IDの最大値
	 */
	public Integer maxId() {
		SqlParameterSource param = new MapSqlParameterSource();		
		Integer maxId = jdbcTemplate.queryForObject("SELECT max(id) FROM books;", param, Integer.class);
		
		if(maxId == null) {
			return null;
		}
		return maxId;
	}
}
