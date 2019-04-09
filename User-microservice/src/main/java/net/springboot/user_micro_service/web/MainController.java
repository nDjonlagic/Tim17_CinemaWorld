package net.springboot.user_micro_service.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import net.springboot.user_micro_service.model.User;
import net.springboot.user_micro_service.repository.UserRepository;
import net.springboot.user_micro_service.service.UserService;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("/")
    public RedirectView redirect() {
    	        return new RedirectView("/index");
    	    }
/*
 * Get login user form
 */
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    /*
    * Get Index page 
    */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /*
     * Get admin home page
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }
    /*
     * Get delete users form and delete specified user
     */
    @RequestMapping(value = "/admin/delete/users", method = RequestMethod.GET)
    public String deleteUserAdmin(@RequestParam (name="userEmail") String email, Model model) {
        userService.deleteByEmail(email);
        return "redirect:/admin/users";
    }
    @ResponseBody
    @RequestMapping(value = "/delete/users", method = RequestMethod.GET)
    public User deleteUser(@RequestParam (name="userEmail") String email, Model model) {
    	User user = userService.findByEmail(email);
        userService.deleteByEmail(email);
        return user;
    }
    
    /*
     * Get edit user information page
     */
    @GetMapping("/edit")
    public String showUpdateForm(Model model) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        System.out.println("GDJEEEEEEEE " + username);
        User user = userService.findByEmail(username);
      System.out.println(user.getId() + " " + user.getFirstName());

        model.addAttribute("user", user);
//        model.addAttribute("roles1", user.getRoles());
        return "updateUser";
    }
    
    /*
     * Post for update user info
     */
//    @PostMapping("/update")
//    public String updateUser(@Valid User user, 
//      BindingResult result, Model model) {
//        if (result.hasErrors()) {
////            user.setId(id);
//            return "updateUser";
//        }
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = loggedInUser.getName();
//        User existing = userService.findByEmail(username);
//        user.setRoles(existing.getRoles());
//        userRepository.save(user);
////        model.addAttribute("users", userRepository.findAll());
//        SecurityContextHolder.clearContext();
//        return "redirect:/index";
//    }
    @ResponseBody
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam (name="id") Long id,@RequestBody @Valid User user) {
        User existing = userService.findById(id);
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRoles(user.getRoles());

        final User updatedUser = userRepository.save(existing);
        return ResponseEntity.ok(updatedUser);
    }
    
   
    /*
     * Get all users page for admin
     */
    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adminUsers";
    }
    
    @ResponseBody
    @GetMapping("/users")
    public List<User> adminUsers() {
        List<User> users = userService.findAll();
        return users;
    }
    
    /*
     * Get user home page
     */
    @GetMapping("/userhome")
    public String user(@RequestParam(name="name", required=false, defaultValue="user")String name,Model model) {
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    	String currentPrincipalName = authentication.getName();
//    	System.out.println(currentPrincipalName);
    	model.addAttribute("name",name);
        return "userhome";
    }
}
