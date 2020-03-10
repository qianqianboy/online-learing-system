package com.qianqian.edu.aliyun.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author minsiqian
 * @date 2020/2/26 21:40
 */
public interface FileService {

    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file,String host);
}