package com.spring.files.service;

import com.spring.files.dao.UserFileDao;
import com.spring.files.entity.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    UserFileDao userFileDao;
    @Override
    public ArrayList<UserFile> findFileByUserId(int userId) {
        return userFileDao.findFilesByUserId(userId);
    }

    @Override
    public void save(UserFile userFile) {
        boolean isImg = userFile.getType().startsWith("image");
        userFile.setImg(isImg);
        userFile.setDownCounts(0);
        userFile.setUploadTime(new Date());
        userFileDao.save(userFile);
    }

    @Override
    public UserFile findFileByFileId(int id) {
        return userFileDao.findFileByFileId(id);
    }

    @Override
    public void updateDownCounts(UserFile file) {
        userFileDao.updateDownCounts(file);
    }

    @Override
    public void deleteFile(UserFile file) {
        userFileDao.deleteFile(file);
    }
}
