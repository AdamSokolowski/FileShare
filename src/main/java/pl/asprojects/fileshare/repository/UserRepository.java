package pl.asprojects.fileshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.asprojects.fileshare.entity.User;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
