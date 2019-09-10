package com.pengheng.service;

import com.pengheng.config.ConfigProperties;
import com.pengheng.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * 文件存储组件
 *
 * @author AlanViast
 */
@Component
public class StorageService {

    private final ConfigProperties configProperties;

    /**
     * 文件存储系统的根目录
     */
    private File rootDirectory;

    @Autowired
    public StorageService(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @PostConstruct
    private void init() {
        rootDirectory = new File(configProperties.getImagesUploadPath());
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }
    }

    /**
     * 保存临时文件, 并生成一个随机文件名
     *
     * @param multipartFile Spring文件流
     * @return 保存完成后的文件对象
     */
    public String transferTo(MultipartFile multipartFile) {
        String originalFileName = multipartFile.getOriginalFilename();
        Assert.notNull(originalFileName, "无效源文件名");

        int suffixIndex = originalFileName.lastIndexOf(".");
        if (suffixIndex == -1) {
            throw new RuntimeException("目标文件不存在文件类型后缀");
        }

        String suffixName = originalFileName.substring(suffixIndex);
        String filename = StringUtil.generateUUID() + suffixName;
        return transferTo(multipartFile, filename);
    }

    /**
     * 移动保存的文件
     *
     * @param multipartFile Spring文件流
     * @param filename      要保存的文件名字
     * @return 保存后的文件相对地址
     */
    public String transferTo(MultipartFile multipartFile, String filename) {
        File targetFile = new File(rootDirectory, filename);
        try {
            targetFile.createNewFile();
            multipartFile.transferTo(targetFile);
            return filename;
        } catch (IOException e) {
            targetFile.deleteOnExit();
            throw new RuntimeException(e);
        }
    }

}
