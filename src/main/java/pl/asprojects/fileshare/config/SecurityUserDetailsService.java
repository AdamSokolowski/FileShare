package pl.asprojects.fileshare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.asprojects.fileshare.User;
import pl.asprojects.fileshare.UserDao;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("UserNameProvided: "+username);
        User user = userDao.findFirstByEmail(username);
        System.out.println("User found: "+user.getEmail());
        if(user==null) throw new UsernameNotFoundException("User with this email does not exist.");
        return new CurrentUser(user);
    }




}
