package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dto.PostDTO;
import util.DBUtil;
import util.DateUtil;

public class PostDAO {

	Connection conn; // DB연결
	Statement st; // sql 코드 전송
	PreparedStatement pst; // Statement 를 상속 받음, (?)바인딩 변수 지원 
	ResultSet rs; // 결과 반환
	
	// 모든 포스팅 조회
	public List<PostDTO> selectAll(){
		List<PostDTO> postList = new ArrayList<PostDTO>();
		String sql = "select * from post";
		conn = DBUtil.dbConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
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
	
	
	// 게시글 입력
	public int postInsert (PostDTO post) {
		int result = 0;
		String sql = "insert into post values (postno_seq.nextval,?,?,?,?,?,?,?)";
		conn = DBUtil.dbConnection();
		
		try {
		
			pst = conn.prepareStatement(sql);
			pst.setString(1, post.getRestaurantPname());
			pst.setString(2, post.getPcategory());
			pst.setString(3, post.getWriter());
			pst.setInt(4, post.getGrade());
			pst.setString(5, post.getContent());
			pst.setString(6, post.getRecommendMenu());
			pst.setTimestamp(7, DateUtil.localDateTimeToTimeStamp(post.getTime()));
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;	
	}
	
	
	public PostDTO makePost(String selectedCategory, String restaurant_pname, String userId,String content,String rcMenu,int grade, LocalDateTime now) {
		PostDTO post = new PostDTO();
		post.setContent(content);
		post.setGrade(grade);
		post.setPcategory(selectedCategory);
		post.setWriter(userId);
		post.setRecommendMenu(rcMenu);
		post.setRestaurantPname(restaurant_pname);
		post.setGrade(grade);
		post.setTime(now);
		
		return post;
		
	}
	
	private PostDTO makePost (ResultSet rs) throws SQLException {
		
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
