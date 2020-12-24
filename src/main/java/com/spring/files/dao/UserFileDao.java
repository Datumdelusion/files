package com.spring.files.dao;

import com.spring.files.entity.UserFile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserFileDao {

    ArrayList<UserFile> findFilesByUserId(int userId);

    void save(UserFile userFile);

    UserFile findFileByFileId(int id);

    void updateDownCounts(UserFile file);

    void deleteFile(UserFile file);
}


