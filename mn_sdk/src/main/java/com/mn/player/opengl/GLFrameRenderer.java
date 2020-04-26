package com.mn.player.opengl;

import android.annotation.SuppressLint;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.scaleM;
import static android.opengl.Matrix.translateM;

/**
 * @author: j Date: 2016-8-18 上午10:48:22
 * To change this template use File | Settings | File Templates.
 * Description：YUV 渲染
 */
public class GLFrameRenderer implements Renderer {
    private String TAG = GLFrameRenderer.class.getSimpleName();
    public int deviceType = 0;//判断是否是180度摄像机 -1:180 0:IPC
    public int mVideoWidth = 0;
    public int mVideoHeight = 0;
    public ByteBuffer y = null;
    public ByteBuffer u = null;
    public ByteBuffer v = null;
    public GLProgram prog = new GLProgram(0);
    public float xAngle = 0;
    public float yAngle = 0;
    public float zAngle = 0;
    public float mAngleX = 0;
    public float mAngleY = 0;
    public float mAngleZ = 0;

    // 缩放矩阵x轴参数
    public float scaleX = 0.6f;

    // 缩放矩阵y轴参数
    public float scaleY = 0.6f;

    // 缩放矩阵z轴参数
    public float scaleZ = 0.6f;

    // 缩放基数 IPC = 0.1  180度摄像机 = 0.3
    public float scaleBase = 0.1f;

    // 缩放到最小时的初始值
    public float scaleInitX = 0.6f;
    public float scaleInitY = 1f;
    public int _is180Open = 1;          // 展开标志 0:展开  1:默认

    final float mCurrMatrix[] = new float[16];
    private final float[] projectMatrix = new float[16];
    final float mMVPMatrix[] = new float[16];
    int ANGLE = 0;

    public float[] getfinalMVPMatrix() {
        Matrix.multiplyMM(mMVPMatrix, 0, projectMatrix, 0, mCurrMatrix, 0);
        Matrix.setIdentityM(mCurrMatrix, 0);
        return mMVPMatrix;
    }

