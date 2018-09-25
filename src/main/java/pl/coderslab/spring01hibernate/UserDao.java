package pl.coderslab.spring01hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface UserDao extends JpaRepository<User, Long> { //}

    public User findFirstByEmail(String email);
}
