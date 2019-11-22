package pl.asprojects.fileshare.service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.asprojects.fileshare.entity.Role;
import pl.asprojects.fileshare.entity.User;
import pl.asprojects.fileshare.repository.RoleRepository;
import pl.asprojects.fileshare.repository.UserRepository;
import pl.asprojects.fileshare.service.UserService;

public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		System.out.println("b4 save to db");
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findRoleByRoleName("user");
		HashSet<Role> roles = new HashSet<Role>();
		roles.add(userRole);
		user.setRoles(roles);
		System.out.println( user.getPassword()+ " "
                + "Is active:" + user.isActive() + " "
                + user.getRoles());
		userRepository.save(user);
	}


}
