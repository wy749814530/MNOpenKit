package com.mn.player.utils;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by hjz on 2017/9/5.
 */

public class FileUtil {

    //删除文件 夹下面的文件
    public static void deleteFile(String fileName) {
        //先清空之前的文件
        File baseFile = new File(fileName);
        if (baseFile != null && baseFile.exists()) {
            if (baseFile.isFile()) {
                deleteSeletect(fileName);
            } else if (baseFile.isDirectory()) {
                File[] f = baseFile.listFiles();
                if (f != null) {
                    for (int i = f.length - 1; i > -1; --i) {
                        if (f[i].isFile() && f[i].length() > 0) {
                            f[i].delete();
                        }
                    }
                }
            }
        }
    }

    //删除单个文件
    public static boolean deleteSeletect(String path) {
        if (path == "") {
            return false;
        }
        File file = new File(path);
        try {
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件后缀
     *
     * @param url
     * @return
     */
    public static String getFileSuffix(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        File file = new File(url);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    /**
     * 修改文件后缀
     *
     * @param url
     * @param suffix
     * @return
     */
    public static String modifyFileSuffix(String url, String suffix) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        File file = new File(url);
        String fileName = file.getName();
        String dsuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return url.replace(dsuffix, suffix);
    }

    //时间排序
    public static List<String> sortList(List<String> list) {
        List<String> lstTemp = new ArrayList<String>();
        int nCount1 = list.size();
        for (int n = 0; n < nCount1; n++) {
            String strMaxItem = Collections.max(list);
            if (!lstTemp.contains(strMaxItem)) {
                lstTemp.add(strMaxItem);
            }
            list.remove(strMaxItem);
        }
        list.clear();
        for (String strNewItem : lstTemp) {
            list.add(strNewItem);
        }
        return list;
    }
}
