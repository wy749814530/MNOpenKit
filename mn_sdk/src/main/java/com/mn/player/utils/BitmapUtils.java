package com.mn.player.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;

/**
 * Created by jz on 2017/8/17.
 */

public class BitmapUtils {

    public static Bitmap getBitmapFromUri(Context mContext, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 销毁头像文件(缓存)
     */
    public static void clearCache(String path, String filename) {
        File fileDir = new File(path);
        if (fileDir.exists()) {
            File file = new File(path, filename);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    static double[][] YUV2RGB_CONVERT_MATRIX = {{1, 0, 1.4022}, {1, -0.3456, -0.7145}, {1, 1.771, 0}};

    public static void YUV420SP2RGB565(byte[] rgb, byte[] _y, byte[] _u, byte[] _v, int width, int height) {
        byte[] yuv420sp = new byte[width * height * 3 / 2];

        System.arraycopy(_y, 0, yuv420sp, 0, _y.length);
        System.arraycopy(_u, 0, yuv420sp, _y.length, _u.length);
        System.arraycopy(_v, 0, yuv420sp, _y.length + _u.length, _v.length);

        int uIndex = width * height;
        int vIndex = uIndex + ((width * height) >> 2);
        int gIndex = width * height;
        int bIndex = gIndex * 2;
        int temp = 0;


        for (int y = 0; y < height; y++) {
            long begin = System.currentTimeMillis();

            for (int x = 0; x < width; x++) {
                // R分量
                temp = (int) (yuv420sp[y * width + x] + (yuv420sp[vIndex + (y / 2) * (width / 2) + x / 2] - 128) * YUV2RGB_CONVERT_MATRIX[0][2]);
                rgb[y * width + x] = (byte) (temp < 0 ? 0 : (temp > 255 ? 255 : temp));
                // G分量
                temp = (int) (yuv420sp[y * width + x] + (yuv420sp[uIndex + (y / 2) * (width / 2) + x / 2] - 128) * YUV2RGB_CONVERT_MATRIX[1][1] + (yuv420sp[vIndex + (y / 2) * (width / 2) + x / 2] - 128) * YUV2RGB_CONVERT_MATRIX[1][2]);
                rgb[gIndex + y * width + x] = (byte) (temp < 0 ? 0 : (temp > 255 ? 255 : temp));
                // B分量
                temp = (int) (yuv420sp[y * width + x] + (yuv420sp[uIndex + (y / 2) * (width / 2) + x / 2] - 128) * YUV2RGB_CONVERT_MATRIX[2][1]);
                rgb[bIndex + y * width + x] = (byte) (temp < 0 ? 0 : (temp > 255 ? 255 : temp));
            }

            long end = System.currentTimeMillis();

            System.out.println("costxxx : " + (end - begin) + "ms");
        }
    }


//    public static void YUV420SP2RGB565(byte[] rgb, byte[] _y, byte[] _u, byte[] _v, int width, int height) {
//        final int frameSize = width * height;
//
//        byte[] yuv420sp = new byte[width * height * 3 / 2];
//
//        System.arraycopy(_y, 0, yuv420sp, 0, _y.length);
//        System.arraycopy(_u, 0, yuv420sp, _y.length, _u.length);
//        System.arraycopy(_v, 0, yuv420sp, _y.length + _u.length, _v.length);
//
//        for (int j = 0, yp = 0; j < height; j++) {
//            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
//
//            for (int i = 0; i < width; i++, yp++) {
//                int y = (0xff & ((int) yuv420sp[yp])) - 16;
//                if (y < 0) y = 0;
//                if ((i & 1) == 0) {
//                    v = (0xff & yuv420sp[uvp++]) - 128;
//                    u = (0xff & yuv420sp[uvp++]) - 128;
//                }
//                int y1192 = 1192 * y;
//                int r = (y1192 + 1634 * v);
//                int g = (y1192 - 833 * v - 400 * u);
//                int b = (y1192 + 2066 * u);
//                if (r < 0) r = 0;
//                else if (r > 262143) r = 262143;
//                if (g < 0) g = 0;
//                else if (g > 262143) g = 262143;
//                if (b < 0) b = 0;
//                else if (b > 262143) b = 262143;
//
//                int a = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
//                rgb[yp * 4 + 0] = (byte)(a >> 6 & 0xFF);
//                rgb[yp * 4 + 1] = (byte)(a >> 4 & 0xFF);
//                rgb[yp * 4 + 2] = (byte)(a >> 2 & 0xFF);
//                rgb[yp * 4 + 3] = (byte)(a & 0xFF);
//            }
//        }
//
//        yuv420sp = null;
//    }

    public static Bitmap getRGB565toBitmap(byte[] outBytes, int width, int height) {
        Bitmap bmp = null;
        try {
            byte[] bmpBuff = null;
            ByteBuffer byteBuffer = null;
            if (outBytes == null)
                return null;
            int width_frame = width;
            int height_frame = height;
            if (width_frame > 0 && height_frame > 0) {
                bmpBuff = new byte[width_frame * height_frame * 4];
                bmp = Bitmap.createBitmap(width_frame,
                        height_frame,
                        Bitmap.Config.RGB_565);

                if (bmpBuff != null) {
                    System.arraycopy(outBytes, 0, bmpBuff, 0, outBytes.length);
                    byteBuffer = ByteBuffer.wrap(bmpBuff);
                    bmp.copyPixelsFromBuffer(byteBuffer);
                }
            }
        } catch (Exception e) {

        }

        return bmp;
    }

    //将Byte数组转换成文件
    public static void getFileByBytes(byte[] bytes, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        long l = System.currentTimeMillis();
        try {
            File dir = new File(fileName);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveBitmaptoFile(Bitmap bitmap, String filename) {
        if (bitmap == null) {
            return;
        }
        File f = new File(filename);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Handler handler = new Handler(Looper.getMainLooper());
    //2018.8.20
    public static Bitmap byteToBitmap(byte[] imgByte, int x, int y) {
        InputStream input = null;
        Bitmap bitmap = null;
        Options options = new Options();
        options.outWidth = x;
        options.outHeight = y;
        options.inSampleSize = 1;

        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int getinSampleSize(Options options, int x, int y) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int reqHeight = x;
        int reqWidth = y;
        int inSampleSize = 1;

        if (height > width && height > reqHeight) {
            inSampleSize = (int) Math.ceil(height / reqHeight);
            System.out.println(inSampleSize + "inSampleSize" + reqHeight + ",," + reqWidth);
        } else if (height <= width && width > reqWidth) {
            inSampleSize = (int) Math.ceil(width / reqWidth);
            System.out.println(inSampleSize + "inSampleSize");

        }
        System.out.println("inSampleSize" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 压缩图片
     *
     * @param originalPath  原始图片路径
     * @param thumbnailPath 缩略图路径
     * @param i             压缩比例,最终为原图的1/(i^2)
     * @return
     */
    private Bitmap onCreateThumbnail(String originalPath, String thumbnailPath, int i) {
        Options options = new Options();
        //设置为不读内容，值读取边界值
        options.inJustDecodeBounds = true;
        //通过编辑，得到边界值，并存入到option中
        BitmapFactory.decodeFile(originalPath, options);
        //赋值缩放比例
        options.inSampleSize = i;
        //设置显示的图片格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //设置为读取内容，
        options.inJustDecodeBounds = false;
        //得到缩略图
        return BitmapFactory.decodeFile(thumbnailPath, options);
    }

    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static void bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath, 480, 800);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
    }
}
