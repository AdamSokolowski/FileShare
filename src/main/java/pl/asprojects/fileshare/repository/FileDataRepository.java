package pl.asprojects.fileshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.asprojects.fileshare.entity.FileData;

@Component
    @Transactional
    public interface FileDataRepository extends JpaRepository<FileData, Long> {

}
