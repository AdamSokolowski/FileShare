package pl.asprojects.fileshare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.asprojects.fileshare.User;

import java.util.List;

@Component
@Transactional
public interface UserDao extends JpaRepository<User, Long> { //}

    User findFirstByEmail(String email);


}
