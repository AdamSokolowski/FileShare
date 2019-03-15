package pl.asprojects.fileshare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.asprojects.fileshare.FileData;

@Component
    @Transactional
    public interface FileDataDao extends JpaRepository<FileData, Long> {

}
