package pl.asprojects.fileshare.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
    @NotNull
    private Boolean active;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role;

    public User() {
    	
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean passwordMatches(String password){
        //return (BCrypt.checkpw(password, getPassword()));
        return this.password.equals(password);

    }

    public void setPassword(String password) {

        //this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = password;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return role;
    }

    public void setRoles(Set<Role> roles) {
        this.role = roles;
    }

    public void addRole(Role role){
        if(this.role==null){
            this.role = new HashSet<Role>();
        }
        this.role.add(role);
    }

    public void removeRole(Role role){
        if(this.role!=null){
            this.role.remove(role);
        }

    }
}

