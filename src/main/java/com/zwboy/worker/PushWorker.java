package com.zwboy.worker;

import com.zwboy.cache.CacheUtil;
import com.zwboy.exceptions.PusherException;
import com.zwboy.model.PushTask;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.avcodec.AVPacket;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;

import static org.bytedeco.ffmpeg.global.avcodec.av_packet_unref;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/25 10:15
 */
@Slf4j
public class PushWorker implements Runnable {


    /**
     * 描述推流任务
     */
    private PushTask pushTask = null;
    /**
     * 采集流
     */
    private FFmpegFrameGrabber grabber = null;
    /**
     * 编码推送流
     */
    private FFmpegFrameRecorder recorder = null;
    /**
     * 视频的高度和宽度
     */
    private int videoHeight = 0;
    private int videoWidth = 0;

    /**
     * 控制该进程是否进行
     */
    private volatile boolean isRunning = false;
    /**
     * 记录推流进程的启动时间，用于计算运行时长
     */
    private long startTime = System.currentTimeMillis();

    PushWorker(PushTask pushTask) {
        this.pushTask = pushTask;
    }

    public void stop() {
        this.isRunning = false;
    }

    public boolean isRunning(){
        return isRunning;
    }

    /**
     * 获取推流进程的运行时长
     * @return
     */
    public long getRunningTime(){
        return System.currentTimeMillis() - startTime;
    }

    @Override
    public void run() {
        try {
            go();
        } catch (Exception e) {
            log.error("推流服务异常");
        }
        CacheUtil.PUSHWORKERMAP.remove(pushTask.getId());
    }

    public void startGrabber() throws Exception {
        grabber = new FFmpegFrameGrabber(pushTask.getPushSrcUrl());
        grabber.setOption("stimeout", "2000000");
        try {
            grabber.start();
            this.videoWidth = grabber.getImageWidth();
            this.videoHeight = grabber.getImageHeight();
        } catch (Exception e) {
            grabber.stop();
            grabber.close();
            log.error("启动拉流进程失败，请检查拉流地址");
        }
    }

    public void startPush() throws Exception {
        recorder = new FFmpegFrameRecorder(pushTask.getPushDescUrl(), this.videoWidth, this.videoHeight);
        AVFormatContext avFormatContext = grabber.getFormatContext();
        recorder.setFormat("flv");
        recorder.setAudioCodecName("aac");
        try {
            recorder.start(avFormatContext);
        } catch (Exception e) {
            grabber.stop();
            grabber.close();
            recorder.stop();
            recorder.close();
            log.error("启动推流过程失败，请检查拉流地址");
        }
    }

    public void go() throws Exception {
        startTime = System.currentTimeMillis();
        startGrabber();
        startPush();
        grabber.flush();
        isRunning = true;
        while (isRunning) {
            try {
                // 用于中断线程时，结束该循环
                Thread.sleep(1);
                AVPacket pkt = null;
                // 获取没有解码的音视频帧
                pkt = grabber.grabPacket();
                recorder.recordPacket(pkt);
                av_packet_unref(pkt);
            } catch (InterruptedException e) {
                log.error("推拉流进程出现异常");
                isRunning = false;
            }
        }
        grabber.stop();
        grabber.close();
        recorder.stop();
        recorder.close();
    }


}
