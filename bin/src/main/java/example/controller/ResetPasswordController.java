package example.controller;

import example.model.User;
import example.service.SecurityService;
import example.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;

@Controller
public class ResetPasswordController
{
  @Autowired
  private SecurityService securityService;

  @Autowired
  private UserService userService;

	@GetMapping("/reset")
	public String reset(Model model)
	{
    if (securityService.isAuthenticated()) {
        return "redirect:/";
    }
		model.addAttribute("resetForm", new User());
		return "reset";
	}

  @PostMapping("/reset")
  public String reset(@ModelAttribute("resetForm") User userForm) {
      User user = userService.resetPassword(userForm.getUsername(), userForm.getPassword());

      if (user == null) {
        return "redirect:/register";
      }

      securityService.autoLogin(user.getUsername(), user.getPassword());

      return "redirect:/user";
  }
}
