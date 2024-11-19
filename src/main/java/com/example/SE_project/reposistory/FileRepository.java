package com.example.SE_project.reposistory;

import com.example.SE_project.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,String> {
    File findFileByFileid(String file_id);
}
