import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sillycat.easynosql.service.UserService


@Controller
@RequestMapping("/users")
class UserController {
	
	@Autowired
	UserService userService
	
}