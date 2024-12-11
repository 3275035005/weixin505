package com.feedback.controller;

import com.feedback.service.FileService;
import com.feedback.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片上传和下载入口
 */
@RestController
@RequestMapping("/file")
public class FileController {


    private final static String userFilePath = "E:\\project\\secondhandShop\\xmtp";

    private final static String baseUrl = "http://localhost:9001/file";

    @Autowired
    private FileService fileService;

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    @PostMapping
    public ResultVo uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String uuid="file"+ System.currentTimeMillis();
        String fileName= uuid+ multipartFile.getOriginalFilename();
        try {
            if (fileService.uploadFile(multipartFile,fileName)) {
                return ResultVo.success(baseUrl+"/image?imageName="+fileName);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return ResultVo.fail("");
    }

    /**
     * 图片下载
     * @param imageName
     * @param response
     * @throws IOException
     */
    @GetMapping("/image")
    public void getImage(@RequestParam("imageName") String imageName,
                         HttpServletResponse response) throws IOException {
        File fileDir = new File(userFilePath);
        File image=new File(fileDir.getAbsolutePath() +"/"+imageName);
        if (image.exists()){
            FileInputStream fileInputStream=new FileInputStream(image);
            byte[] bytes=new byte[fileInputStream.available()];
            if (fileInputStream.read(bytes)>0){
                OutputStream outputStream=response.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();
            }
            fileInputStream.close();
        }
    }

}
