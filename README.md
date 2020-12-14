# FFmpegWebPusher
基于FFmpeg、SpringBoot、JavaCV开发的远程推流服务，可通过http-api实现远程拉流、推流、转发流等功能，实现拉流直播、第三方直播等功能。

P.S. 之前使用node实现的简易版，[web-ffmpeg-handler-nodejs](https://github.com/ZWboy97/web-ffmpeg-handler)

# 环境与依赖
```
IDE:     IDEA           : IDEA-2018
Build:   Maven          : 3.6.3
VERSION: SpringBoot     : 2.2.2
VERSION: JDK            : Oracle 8u201
VERSION: FFMPEG         : 4.0.6
VERSION: GLIBC          : 2.29
```

# 特性
1. 通过SpringBoot提供 Restful API
2. 使用Java线程池技术，支持并发推流
3. 使用[JavaCV](https://github.com/bytedeco/javacv)，相比直接调用ffmpeg命令行，更加灵活，支持更多的定制。
4. 容器化部署，简化部署流程

# 使用
## 1. 容器部署
由于同时需要java8以及ffmpeg环境，为了部署方便，通过容器的方式简化部署流程。

镜像地址：[ffmpeg-web-pusher](https://hub.docker.com/r/zwboy/ffmpeg-web-pusher)

```
# 拉取镜像
docker pull zwboy/ffmpeg-web-pusher
# 运行容器
docker run -d -it -p 8080:8080 zwboy/ffmpeg-web-pusher
# 访问接口
http://localhost:8080/pushers/status?id=12345
# 进入容器
docker exec -it container_id
```

## 2. 使用jar部署
使用SpringBoot基于IDEA开发，使用Maven进行依赖管理
``` 
1. git clone 项目代码
2. 将项目导入到IDEA中，以Maven项目导入
3. 运行测试系统，是否工作正常
4. 使用maven对SpringBoot进行打包，生成的jar包位于target目录下
5. 在服务器上安装java8以及ffmpeg环境，ffmpeg版本建议大于4.0
6. 将jar包上传到服务器，执行命令: nohup java -jar app.jar --server.port=8080 & 
```

# HTTP接口定义

## PushTask定义
```java
class PushTask {
    String id;          // 推流任务id,唯一,基于该id对任务进行管理
    String pushSrcUrl;  // 推流源地址，一般为RTMP地址(暂不支持文件推流，之后有空计划支持，欢迎PR)
    String pushDescUrl; // 推流目的地址，一般为RTMP直播地址
}
```
## WorkerStatus定义
```java
class WorkerStatus {
    Integer status;     // 当前推流进程的工作状态
                        // 为 0：running，正在推流
                        // 为 1：not existing, 流不存在
                        // 为 2：stopped，推流已停止
                        // 为 3：waiting，等待启动中
    long runningTime;   // 当前推流进程的工作时长，时间为毫秒
}
```

## 启动一个推流任务

**Path：** /pushers

**Method：** POST


**接口描述：**
<p>向接口post PushTask的相关字段，服务将基于提供的推流和拉流地址启动一个推流进程</p>


### 请求参数
**Body:** { id, pushSrcUrl, pushDescUrl} 

详见`PushTask`的字段



### 返回数据
返回一个`WorkerStatus`，status字段，包括如下几种情况
- 启动成功：status为 0，或者3
- 启动失败：status为 2

由于启动推流需要进程调度时间，返回的结果不一定准确，前端最好以正在启动处理，隔一段时间后，通过状态接口检查是否启动成功。

            
## 停止一个推流

**Path：** /pushers

**Method：** DELETE

**接口描述：**
<p>停止指定的推流进程</p>


### 请求参数
**Query:** id=taskid

### 返回数据
返回一个`WorkerStatus`，status字段，包括如下几种情况
- 停止成功：status为 2，表示已停止
- id指定的流不存在：status为 1，表示流不存在
            
            
            
## 查看推流进程状态

**Path：** /pushers/status

**Method：** GET

**接口描述：**
<p>查看指定id的推流进程，返回其工作状态</p>


### 请求参数
**Query：** id=taskId
            
### 返回数据
返回一个`WorkerStatus`，status字段，包括如下几种情况
- 0：running，推流进行中
- 1：not existing，不存在该流
- 3：waiting，等待调度推流中，该状态较少见
