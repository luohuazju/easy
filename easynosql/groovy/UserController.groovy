import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
class UserController {
	
	@Autowired
	UserService userService
	
	@RequestMapping
	public String getUsersPage() {
		return "users";
	}
	
}