    public GLFrameRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 该方法在渲染开始前调用，OpenGL ES的绘制上下文被重建时也会调用。
        // 当Activity暂停时，绘制上下文会丢失，当Activity恢复时，绘制上下文会重建。
        if (!prog.isProgramBuilt()) {
            prog.buildProgram();
//            int mTextureID = createTextureID();
//            mDirectDrawer = new GLES20DirectDrawer(mTextureID);
        }
        // 设置屏幕背景色RGBA 如需要改背景色：用RGBA值/255 的值写到下面即可
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // 打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);    // 所作深度测试的类型
    }

    int view_w = 0;
    int view_h = 0;

    @SuppressLint("NewApi")
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 当surface 的尺寸发生改变时，该方法被调用。往往在这里设置Viewport.或者Camara等。
        Log.i(TAG, "onSurfaceChanged width : " + width + " , height : " + height);
        GLES20.glViewport(0, 0, width, height);////设置视口的大小以及位置， 视口：也就是图形最终显示到屏幕的区域，前两个参数是视口的位置，后两个参数是视口的宽和长。
        view_w = width;
        view_h = height;
        ratio = (float) width / height;

        // 缩放：IPC占满屏
        if (deviceType == 0) {
            //缩放：IPC占满屏
            scaleY = 0.5f;
            scaleX = (float) ratio * scaleY;
        } else {
            if (_is180Open == 1) {
                scaleX = 1f;
                scaleY = 1f;
                scaleInitX = scaleX;
                scaleInitY = scaleY;
                xAngle = 0;
                yAngle = 0;
                zAngle = 0;
                Matrix.setLookAtM(projectMatrix, 0, 0, 0, 0, 0f, 0f, -2f, 0, 1, 0);
            }
        }
        scaleInitX = ratio / 2;
        scaleInitY = scaleY;
        // 调用此方法计算产生透视投影矩阵
        Matrix.frustumM(projectMatrix, 0, -ratio, ratio, -1, 1, 1, 80);
        // 设置正交投影参数
        // Matrix.orthoM(projectMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

        Matrix.setIdentityM(mCurrMatrix, 0);
        Matrix.setIdentityM(mMVPMatrix, 0);

        ANGLE = 80;//设置resize后，初始视角的大小

        translateM(projectMatrix, 0, 0, 0, -2);
        scaleM(projectMatrix, 0, 4, 4, 4);
    }

    int _xflag = 0;

    @SuppressLint("NewApi")
    public void changeMatrix() {
        if (deviceType == -1) {//点击展开
            if (_is180Open == 0) {
                if (_xflag == 0) {
                    _xflag = 1;
                    scaleX = 1.5f;
                } else {
                    scaleX = 1.5f;
                }
                scaleY = scaleX;
                scaleInitX = scaleX;
                mAngleY = 0f;
                mAngleX = 0f;
                ANGLE = 80;
                m_z_translate = 0;
                Matrix.perspectiveM(projectMatrix, 0, ANGLE, (float) view_w / (float) view_h, 0.1f, 100.0f);//投影转换矩阵
            } else {
                if (_is180Open == 1) {  // 还原展开
                    scaleX = 1f;
                    scaleY = 1f;
                    scaleInitX = scaleX;
                    scaleInitY = scaleY;
                    xAngle = 0;
                    yAngle = 0;
                    zAngle = 0;
                    Matrix.setLookAtM(projectMatrix, 0, 0, 0, 0, 0f, 0f, -2f, 0, 1, 0);//视见转换矩阵
                    //  isBigPtz=false;
                }
            }
        } else {
            if (_is180Open == 0) {
                scaleX = 1f;
                scaleY = 0.5f;
                scaleInitX = scaleX;
                scaleInitY = scaleY;
                xAngle = 0;
                yAngle = 0;
                zAngle = 0;
                if (ratio != 0) {
                    Matrix.frustumM(projectMatrix, 0, -ratio, ratio, -1, 1, 1, 100);
                }
                Matrix.setIdentityM(mCurrMatrix, 0);
                Matrix.setIdentityM(mMVPMatrix, 0);
                translateM(projectMatrix, 0, 0, 0, -2);
                scaleM(projectMatrix, 0, 8, 8, 8);
            } else {
                scaleX = 1f;
                scaleY = 0.5f;
                scaleInitX = scaleX;
                scaleInitY = scaleY;
                xAngle = 0;
                yAngle = 0;
                zAngle = 0;
                if (ratio != 0) {
                    Matrix.frustumM(projectMatrix, 0, -ratio, ratio, -1, 1, 1, 100);
                }
                Matrix.setIdentityM(mCurrMatrix, 0);
                Matrix.setIdentityM(mMVPMatrix, 0);
                translateM(projectMatrix, 0, 0, 0, -2);
                scaleM(projectMatrix, 0, 4, 4, 4);
                //  isBigPtz=false;
            }
        }

    }


    private float ratio;
    // 缩放事件
    float m_z_translate = 0;

    ///拖拽
    public void drag(float dx, float dy) {
        ///计算放大倍率
        float magnificate = (scaleY - scaleInitY) / scaleInitY;
        float rightShift = Math.abs(xAngle + dx * 0.002f);
        if (dx > 0) {
            if (rightShift > magnificate / 2) {
                xAngle = magnificate / 2;
            } else {
                xAngle += dx * 0.002f;// 设置填充椭圆绕y轴旋转的角度
                mAngleX += dx * 0.1f;
            }
        } else if (dx < 0) {
            if (rightShift > magnificate / 2) {
                xAngle = -magnificate / 2;
            } else {
                xAngle += dx * 0.002f;// 设置填充椭圆绕y轴旋转的角度
                mAngleX += dx * 0.1f;
            }
        }
        float upShift = Math.abs(yAngle - dy * 0.002f);
        if (dy > 0) {
            if (upShift > magnificate / 2) {
                yAngle = -magnificate / 2;
            } else {
                yAngle -= dy * 0.002f;// 设置填充椭圆绕y轴旋转的角度
                mAngleY += dy * 0.1f;
            }
        }
        if (dy < 0) {
            if (upShift > magnificate / 2) {
                yAngle = magnificate / 2;
            } else {
                yAngle -= dy * 0.002f;// 设置填充椭圆绕y轴旋转的角度
                mAngleY += dy * 0.1f;
            }
        }
    }

    ///缩小
    public void narrow(float changeRate) {
        float zoomRatios = (scaleY - changeRate - scaleInitY)
                / scaleInitY;
        if (xAngle > 0) {
            if (xAngle > zoomRatios / 2) {
                xAngle = zoomRatios / 2;
            }
        } else if (xAngle < 0) {
            if (xAngle < -zoomRatios / 2) {
                xAngle = -zoomRatios / 2;
            }
        }
        if (yAngle > 0) {
            if (yAngle > zoomRatios / 2) {
                yAngle = zoomRatios / 2;
            }
        } else if (yAngle < 0) {
            if (yAngle < -zoomRatios / 2) {
                yAngle = -zoomRatios / 2;
            }
        }
        scaleX -= changeRate;
        scaleY -= changeRate;
    }

    @SuppressLint("NewApi")
    public void wheelEvent(float angle) {
        //int angle=event->delta();
        if (angle > 0 && ANGLE < 170)
            ANGLE -= ANGLE / 20.0f;
        else if (angle < 0 && ANGLE > 3)
            ANGLE += ANGLE / 15.0f;

        if (angle == -80) {
            mAngleX = 0;
            mAngleY = 0;
            ANGLE = 110;
        }

        if (ANGLE < 80)
            m_z_translate = 0;
        else
            m_z_translate = (80.0f - ANGLE) / 50.0f;

        Matrix.perspectiveM(projectMatrix, 0, ANGLE, (float) view_w / (float) view_h, 0.1f, 100.0f);
    }

    // 一个球体里，很少会用平移的，都是在球心上做旋转的
    @Override
    public void onDrawFrame(GL10 gl) {
        try {
            _lock.lock();
            // 设置背景色
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);// 设置模式窗口的背景颜色，颜色采用的是RGBA值
            // 清除屏幕和深度缓存
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
            if (y != null && u != null && v != null) {
                y.position(0);
                u.position(0);
                v.position(0);
                prog.buildTextures(y, u, v, mVideoWidth, mVideoHeight);
                if (deviceType == -1) {
                    if (_is180Open == 0) {
                        Matrix.translateM(mCurrMatrix, 0, 0, 0, m_z_translate);
                        // 旋转矩阵M
                        rotateM(mCurrMatrix, 0, -mAngleY, 1, 0, 0);
                        rotateM(mCurrMatrix, 0, -mAngleX, 0, 1, 0);
                    } else {
                        // 缩放
                        Matrix.scaleM(mCurrMatrix, 0, scaleX, scaleY, 0);
                    }
                } else {
                    // 移动转换
                    Matrix.translateM(mCurrMatrix, 0, xAngle, yAngle, zAngle);
                    // 缩放
                    Matrix.scaleM(mCurrMatrix, 0, scaleX, scaleY, 0);
                }
                glUniformMatrix4fv(prog.mUProjectMatrixHandler, 1, false, getfinalMVPMatrix(), 0);
                if (deviceType == -1 && _is180Open == 0) {
                    prog.paintGL();
                } else {
                    prog.drawFrame();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            _lock.unlock();
        }

    }

    /**
     * this method will be called from native code, it happens when the video is about to play or
     * the video size changes.
     */
    public void update(int w, int h) {
        try {
            if (w > 0 && h > 0) {
                // 初始化容器
                if (w != mVideoWidth || h != mVideoHeight) {
                    this.mVideoWidth = w;
                    this.mVideoHeight = h;
                    int yarraySize = w * h;
                    int uvarraySize = yarraySize / 4;
                    _lock.lock();
                    ydata = new byte[yarraySize];
                    udata = new byte[uvarraySize];
                    vdata = new byte[uvarraySize];
                    y = ByteBuffer.allocate(yarraySize);
                    u = ByteBuffer.allocate(uvarraySize);
                    v = ByteBuffer.allocate(uvarraySize);
                    _lock.unlock();
                    // System.out.println("test22: GLFrameRenderer w= " + w + " h= " + h + " mVideoWidth= " + mVideoWidth + " mVideoHeight= " + mVideoHeight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method will be called from native code, it's used for passing yuv data to me.
     * 改Ydata 上面去掉一行
     * for (int i = 0; i < m_videoW; i++){
     * m_pByYuv420Data[i] = 0;
     * m_pByYuv420Data[m_videoW * (m_videoH - 1) + i] = 0;
     * }
     */
    private Lock _lock = new ReentrantLock();

    public ByteBuffer getYUVByteBuffer(int[] nWidth, int[] nHeight) {
        if (0 == mVideoWidth || 0 == mVideoHeight) return null;

        ByteBuffer yuv = ByteBuffer.allocate(mVideoWidth * mVideoHeight * 3 / 2);
        _lock.lock();
        nWidth[0] = mVideoWidth;
        nHeight[0] = mVideoHeight;

        yuv.put(y);
        yuv.put(u);
        yuv.put(v);
        _lock.unlock();

        return yuv;
    }


    int width = 352; //为了防止切换readers时y , u , v 为null, 设置一个默认渲染大小。
    int height = 288;

    public void update(ByteBuffer _ydata, ByteBuffer _udata, ByteBuffer _vdata) {
        try {
            _lock.lock();
            if (y == null || u == null || v == null) {
                update(width, height);
            }
            y.clear();
            u.clear();
            v.clear();

            _ydata.get(ydata, 0, ydata.length);
            _udata.get(udata, 0, udata.length);
            _vdata.get(vdata, 0, vdata.length);

            // 180度像机 Y数据第一行和最后一行置成黑色 uv=128
            if (deviceType == -1) {
                for (int i = 0; i < mVideoWidth; i++) {
                    ydata[i] = 0;
                    ydata[mVideoWidth * (mVideoHeight - 1) + i] = 0;
                }
                for (int i = 0; i < mVideoWidth / 2; i++) {
                    udata[i] = (byte) 128;
                    udata[mVideoWidth / 2 * (mVideoHeight / 2 - 1) + i] = (byte) 128;
                    vdata[i] = (byte) 128;
                    vdata[mVideoWidth / 2 * (mVideoHeight / 2 - 1) + i] = (byte) 128;
                }
            }
            y.put(ydata, 0, ydata.length);
            u.put(udata, 0, udata.length);
            v.put(vdata, 0, vdata.length);
            _lock.unlock();
            //mTargetSurface.requestRender();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private byte[] ydata;
    private byte[] udata;
    private byte[] vdata;

    public void clear() {
        y = null;
        u = null;
        v = null;
        ydata = null;
        udata = null;
        vdata = null;
    }

    public void stop() {
        try {
            //   System.out.println("test22  start....GLFrameRenderer  stop....");
            _lock.lock();
            if (y != null) y.clear();
            if (u != null) u.clear();
            if (v != null) v.clear();
            y = null;
            u = null;
            v = null;
            mVideoWidth = 0;
            mVideoHeight = 0;
            _lock.unlock();
            Thread.sleep(1000);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            //   mTargetSurface.requestRender();
            //  System.out.println("test22   end....GLFrameRenderer  stop....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stopCloud() {
        try {
            //   System.out.println("test22  start....GLFrameRenderer  stop....");
            //_lock.lock();
            if (y != null) y.clear();
            if (u != null) u.clear();
            if (v != null) v.clear();
            y = null;
            u = null;
            v = null;
            mVideoWidth = 0;
            mVideoHeight = 0;
            // _lock.unlock();
            // Thread.sleep(1000);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            //     mTargetSurface.requestRender();
            //   System.out.println("test22   end....GLFrameRenderer  stop....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        stopCloud();
        prog = null;
        //  mTargetSurface = null;

    }

    @SuppressLint("InlinedApi")
    private int createTextureID() {
        /** The texture pointer 纹理指针*/
        //纹理可以用来表示图像，照片，甚至由一个数学算法生成的分形数据
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        //指定缩小过滤方式(硬解码为流模式 需要此类型GLES11Ext.GL_TEXTURE_EXTERNAL_OES)
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        //指定放大过滤方式
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        //指定S坐标轴贴图模式
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        //指定T坐标轴贴图模式
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        return textures[0];
    }

}
