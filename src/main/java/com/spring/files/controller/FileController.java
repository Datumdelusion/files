package com.spring.files.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.spring.files.entity.User;
import com.spring.files.entity.UserFile;
import com.spring.files.service.UserFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    UserFileService userFileService;

    @GetMapping("delete")
    public String delete(int id) throws FileNotFoundException {

        UserFile file = userFileService.findFileByFileId(id);
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + file.getPath();
        File realFile = new File(realPath, file.getNewFileName());
        if (realFile.exists()) {
            realFile.delete();
            //立即删除
        }
        userFileService.deleteFile(file);

        return "redirect:/file/findAll";
    }

    @GetMapping("download")
    public void download(String openStyle, int id, HttpServletResponse response) throws IOException {
        openStyle = openStyle == null ? "attachment" : openStyle;
        //一次响应请求
        UserFile file = userFileService.findFileByFileId(id);
        //获取到文件信息
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "static" + file.getPath();
        System.out.println(realPath);
        //获取文件绝对路径
        FileInputStream is = new FileInputStream(new File(realPath, file.getNewFileName()));
        //文件输入流
        response.setHeader("content-disposition", openStyle + ";fileName=" + URLEncoder.encode(file.getOldFileName(), "UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        //获取响应输出流
        IOUtils.copy(is, os);
        //文件拷贝
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
        if ("attachment".equals(openStyle)) {
            file.setDownCounts(file.getDownCounts() + 1);
            userFileService.updateDownCounts(file);
        }
    }

    @PostMapping("upload")
    public String upload(MultipartFile upload, HttpSession session, RedirectAttributes attributes) throws IOException {
        User user = (User) session.getAttribute("user");
        String oldFileName = upload.getOriginalFilename();
        //获取文件名
        if ("application/octet-stream".equals(upload.getContentType())) {
            return "redirect:/file/findAll";
        }else {
            String extension = "." + FilenameUtils.getExtension(upload.getOriginalFilename());
            //获取后缀
            // FilenameUtils需要引入依赖commons-fileupload
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                    UUID.randomUUID().toString().replace("-", "") +
                    extension;
            //处理新文件名
            long size = upload.getSize();
            String type = upload.getContentType();
            //文件类型
            String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/files";

            //获取绝对路径，接上相对路径
            String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String dateDirPath = realPath + "/" + dateFormat;
            File dateDir = new File(dateDirPath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }
            //创建文件夹
            upload.transferTo(new File(dateDir, newFileName));
            //上传

            UserFile userFile = new UserFile();
            //文件信息
            userFile.setOldFileName(oldFileName).setNewFileName(newFileName).setExt(extension).setSize(size)
                    .setType(type).setPath("/files/" + dateFormat).setUserId(user.getId());

            userFileService.save(userFile);
        }
        return "redirect:/file/findAll";
    }

    @GetMapping("findAll")
    public String findAll(HttpSession session, Model model){
        System.out.println("进入findAll");
        User user = (User) session.getAttribute("user");
        ArrayList<UserFile> userFiles = userFileService.findFileByUserId(user.getId());
        model.addAttribute("files", userFiles);
        return "showAll";
    }
}
