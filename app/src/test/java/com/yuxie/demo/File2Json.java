package com.yuxie.demo;

import com.baselib.basebean.BaseRespose;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;
import com.yuxie.demo.bean.VideoListBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lankun on 2019/05/27
 */
public class File2Json {

    public static void file2Json() {

        String dir = "D:\\WebstormProjects\\yuxie2025.github.io\\video";
        String jsonFile = "D:\\json\\videoList.json";

        BaseRespose baseRespose = new BaseRespose();
        baseRespose.setStatus("200");
        baseRespose.setMessage("请求成功");

        List<VideoListBean> datas = new ArrayList<>();

        VideoListBean data;
        List<File> files = FileUtils.listFilesInDir(dir);
        String fileName;
        for (int i = 0; i < files.size(); i++) {
            fileName = files.get(i).getName();
            if (fileName.contains(".mp4")) {
                String name = fileName.substring(0,fileName.lastIndexOf("."));
                data = new VideoListBean();
                data.setId(i + 1);
                data.setUrl("https://yuxie2025.github.io/video/" + fileName);
                data.setThumbImageUrl("https://yuxie2025.github.io/video/" + name + ".jpg");
                datas.add(data);
            }
        }
        baseRespose.setData(datas);

        String jsonStr = new Gson().toJson(baseRespose);
        FileIOUtils.writeFileFromString(new File(jsonFile), jsonStr);
    }

}
