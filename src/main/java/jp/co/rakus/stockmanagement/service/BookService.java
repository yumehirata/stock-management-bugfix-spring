package jp.co.rakus.stockmanagement.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.co.rakus.stockmanagement.domain.Book;
import jp.co.rakus.stockmanagement.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 書籍関連サービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class BookService {

	@Autowired
	BookRepository bookRepository;

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public Book findOne(Integer id) {
		return bookRepository.findOne(id);
	}

	// public Book save(Book book){
	// return bookRepository.save(book);
	// }

	public Book update(Book book) {
		return bookRepository.update(book);
	}

	// public void delete(Integer id){
	// bookRepository.delete(id);
	// }

	public Book insert(Book book) {
		return bookRepository.insert(book);
	}

	/**
	 * 新規ID用にDB上の最大IDに1を足して返す.
	 * 
	 * @return	新規登録用書籍ID
	 */
	public Integer getNewId() {
		Integer maxId = bookRepository.maxId();

		if (maxId == null) {
			Integer getNewId = 1;
			return getNewId;
		}
		Integer getNewId = maxId + 1;
		return getNewId;
	}

	/**
	 * DBにインサートするためにDate型からString型に変換する.
	 * 
	 * @param saledate	フォームに入力された発売日を表す文字列
	 * @return	Date型に変換された発売日情報
	 */
	public Date stringToDate(String saledate) {
		Date saleDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			saleDate = format.parse(saledate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return saleDate;
	}
}
