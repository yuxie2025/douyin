package com.yuxie.demo;

import com.blankj.utilcode.util.FileUtils;

import org.junit.Test;

import java.io.File;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {

//        ios2android();

        android();

//        SecureRandom sr2 = SecureRandom.getInstance("SHA1PRNG");

//        System.out.println("sr2:"+sr2.toString());

        HashMap<String, String> map = new HashMap<>();
        map.put("test","123");
        map.put("test5","123");
        map.put("test1","1234");

        System.out.print("map:"+map.toString());

        map.remove("test","123");
        System.out.print("map11:"+map.toString());

    }

    /**
     * ios 2x,3x转android drawable-xhdpi和drawable-xxhdpi
     */
    public void ios2android() {

        //源目录
        String srcDir = "D:\\ios2android\\ios";
        //目标目录2x
        String targetDirx = "D:\\ios2android\\android\\drawable-xhdpi";
        //目标目录3x
        String targetDir2x = "D:\\ios2android\\android\\drawable-xxhdpi";
        //默认目录
        String targetDir = "D:\\ios2android\\android\\drawable-xhdpi";

        List<File> srcFiles = FileUtils.listFilesInDir(srcDir);

        if (srcFiles.size() == 0) {
            System.out.println("请在\"" + srcDir + "\"目录放入需要转换的文件");
            return;
        }
        //删除目标目录下的所有文件
        System.out.println("正在删除目标目录...");
        FileUtils.deleteAllInDir(targetDirx);
        FileUtils.deleteAllInDir(targetDir2x);

        File srcFile;
        String srcFileName;
        String targetFileName;

        for (int i = 0; i < srcFiles.size(); i++) {
            srcFile = srcFiles.get(i);
            srcFileName = FileUtils.getFileName(srcFile);
            //替换
            targetFileName = replace(srcFileName);

            //复制文件
            boolean result = FileUtils.copyFile(srcFile.getAbsolutePath(), targetDir, new FileUtils.OnReplaceListener() {
                @Override
                public boolean onReplace() {
                    return true;
                }
            });
            System.out.println("第" + (i + 1) + "个文件  " + FileUtils.getFileName(srcFile) + "  转换后:" + targetFileName + "  转换结果:" + result);
        }
    }

    public void android() {

        //源目录
        String srcDir = "D:\\ios2android\\ios";
//        //目标目录2x
//        String targetDirx = "D:\\ios2android\\android\\drawable-xhdpi";
//        //目标目录3x
//        String targetDir2x = "D:\\ios2android\\android\\drawable-xxhdpi";
//        //默认目录
        String targetDir = "D:\\ios2android\\ios2";

        List<File> srcFiles = FileUtils.listFilesInDir(srcDir);

        if (srcFiles.size() == 0) {
            System.out.println("请在\"" + srcDir + "\"目录放入需要转换的文件");
            return;
        }

        File srcFile;
        String srcFileName;
        String targetFileName;

        for (int i = 0; i < srcFiles.size(); i++) {
            srcFile = srcFiles.get(i);
            srcFileName = FileUtils.getFileName(srcFile);
            //替换
            targetFileName = replace(srcFileName);

            String targetDir2 = targetDir + "\\" + targetFileName;

            //复制文件
            boolean result = FileUtils.copyFile(srcFile.getAbsolutePath(), targetDir2, new FileUtils.OnReplaceListener() {
                @Override
                public boolean onReplace() {
                    return true;
                }
            });
            System.out.println("第" + (i + 1) + "个文件  " + FileUtils.getFileName(srcFile) + "  转换后:" + targetFileName + "  转换结果:" + result);
        }
    }

    /**
     * 替换文本
     *
     * @param srcFileName
     * @return
     */
    private String replace(String srcFileName) {

        //大写转小写
        String targetFileName = srcFileName.toLowerCase();
        //替换-号
        if (targetFileName.contains("-")) {
            targetFileName = targetFileName.replace("-", "_");
        }
        //替换@2x
        if (targetFileName.contains("@2x")) {
            targetFileName = targetFileName.replace("@2x", "");
        }
        //替换@3x
        if (targetFileName.contains("@3x")) {
            targetFileName = targetFileName.replace("@3x", "");
        }
        //替换空格
        if (targetFileName.contains(" ")) {
            targetFileName = targetFileName.replace(" ", "");
        }
        return targetFileName;
    }
}