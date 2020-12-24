package com.spring.files.service;

import com.spring.files.entity.UserFile;

import java.util.ArrayList;

public interface UserFileService {
    ArrayList<UserFile> findFileByUserId(int userId);

    void save(UserFile userFile);

    UserFile findFileByFileId(int id);

    void updateDownCounts(UserFile file);

    void deleteFile(UserFile file);
}
