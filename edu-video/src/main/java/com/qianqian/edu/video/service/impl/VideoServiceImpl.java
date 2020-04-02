package com.qianqian.edu.video.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.qianqian.edu.common.exception.QianQianException;
import com.qianqian.edu.common.vo.R;
import com.qianqian.edu.video.service.VideoService;
import com.qianqian.edu.video.util.AliyunVodSDKUtils;
import com.qianqian.edu.video.util.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author minsiqian
 * @date 2020/3/15 16:13
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)) {
                    throw new QianQianException(40002, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new QianQianException(40003, "视频上传失败！");
        }
    }

    @Override
    public boolean removeVideo(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            log.info("RequestId = " + response.getRequestId() + "\n");

            return  response.getRequestId()!=null;

        } catch (ClientException e) {
            throw new QianQianException(40004, "视频删除失败!");
        }
    }

    @Override
    public boolean removeMoreVideo(List videoIds) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String ids = org.apache.commons.lang.StringUtils.join(videoIds.toArray(), ",");
            request.setVideoIds(ids);
            DeleteVideoResponse response = client.getAcsResponse(request);
            log.info("RequestId = " + response.getRequestId() + "\n");

            return  response.getRequestId()!=null;

        } catch (ClientException e) {
            throw new QianQianException(40004, "视频删除失败!");
        }
    }

    @Override
    public R getVideoPlayAuth(String videoId) {
        try{
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        GetPlayInfoResponse response = client.getAcsResponse(request);
        //得到视频地址
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        String playURL="";
        Long size=0L;
        String duration="";
        for (GetPlayInfoResponse.PlayInfo info : playInfoList) {
            playURL=info.getPlayURL();
            size=info.getSize();
            duration = info.getDuration();
        }
        //返回结果
        return  R.ok().message("获取成功").data("playAuth", playURL).data("size",size).data("duration",duration);
        }catch (Exception e){
            throw new QianQianException(3003,"视频获取失败！");
        }
    }
}
