package pl.asprojects.fileshare;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class MainController {

    @RequestMapping("/index")
    public String Homepage() {
        return "index";
    }


}
