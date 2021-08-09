package com.yuxie.demo;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.yuxie.demo.utils.douyin.Douyin;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedUnitTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testDouYin() throws Exception {
        String fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        String msgFromDouYin = "大家帮我们看看昨天去看的房，两居室的那个房子看中了，就是周边不咋地，有懂的吗？#沪漂 #买房 #宝妈分享  https://v.douyin.com/JbKWX3g/ 复制此链接，打开抖音搜索，直接观看视频！";
        boolean re = Douyin.downloadVideo(msgFromDouYin, fileDir);
        System.out.println("下载结果re:" + re);
    }
}
