package service;

import java.util.List;

import dao.PostDAO;
import dto.PostDTO;

public class PostService {

	PostDAO postDAO = new PostDAO();
	
	public List<PostDTO> selectAll(){
		return postDAO.selectAll();
	}
	
		
	public int postInsert (PostDTO post) {
		return postDAO.postInsert(post);
	}
	
}
