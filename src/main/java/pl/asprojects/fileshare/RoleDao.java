package pl.asprojects.fileshare;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleDao extends JpaRepository<Role, Integer> {

    Set<Role> findRolesByUser(User user);

    Role findRoleByRoleName(String roleName);
}
