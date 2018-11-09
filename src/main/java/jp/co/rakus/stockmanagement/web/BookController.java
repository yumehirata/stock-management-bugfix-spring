package jp.co.rakus.stockmanagement.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.stockmanagement.domain.Book;
import jp.co.rakus.stockmanagement.service.BookService;

/**
 * 書籍関連処理を行うコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/book")
@Transactional
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private ServletContext application;

	/**
	 * フォームを初期化します.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public BookForm setUpBookForm() {
		return new BookForm();
	}
	@ModelAttribute
	public BookResisterForm setUpBookResisterForm() {
		return new BookResisterForm();
	}

	/**
	 * 書籍リスト情報を取得し書籍リスト画面を表示します.
	 * 
	 * @param model
	 *            モデル
	 * @return 書籍リスト表示画面
	 */
	@RequestMapping(value = "list")
	public String list(Model model) {
		List<Book> bookList = bookService.findAll();
		model.addAttribute("bookList", bookList);
		return "book/list";
	}

	/**
	 * 書籍詳細情報を取得し書籍詳細画面を表示します.
	 * 
	 * @param id
	 *            書籍ID
	 * @param model
	 *            モデル
	 * @return 書籍詳細画面
	 */
	@RequestMapping(value = "show/{bookId}")
	public String show(@PathVariable("bookId") Integer id, Model model) {
		Book book = bookService.findOne(id);
		model.addAttribute("book", book);
		return "book/show";
	}

	/**
	 * 書籍更新を行います.
	 * 
	 * @param form
	 *            フォーム
	 * @param result
	 *            リザルト情報
	 * @param model
	 *            モデル
	 * @return 書籍リスト画面
	 */
	@RequestMapping(value = "update")
	public String update(@Validated BookForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return show(form.getId(), model);
		}
		Book book = bookService.findOne(form.getId());
		book.setStock(form.getStock());
		bookService.update(book);
		return list(model);
	}

	/**
	 * 書籍追加を行います.
	 * 
	 * @return 書籍リスト画面
	 */
	@RequestMapping(value = "insert")
	public String insert(@Validated BookResisterForm form, BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			return toInsert(model);
		}

		String fileName = form.getImageFile().getOriginalFilename();
		File imgFile = new File(application.getRealPath("/img/" + fileName));

		try {
			form.getImageFile().transferTo(imgFile);
		} catch (IOException e) {
			System.out.println(e);
		}

		Book book = new Book();
		BeanUtils.copyProperties(form, book);
		book.setId(bookService.getNewId());
		book.setSaledate(bookService.stringToDate(form.getSaledate()));
		book.setImage(fileName);
		bookService.insert(book);

		return "forward:/book/list";
	}

	/**
	 * 新規登録用ページを表示する.
	 * 
	 * @param model	エラー表示時用の変数
	 * @return	新規登録用ページ
	 */
	@RequestMapping(value = "toInsert")
	public String toInsert(Model model) {
		return "book/insert";
	}

}
