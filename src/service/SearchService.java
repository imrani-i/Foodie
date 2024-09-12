package service;

import java.util.List;

import dao.RestaurantDAO;
import dto.RestaurantDTO;

public class SearchService {
	RestaurantDAO restaurantDAO = new RestaurantDAO();
	
	public List<RestaurantDTO> selectByAll (String standard){
		return restaurantDAO.selectByAll(standard);
	}

	public List<RestaurantDTO> selectByCategory(String category,String standard){
		return restaurantDAO.selectByCategory(category, standard);
	}
}
