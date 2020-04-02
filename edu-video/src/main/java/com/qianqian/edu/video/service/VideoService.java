package com.qianqian.edu.video.service;

import com.qianqian.edu.common.vo.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author minsiqian
 * @date 2020/3/15 16:12
 */
public interface VideoService {

    String uploadVideo(MultipartFile file);

    boolean removeVideo(String videoId);

    boolean removeMoreVideo(List videoIds);

    R getVideoPlayAuth(String videoId);
}
