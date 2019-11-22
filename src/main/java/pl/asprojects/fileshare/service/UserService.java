package pl.asprojects.fileshare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.asprojects.fileshare.entity.User;

@Transactional
@Service
public interface UserService {
	
	Optional<User> findByEmail(String email);
	
	void saveUser(User user);
	
}
