package pl.asprojects.fileshare.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "filesdata")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=150, message = "Only 150 characters for file name allowed.")
    private String filename;

    private String path;

    @Size(max=254, message = "Description must me shorter than 255 characters.")
    private String description;

    private long authorID;

    @ManyToMany
    private List<User> fileAccess = new ArrayList<>();


    public FileData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    public List<User> getFileAccess() {
        return fileAccess;
    }

    public void setFileAccess(List<User> fileAccess) {
        this.fileAccess = fileAccess;
    }
}
