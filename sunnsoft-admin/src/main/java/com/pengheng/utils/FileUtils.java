package com.pengheng.utils;

import com.pengheng.core.exception.ApplicationException;
import com.pengheng.util.DateUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @Remark 文件上传处理类
 * @Author pengheng
 * @Date 2019年7月23日15:02:49
 */
public class FileUtils {


    /**
     * 上传文件
     * @param file   上传的文件
     * @param rootPath  上传的目录
     * @return
     */
    public static String upload(File file ,String rootPath){
        return upload(transferMultipartFile(file),rootPath);
    }

    public static String upload(MultipartFile file, String rootPath) {
        String dateTime =  DateUtils.getNow("yyyyMMdd");

        if (file.isEmpty()) {
            throw new ApplicationException("上传文件为空");
        }
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 新文件名
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(rootPath+ dateTime + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //显示的名字
        String filename = "/file/"+ dateTime + "/" + fileName;
        System.out.println(filename);
        return filename;
    }

    /**
     * 将file文件转换成为MultipartFile 上传文件
     * @return
     */
    public static MultipartFile transferMultipartFile(File f){
        MultipartFile mulFile = null;
        try {
            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(f.toPath()), false, f.getName(), (int) f.length(), f.getParentFile());
            InputStream input = new FileInputStream(f);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            mulFile = new CommonsMultipartFile(fileItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mulFile;
    }

    /**
     * 下载文件
     * @param response
     * @param file
     * @param newFileName
     */
    public static void download(HttpServletResponse response,File file,String newFileName){
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="+new String(newFileName.getBytes("ISO8859-1"),"UTF-8"));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            InputStream is = new FileInputStream(file.getAbsoluteFile());
            BufferedInputStream bis = new BufferedInputStream(is);
            int length = 0;
            byte[] temp = new byte[1 * 1024 * 10];
            while((length = bis.read(temp))!=-1){
                 bos.write(temp,0,length);
            }
            bos.flush();
            bis.close();
            bos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\admin\\Desktop\\test001.jpg");

            String rootPath = "/project/sunnsoft-admin/general/";
            FileUtils.upload(file, rootPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
