package dto;

import java.time.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString

public class PostDTO {
	
	private int postNo;
	private String restaurantPname;
	private String writer;
	private int grade;
	private String content;
	private String recommendMenu;
	private String pcategory;
	private LocalDateTime time;
	
	
	public PostDTO(int postNo, String restaurantPname, String writer, int grade, String content, String recommendMenu,
			String pcategory, LocalDateTime time) {
		super();
		this.postNo = postNo;
		this.restaurantPname = restaurantPname;
		this.writer = writer;
		this.grade = grade;
		this.content = content;
		this.recommendMenu = recommendMenu;
		this.pcategory = pcategory;
		this.time = time;
	}


	public PostDTO() {
		super();
	} 
	
	
}
