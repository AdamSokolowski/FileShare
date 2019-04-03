package pl.asprojects.fileshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.asprojects.fileshare.config.CurrentUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@SessionAttributes({"authenticatedFullName"})
public class UserController {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;


    private String authenticatedFullName = "";

    @RequestMapping(value = "/admin/user/add/{email}/{password}/{firstName}/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public String addUser(@PathVariable String email, @PathVariable String password, @PathVariable String firstName, @PathVariable String lastName) {
        User user = new User();
        Role role = roleDao.findRoleByRoleName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        System.out.println(role.getRoleName());
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(true);
        user.addRole(role);

        userDao.save(user);
        return "Added user: " + firstName + " " + lastName + " eMail: " + email;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String addUserForm(Model model) {
        if ("".equals(authenticatedFullName) == false) {
            User user = new User();
            model.addAttribute("user", user);
            return "addUser";
        } else {
            return "alreadyLoggedPage";
        }
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String addUserToDB(@ModelAttribute User user, Model model) {
        System.out.println(user.getEmail() + " "
                + user.getFirstName() + " "
                + user.getLastName()
        );
        userDao.save(user);
        authenticatedFullName = user.getFirstName() +" "+ user.getLastName();
        String authenticatedFullName = user.getFirstName()+" "+user.getLastName();
        model.addAttribute("authenticatedFullName", authenticatedFullName);

        return "success";
    }

    @RequestMapping(value = "/authenticated/user/modify", method = RequestMethod.GET)
    public String updateUserForm(@AuthenticationPrincipal CurrentUser currentUser, Model model){
        if("".equals(authenticatedFullName) == false) {return "user/register";}
        User user = userDao.findFirstByEmail(currentUser.getUsername());
        model.addAttribute("user", user);
        return "updateUser";
    }

    @RequestMapping(value = "/authenticated/user/modify", method = RequestMethod.POST)
    public String updateUserInDB(@ModelAttribute User user) {
        System.out.println(user.getEmail() + " "
                + user.getFirstName() + " "
                + user.getLastName()
        );
        //userDao.saveUser(user);
        userDao.save(user);
        return "success";
    }

    @RequestMapping(value = "/admin/user/showall")
    @ResponseBody
    public String showAllUsers(){
        String result = "";
        List<User> allUsers = userDao.findAll();
        for (int i = 0;i<allUsers.size();i++){
            result = result + allUsers.get(i).getEmail() + " <br>";
        }
        return result;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String loginForm() {
        if("".equals(authenticatedFullName) == false){ return "loggedHomepage";} else {
            System.out.println("current user: " + authenticatedFullName);
            //User user = new User();
            //model.addAttribute("user", user);
            return "loginPage";
        }
    }


/*
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String verifyUser(@ModelAttribute User user, Model model) {

        //User loggingUser = userDao.findByEmail(user.getEmail());
        User loggingUser = userDao.findFirstByEmail(user.getEmail());
        //System.out.println(user.getPassword());
        //System.out.println(loggingUser.getPassword());
        if(loggingUser.getId()>0 && loggingUser.getPassword().equals(user.getPassword())) {
            authenticatedUserId = loggingUser.getId();
            String authenticatedFullName = loggingUser.getFirstName() + " " + loggingUser.getLastName();
            model.addAttribute("authenticatedFullName", authenticatedFullName);
            return "loggedHomepage";
        } else {

            return "loginPage";
        }
    }
*/
    @RequestMapping(value = "/user/logout")
    public String LoggedOut(Model model){
        authenticatedFullName ="";
        model.addAttribute("authenticatedFullName", authenticatedFullName);
        return "index";
    }

    @RequestMapping(value= "/user/nowlogged")
    @ResponseBody
    public String loggedUserId(@AuthenticationPrincipal CurrentUser currentUser){

       if(currentUser == null){
           return "No one is currently logged in.";
       }
       User user =userDao.findFirstByEmail(currentUser.getUsername());
        return "Logged in user info: <br> " + user.getEmail() + "<br>" + user.getFirstName() + " " + user.getLastName();
    }


    @RequestMapping("/admin/userManagement")
    public String userManagementPage() {
        return "userManagement";
    }

}

