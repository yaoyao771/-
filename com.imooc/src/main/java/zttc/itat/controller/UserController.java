package zttc.itat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import zttc.itat.model.User;
import zttc.itat.model.UserException;
@Controller
@RequestMapping("/user")
public class UserController {
private Map<String,User> users=new HashMap<String,User>();
	public UserController() {
        users.put("idm",new User( "idm","123","郑云龙","asss"));
		users.put("zyp",new User( "zyp","123","郑云龙","asss"));
		users.put("zww",new User( "zww","123","郑云龙","asss"));
		users.put("vt",new User( "vt","123","郑云龙","asss"));
		users.put("sdy",new User( "ayg","123","郑云龙","asss"));
	}
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("users",users);
		return "user/list";
	}
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(@ModelAttribute("user") User user) {
		//model.addAttribute(new User());
		return "user/add";
	}
	//添加用户是post请求
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated User user,BindingResult br) {
		if(br.hasErrors()) {
			return "/user/add";
		}
		users.put(user.getUsername(), user);
		return "redirect:/user/users";
	}
	@RequestMapping(value="/{username}",method=RequestMethod.GET)
	public String show(@PathVariable String username,Model model) {
		model.addAttribute(users.get(username));
		return "/user/show";
	}
	
	@RequestMapping(value="/{username}/update",method=RequestMethod.GET)
	public String updata(@PathVariable String username,Model model) {
		model.addAttribute(users.get(username));
		return "user/update";
	}
	@RequestMapping(value="/{username}/update",method=RequestMethod.POST)
	public String updata(@PathVariable String username,@Validated User user,BindingResult br) {
		if(br.hasErrors()) {
			return "user/update";
		}
		users.put(username, user);
		return "redirect:/user/users";
	}
	
	@RequestMapping(value="/{username}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String username){
		users.remove(username);
		return "redirect:/user/users";
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session) {
		if(!users.containsKey(username)) {
			throw new UserException("用户名不正确");
		}
		User u=users.get(username);
		if(!u.getPassword().equals(password)) {
			throw new UserException("用户密码不正确");
		}
		session.setAttribute("loginUser", u);
		return "redirect:/user/users";
	}
	//局部异常
	/*
	@ExceptionHandler(UserException.class)
	public String handlerException(UserException e,HttpServletRequest reg) {
		reg.setAttribute("e", e);
		return "error";
	}
	*/
}
