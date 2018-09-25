package pl.coderslab.spring01hibernate;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes({"authenticatedUserId", "authenticatedFullName"})
public class UserController {
    @Autowired
    UserDao userDao;

    long authenticatedUserId = 0;
    //String authenticatedFullName ="";

    @RequestMapping(value = "/user/add/{email}/{password}/{firstName}/{lastName}", method = RequestMethod.GET)
    @ResponseBody
    public String addUser(@PathVariable String email, @PathVariable String password, @PathVariable String firstName, @PathVariable String lastName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        //userDao.saveUser(user);
        userDao.save(user);
        return "Added user: " + firstName + " " + lastName + " eMail: " + email;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String addUserForm(Model model) {
        if (authenticatedUserId==0) {
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
        //userDao.saveUser(user);
        userDao.save(user);
        authenticatedUserId = user.getId();
        String authenticatedFullName = user.getFirstName()+" "+user.getLastName();
        model.addAttribute("authenticatedFullName", authenticatedFullName);

        return "success";
    }

    @RequestMapping(value = "/user/modify", method = RequestMethod.GET)
    public String updateUserForm(Model model){
        if(authenticatedUserId==0) {return "user/register";}
        Optional<User> user = userDao.findById(authenticatedUserId);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @RequestMapping(value = "/user/modify", method = RequestMethod.POST)
    public String updateUserInDB(@ModelAttribute User user) {
        System.out.println(user.getEmail() + " "
                + user.getFirstName() + " "
                + user.getLastName()
        );
        //userDao.saveUser(user);
        userDao.save(user);
        return "success";
    }

    @RequestMapping(value = "/user/showall")
    @ResponseBody
    public String showAllUsers(){
        String result = "";
        //List<User> allUsers = userDao.showAll();
        List<User> allUsers = userDao.findAll();
        for (int i = 0;i<allUsers.size();i++){
            result = result + allUsers.get(i).getEmail() + " <br>";
        }
        return result;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        if(authenticatedUserId>0){ return "loggedHomepage";}
        User user = new User();
        model.addAttribute("user", user);
        return "loginPage";
    }


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

    @RequestMapping(value = "/user/logout")
    public String LoggedOut(Model model){
        authenticatedUserId=0;
        String authenticatedFullName="";
        model.addAttribute("authenticatedFullName", authenticatedFullName);
        return "index";
    }

    @RequestMapping(value= "user/nowlogged")
    @ResponseBody
    public String loggedUserId(){
        if(authenticatedUserId==0){return "No one is currently logged in.";}
        Optional<User> user = userDao.findById(authenticatedUserId);
        return "Logged in user info: <br> "+ user.get().getEmail() +"<br>"+user.get().getFirstName()+" "+user.get().getLastName();
    }


    @RequestMapping("/userManagement")
    public String userManagementPage() {
        return "userManagement";
    }

}

