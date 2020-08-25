# FFmpegPusher接口

## PushTask定义
```java
class PushTask {
    String id;          // 推流任务id,唯一,基于该id对任务进行管理
    String pushSrcUrl;  // 推流源地址，可以是直播地址，或者文件地址
    String pushDescUrl; //  推理目的地址，一般为rtmp直播地址
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

**Path：** /youmu/pusher

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

**Path：** /youmu/pushers

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

**Path：** /youmu/pushers/status

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