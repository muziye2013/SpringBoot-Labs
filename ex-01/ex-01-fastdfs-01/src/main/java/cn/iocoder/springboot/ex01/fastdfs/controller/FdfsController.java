package cn.iocoder.springboot.ex01.fastdfs.controller;

import cn.iocoder.springboot.ex01.fastdfs.entity.FileEntity;
import cn.iocoder.springboot.ex01.fastdfs.entity.RepEntity;
import cn.iocoder.springboot.ex01.fastdfs.util.FdfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author x
 * @since 2020-2-13 18:48
 **/
@Controller
@Slf4j
public class FdfsController {
    @Autowired
    private FdfsUtil fdfsUtil;
    /**
     *  文件上传
     *  最后返回fastDFS中的文件名称;group1/M00/01/04/CgMKrVvS0geAQ0pzAACAAJxmBeM793.doc
     * @param file 页面提交时文件
     * @return
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public RepEntity uploadFile(MultipartFile file){
        RepEntity repEntity = new RepEntity();
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            log.error("获取文件错误");
            e.printStackTrace();
            repEntity.setCode("404");
            repEntity.setContent("获取不到文件");
        }
        //获取源文件名称
        String originalFileName = file.getOriginalFilename();
        //获取文件后缀--.doc
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = file.getName();
        //获取文件大小
        long fileSize = file.getSize();
        System.out.println(originalFileName + "==" + fileName + "==" + fileSize + "==" + extension + "==" + bytes.length);
        String uploadFilePath = fdfsUtil.uploadFile(bytes,fileSize,extension);
        if(StringUtils.isBlank(uploadFilePath)){
            repEntity.setCode("404");
            repEntity.setContent("上传失败");
        }else{
            repEntity.setCode("200");
            repEntity.setContent(uploadFilePath);
        }
        return repEntity;
    }


    /**
     *
     * @param fileEntity
     * @return
     */
    @PostMapping(value = "/uploadBytes")
    @ResponseBody
    public RepEntity uploadFileByByte(@RequestBody FileEntity fileEntity){
        RepEntity repEntity = new RepEntity();
        byte[] bytes = fileEntity.getBytes();
        if(bytes.length < 1) {
            log.error("获取文件错误");
            repEntity.setCode("404");
            repEntity.setContent("获取不到文件");
        }
        //获取源文件名称
        String originalFileName = fileEntity.getFileName();
        //获取文件后缀--.doc
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = fileEntity.getFileName();
        //获取文件大小
        long fileSize = fileEntity.getLength();
        System.out.println(originalFileName + "==" + fileName + "==" + fileSize + "==" + extension + "==" + bytes.length);
        String uploadFilePath = fdfsUtil.uploadFile(bytes,fileSize,extension);
        if(StringUtils.isBlank(uploadFilePath)){
            repEntity.setCode("404");
            repEntity.setContent("上传失败");
        }else{
            repEntity.setCode("200");
            repEntity.setContent(uploadFilePath);
        }
        return repEntity;
    }


    /**
     *  文件上传
     *  最后返回fastDFS中的文件名称;group1/M00/01/04/CgMKrVvS0geAQ0pzAACAAJxmBeM793.doc
     * @param file 页面提交时文件
     * @return
     */
    @PostMapping(value = "/image/upload")
    @ResponseBody
    public RepEntity uploadImage(MultipartFile file){
        RepEntity repEntity = new RepEntity();
        byte[] bytes = new byte[0];
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            log.error("获取文件错误");
            e.printStackTrace();
            repEntity.setCode("404");
            repEntity.setContent("获取不到文件");
        }
        //获取源文件名称
        String originalFileName = file.getOriginalFilename();
        //获取文件后缀--.doc
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = file.getName();
        //获取文件大小
        long fileSize = file.getSize();
        System.out.println(originalFileName + "==" + fileName + "==" + fileSize + "==" + extension + "==" + bytes.length);
        String uploadFilePath = fdfsUtil.uploadImage(bytes,fileSize,extension);
        if(StringUtils.isBlank(uploadFilePath)){
            repEntity.setCode("404");
            repEntity.setContent("上传失败");
        }else{
            repEntity.setCode("200");
            repEntity.setContent(uploadFilePath);
        }
        return repEntity;
    }

    /**
     *  文件下载
     * @param response   HttpServletResponse 内置对象
     * @throws IOException
     */
    @GetMapping(value = "/file/**")
    public void downloadFile(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String fileUrl = request.getRequestURI().replace("/file/","");
        byte[] bytes = fdfsUtil.downloadFile(fileUrl);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.length());
        // 这里只是为了整合fastdfs，所以写死了文件格式。需要在上传的时候保存文件名。下载的时候使用对应的格式
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  文件下载
     * @param response   HttpServletResponse 内置对象
     * @throws IOException
     */
    @GetMapping(value = "/downloadFileByPath")
    public void downloadFileByPath( HttpServletResponse response,
                                    @RequestParam("fileUrl") String fileUrl,
                                    @RequestParam("fileName") String nameFile) throws IOException {
        byte[] bytes = fdfsUtil.downloadFile(fileUrl);
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.length());
        response.setContentLength(bytes.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        // 这里只是为了整合fastdfs，所以写死了文件格式。需要在上传的时候保存文件名。下载的时候使用对应的格式
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(nameFile, "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null){
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
