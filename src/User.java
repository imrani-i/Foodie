import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class User {

	
	private String id;
	private String password;
	
	
	
	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	
}
