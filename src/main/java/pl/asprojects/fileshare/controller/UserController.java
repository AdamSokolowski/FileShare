package pl.asprojects.fileshare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import pl.asprojects.fileshare.entity.User;
import pl.asprojects.fileshare.repository.RoleRepository;
import pl.asprojects.fileshare.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String addUserForm(Model model, Principal principal) {
            User user = new User();
            model.addAttribute("user", user);
            return "addUser";
    }
    

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String addUserToDB(@ModelAttribute User user, Model model, Principal principal) {
        System.out.println(user.getEmail() + " "
                + user.getFirstName() + " "
                + user.getLastName()+ " "
                + user.getPassword()+ " "
                + user.isActive()
        );
        user.setActive(true);
        userRepository.save(user);

        return "success";
    }
    

    @RequestMapping(value = "/authenticated/user/modify", method = RequestMethod.GET)
    public String updateUserForm(@AuthenticationPrincipal Principal currentUser, Model model){
        Optional<User> user = userRepository.findByEmail(currentUser.getName());
        if(user.isPresent()) {return "user/register";}
        model.addAttribute("user", user);
        return "updateUser";
    }
    

    @RequestMapping(value = "/authenticated/user/modify", method = RequestMethod.POST)
    public String updateUserInDB(@ModelAttribute User user) {
        System.out.println(user.getEmail() + " "
                + user.getFirstName() + " "
                + user.getLastName()
        );
        userRepository.save(user);
        return "success";
    }
    

    @RequestMapping(value = "/admin/user/showall")
    @ResponseBody
    public String showAllUsers(){
        String result = "";
        List<User> allUsers = userRepository.findAll();
        for (int i = 0;i<allUsers.size();i++){
            result = result + allUsers.get(i).getEmail() + " <br>";
        }
        return result;
    }
    

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String loginForm(@AuthenticationPrincipal Principal currentUser) {
    	if(currentUser == null) {
    		return "loginPage";
    	} else {
    		return "loggedHomepage";
    	}      
    }



    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String verifyUser(@ModelAttribute User user, Model model) {
        Optional<User> loggingUser = userRepository.findByEmail(user.getEmail()); 
        if(loggingUser.isPresent() && loggingUser.get().passwordMatches(user.getPassword())) {
            return "loggedHomepage";
        } else {

            return "loginPage";
        }
    }
    

    @RequestMapping(value = "/user/logout")
    public String LoggedOut(Model model){
        return "index";
    }
    

    @RequestMapping(value= "/user/nowlogged")
    @ResponseBody
    public String loggedUserId(@AuthenticationPrincipal Principal currentUser){

       if(currentUser == null){
           return "No one is currently logged in.";
       }
       User user = userRepository.findByEmail(currentUser.getName()).get();
        return "Logged in user info: <br> " + user.getEmail() + "<br>" + user.getFirstName() + " " + user.getLastName();
    }


    @RequestMapping("/admin/usermanagement")
    public String userManagementPage() {
        return "userManagement";
    }

}

