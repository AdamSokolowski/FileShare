package pl.asprojects.fileshare.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.asprojects.fileshare.entity.User;

@Service
public class SpringDataUserDetailsService implements UserDetailsService{
	
	private UserService userService;
	
//	@Autowired
	//public void setUserRepository(UserService userService) {
		//this.userService = userService;
	//}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userService.findByEmail(userName);
		user.orElseThrow(() ->new UsernameNotFoundException("User with email:'" + userName +"' does not exist."));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		user.get().getRoles().forEach(role ->	grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName())));
		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), grantedAuthorities);
	}
}
