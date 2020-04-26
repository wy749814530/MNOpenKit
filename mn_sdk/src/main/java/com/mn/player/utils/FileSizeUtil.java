package com.mn.player.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WIN on 2017/10/18.
 */

public class FileSizeUtil {
    public static final int SIZETYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;//获取文件大小单位为GB的double值
    private static final String ALL_FILES = "ALL";
    /**
     * 获取文件指定文件的指定单位的大小
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath,int sizeType){
        File file=new File(filePath);
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize, sizeType);
    }
    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath){
        File file=new File(filePath);
        long blockSize=0;
        try {
            if(file.isDirectory()){
                blockSize = getFileSizes(file);
            }else{
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FormetFileSize(blockSize);
    }
    /**
     * 获取指定文件大小
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception
    {
        long size = 0;
        if (file.exists()){
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        else{
            file.createNewFile();
        }
        return size;
    }

    /**
     * 获取指定文件夹
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File file) throws Exception
    {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++){
            if (flist[i].isDirectory()){
                size = size + getFileSizes(flist[i]);
            }
            else{
                size =size + getFileSize(flist[i]);
            }
        }
        return size;
    }
    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824){
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else{
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
    /**
     * 转换文件大小,指定转换的类型
     * @param fileS
     * @param sizeType
     * @return
     */
    public static double FormetFileSize(long fileS,int sizeType)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong=Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong=Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 文件是否为指定文件类型
     * @param fileName
     * @param suffixs
     * @exp comparesuffix("sdcard/aaa/aaa.txt", .mp4/.mp3")
     * @return
     */
    public static boolean comparesuffix(String fileName, String suffixs){
        String[] fixs  = suffixs.split("/");
        for (String fix : fixs){
            if(fileName.endsWith(fix)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定文件夹下某些类型的文件
     * @param path
     * @param _files
     * @return
     */
    private static List<File> traverseFolder(String path, List<File> _files, String suffixs) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] mfiles = file.listFiles();
            if (mfiles.length == 0) {
                return _files;
            } else {
                for (File file2 : mfiles) {
                    if (file2.isDirectory()) {
                        traverseFolder(file2.getAbsolutePath(), _files, suffixs);
                    } else {
                        if (ALL_FILES.equals(suffixs)){
                            _files.add(file2);
                        }else if (comparesuffix(file2.getName(), suffixs)){
                            _files.add(file2);
                        }
                    }
                }
            }
            return _files;
        }
        return _files;
    }
    /**
     * 获取指定文件夹下所有类型的文件
     * @param path
     * @exp traverseFolder("sdcard/aaa")
     * @return
     */
    public static List<File> traverseFolder(String path) {
        return traverseFolder(path, new ArrayList<File>(), ALL_FILES);
    }
    /**
     * 获取指定文件夹下某些类型的文件
     * @param path
     * @param suffixs
     * @exp traverseFolder("sdcard/aaa", ".mp4/.mp3")
     * @return
     */
    public static List<File> traverseFolder(String path, String suffixs) {
        return traverseFolder(path, new ArrayList<File>(), suffixs);
    }
}
