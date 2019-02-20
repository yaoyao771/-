package zttc.itat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;


@Controller
public class HelloController {
	@RequestMapping({"/hello","/"})
public String hello(String username,Model model) {
		System.out.println("hello");
		model.addAttribute("username",username);
	    return "hello";
}
	@RequestMapping("/welcome")
	public String welcone() {
		return "welcome";
	}
}
