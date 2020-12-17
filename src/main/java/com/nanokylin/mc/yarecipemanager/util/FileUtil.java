package com.nanokylin.mc.yarecipemanager.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtil {
    /**
     * 创建一个新的文件
     *
     * @param path 路径
     * @return 是否成功
     * @author Hanbings
     */
    public static Boolean createFile(String path) {
        File file = new File(path);
        //如果文件夹不存在
        if (!file.getParentFile().exists()) {
            //创建文件夹
            Boolean create = file.getParentFile().mkdirs();
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个文件
     *
     * @param path 路径
     * @return 是否删除成功
     * @author Hanbings
     */
    public static boolean deleteFile(String path) {
        try {
            File file = new File(path);
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path 路径
     * @return 文件是否存在
     * @author Hanbings
     */
    public static boolean doesItExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 获取文件
     *
     * @author Hanbings
     */
    public static List<File> getFiles(String path) {
        File root = new File(path);
        List<File> files = new ArrayList<>();
        if (!root.isDirectory()) {
            files.add(root);
        } else {
            File[] subFiles = root.listFiles();
            for (File f : Objects.requireNonNull(subFiles)) {
                files.addAll(getFiles(f.getAbsolutePath()));
            }
        }
        return files;
    }

    /**
     * 获取目录中文件列表
     *
     * @param path 被扫描的目录
     * @return 文件列表Array
     * @author Hanbings
     */
    public static String[] getFileList(String path) {
        return new File(path).list();
    }

}
