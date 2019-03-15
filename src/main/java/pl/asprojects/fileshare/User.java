package pl.asprojects.fileshare;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private int role;

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

    public int getRole() { return role;}

    public void setRole(int role){
        this.role = role;
    }
}

