package jp.co.rakus.stockmanagement.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.stockmanagement.domain.Member;

/**
 * membersテーブル操作用のリポジトリクラス.
 * 
 * @author igamasayuki
 */
@Repository
public class MemberRepository {

	/**
	 * ResultSetオブジェクトからMemberオブジェクトに変換するためのクラス実装&インスタンス化
	 */
	private static final RowMapper<Member> MEMBER_ROW_MAPPER = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String mailAddress = rs.getString("mail_address");
		String password = rs.getString("password");
		return new Member(id, name, mailAddress, password);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * メールアドレスとパスワードからメンバーを取得.
	 * 
	 * @param mailAddress
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 * @return メンバー情報.メンバーが存在しない場合はnull.
	 */
	public Member findByMailAddressAndPassword(String mailAddress, String password) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		Member member = null;
		try {
			member = jdbcTemplate.queryForObject(
					"SELECT id,name,mail_address,password FROM members WHERE mail_address=:mailAddress and password=:password",
					param, MEMBER_ROW_MAPPER);
			return member;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * メンバー情報を保存 または 更新する.
	 * 
	 * @param member
	 *            保存または更新するメンバー情報
	 * @return 保存または更新されたメンバー情報
	 */
	public Member save(Member member) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(member);
		if (member.getId() == null) {
			jdbcTemplate.update("INSERT INTO members(name,mail_address,password) values(:name,:mailAddress,:password)",
					param);
		} else {
			jdbcTemplate.update(
					"UPDATE members SET name=:name,mail_address=:mailAddress,password=:password WHERE id=:id", param);
		}
		return member;
	}

	
	/**
	 * メールアドレスの重複を調べる.
	 * 
	 * @param mailAddress	入力されたメールアドレス
	 * @return	検索されたメンバーを返す
	 */
	public Member findMailAddress(String mailAddress) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		Member member = new Member();
		List<Member> memberList = new ArrayList<>();
		try {
			memberList = jdbcTemplate.query(
					"SELECT id,name,mail_address,password FROM members WHERE mail_address=:mailAddress", param,
					MEMBER_ROW_MAPPER);
			
			if(memberList.size()==0) {
				return null;
			}
			
			return member;
		} catch (DataAccessException e) {
			e.printStackTrace();

			return null;
		}
	}
}
