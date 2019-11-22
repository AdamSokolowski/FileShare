package pl.asprojects.fileshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.asprojects.fileshare.entity.Role;
import pl.asprojects.fileshare.entity.User;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Set<Role> findRolesByUser(User user);

    Role findRoleByRoleName(String roleName);
    
}
