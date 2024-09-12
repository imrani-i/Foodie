package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.MemberDTO;
import dto.PostDTO;
import util.DBUtil;
import util.DateUtil;

public class MemberDAO {

	Connection conn; // DB연결
	Statement st; // sql 코드 전송
	PreparedStatement pst; // Statement 를 상속 받음, (?)바인딩 변수 지원
	ResultSet rs; // 결과 반환
	
	// 포스팅 삭제
	public int deletePost(int deleteNo) {
		int result = 0;
		String sql = "delete from post"
				+ " where post_no = ?";
		
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deleteNo);
			result = pst.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;

	}

	// 작성글 조회
	public List<PostDTO> selectByWriter(String writer) {
		List<PostDTO> postList = new ArrayList<PostDTO>();
		String sql = "select *"
				+ " from post"
				+ " where post.writer = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, writer);
			rs =pst.executeQuery();
			while (rs.next()) {
				PostDTO post = makePost(rs);
				postList.add(post);
			}
		} catch (SQLException e) {

		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return postList;
	}

	// 회원가입
	public int memberInsert(MemberDTO member) throws SQLException {
		int result = 0;
		String sql = "insert into foodieuser values (?,?)";
		;
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, member.getId());
			pst.setString(2, member.getPassword());
			result = pst.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;

	}

	public int memberCheck(int num, String idPw) throws SQLException {
		String checkIdPw = num > 0 ? "member_id" : "member_pw";
		int count = 0;

		String check = "SELECT COUNT(*) FROM foodieuser WHERE " + checkIdPw + " = ?";
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(check);
			pst.setString(1, idPw);
			rs = pst.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		System.out.println(count);
		return count;
	}

	private PostDTO makePost(ResultSet rs) throws SQLException {

		PostDTO post = new PostDTO();
		post.setPostNo(rs.getInt("post_no"));
		post.setRestaurantPname(rs.getString("restaurant_pname"));
		post.setWriter(rs.getString("writer"));
		post.setGrade(rs.getInt("grade"));
		post.setContent(rs.getString("post_content"));
		post.setRecommendMenu(rs.getString("recommend_menu"));
		post.setPcategory(rs.getString("menu_pcategory"));
		post.setTime(DateUtil.timeStampToLocalDateTime(rs.getTimestamp("post_date")));
		return post;

	}

}
