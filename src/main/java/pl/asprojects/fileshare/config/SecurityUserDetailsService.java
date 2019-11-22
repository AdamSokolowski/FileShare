package pl.asprojects.fileshare.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.asprojects.fileshare.entity.User;
import pl.asprojects.fileshare.repository.UserRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    	
        System.out.println("UserNameProvided: "+username);
        Optional<User> user = userRepository.findByEmail(username);
         user.orElseThrow(() -> new UsernameNotFoundException("User with this email does not exist."));
        return new CurrentUser(user.get());
    }




}
