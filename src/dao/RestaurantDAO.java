package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import dto.RestaurantDTO;

public class RestaurantDAO {

	Connection conn; // DB연결
	Statement st; // sql 코드 전송
	PreparedStatement pst; // Statement 를 상속 받음, (?)바인딩 변수 지원
	ResultSet rs; // 결과 반환
	
	// 특정 카테고리에 따른 조회 
	public List<RestaurantDTO> selectByCategory(String category,String standard){
		
		List<RestaurantDTO> restaurantList = new ArrayList<RestaurantDTO>();
		String sql = "select *" 
				+ " from restaurant"
				+ " where menu_category = ? and avg_grade is not null" 
				+ " order by " + standard + " desc";
		
		conn = DBUtil.dbConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, category);
			rs =pst.executeQuery();
			while (rs.next()) {
				RestaurantDTO restaurant = makeRestaurant(rs);
				restaurantList.add(restaurant);
	            
			}
			
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
	
		return restaurantList;

		
	}

	// 전체 카테고리에서 조회

	public List<RestaurantDTO> selectByAll(String standard) {
		
		List<RestaurantDTO> restaurantList = new ArrayList<RestaurantDTO>();
	
		String sql = "select menu_category, restaurant_name, avg_grade, post_count" 
				+ " from restaurant"
				+ " where avg_grade is not null" 
				+ " order by " + standard + " desc";

		conn = DBUtil.dbConnection();
		try {
			st = conn.createStatement();
			rs =st.executeQuery(sql);
			while (rs.next()) {
				RestaurantDTO restaurant = makeRestaurant(rs);
				restaurantList.add(restaurant);
			}
			
		} catch (SQLException e) {
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return restaurantList;

	}

	// 모든 조회하기

	public void selectRestaurantAll() {

	}

	private RestaurantDTO makeRestaurant(ResultSet rs) throws SQLException {

		RestaurantDTO restaurant = new RestaurantDTO();

		
		restaurant.setRestaurantName(rs.getString("restaurant_name"));
		restaurant.setCategory(rs.getString("menu_category"));
		restaurant.setAvgGrade(rs.getDouble("avg_grade"));
		restaurant.setPost_count(rs.getInt("post_count"));
		
		return restaurant;
	}

}
