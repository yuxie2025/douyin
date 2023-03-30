# 抖音无水印下载工具(20230330,测试可以使用) 最新方案

# 这是一个抖音无水印视频下载工具:

### 安装包下载地址: https://raw.githubusercontent.com/yuxie2025/douyin/douyin/download/douyin.apk

# 原理:

## 1.webView伪装PC浏览器,UserAgent

```
webView.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:91.0) Gecko/20100101 Firefox/91.0");
```

## 2.使用webView打开抖音分享链接,setWebViewClient->shouldInterceptRequest拦截无水印视频链接

```
https://v26-web.douyinvod.com/d9ec5407864eff09d9daef3faca2ef5e/6422a9c1/video/tos/cn/tos-cn-ve-15c001-alinc2/osLh7xvAIIEEBdBZnCQeJoeDUenQu0A9XAb55J/?
a=6383&ch=26&cr=3&dr=0&lr=all&cd=0%7C0%7C0%7C3&cv=1&br=3748&bt=3748&cs=0&ds=4&ft=bvTKJbQQqUYqfJEZPo0OW_EklpPiX9A_ZMVJEH28f2vPD-I&
mime_type=video_mp4&qs=0&rc=M2Q7ZDg1ZmQ1MzU3ZWQzNUBpamV1a2Y6ZjhyajMzNGkzM0BjMV81YmJhX2ExL15hNTMwYSNrbjRvcjRvc2RgLS1kLS9zcw%3D%3D
&l=20230328154752918F62A33B161C0D313A&btag=8000&testst=1679989686676
```

## 3.使用:

1.打开抖音>分享>复制链接

2.粘贴链接>点击下载

3.跳转到WebView打开>等待提示无水印视频下载成功提示即可(10-20秒)

4.下载文件保存在Download目录下

# 安装包下载地址: https://raw.githubusercontent.com/yuxie2025/douyin/douyin/download/douyin.apk

[抖音无水印](https://raw.githubusercontent.com/yuxie2025/douyin/douyin/download/douyin.apk) 下载地址:

![下载链接](https://raw.githubusercontent.com/yuxie2025/douyin/douyin/download/download_qr.png)

app截图:

![app主页截图](https://raw.githubusercontent.com/yuxie2025/douyin/douyin/download/home.png)