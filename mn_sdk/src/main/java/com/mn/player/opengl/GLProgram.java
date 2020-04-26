package com.mn.player.opengl;

import android.opengl.GLES20;


import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static android.opengl.GLES20.glGetUniformLocation;


/**
 * @author: li_jianhua Date: 2016-8-18 上午11:30:22
 * To change this template use File | Settings | File Templates.
 * Description：YUV 转 RGB565
 * 1. new GLProgram()<br/>
 * 2. buildProgram()<br/>
 * 3. buildTextures()<br/>
 * 4. drawFrame()<br/>
 */

public class GLProgram {

    public int _program;
    public final int mWinPosition;
    public static int vCount = 0;//绘制三角形count
    public static FloatBuffer vertexBuffer; // 顶点缓冲区
    public static FloatBuffer textureBuffer; // 纹理坐标的缓冲区
    // texture id
    private int _textureI;
    private int _textureII;
    private int _textureIII;
    // texture index in gles
    private int _tIindex;
    private int _tIIindex;
    private int _tIIIindex;
    // vertices on screen
//    private float[] _vertices;
    // handles
    private int _positionHandle = -1, _coordHandle = -1;
    public int mUProjectMatrixHandler;//矩阵handler
    private int _yhandle = -1, _uhandle = -1, _vhandle = -1;
    private int _ytid = -1, _utid = -1, _vtid = -1;
    // vertices buffer
    private ByteBuffer _vertice_buffer;
    private ByteBuffer _coord_buffer;
    // video width and height
    private int _video_width = -1;
    private int _video_height = -1;
    // flow control
    private boolean isProgBuilt = false;


    /**
     * position can only be 0~4:<br/>
     * fullscreen => 0<br/>
     * left-top => 1<br/>
     * right-top => 2<br/>
     * left-bottom => 3<br/>
     * right-bottom => 4
     */
    public GLProgram(int position) {
        if (position < 0 || position > 4) {
            throw new RuntimeException("Index can only be 0 to 4");
        }
        mWinPosition = position;
        setup(mWinPosition);
        createBuffers(null);
        if(vertexBuffer == null && vCount == 0){
        	System.out.println("test22   GLProgram 加载顶点数据。。。11111");
        	init();
        	System.out.println("test22   GLProgram 加载顶点数据。。。2222");
        }

    }

    /**
     * prepared for later use
     */
    public void setup(int position) {
        switch (mWinPosition) {
        case 1:
//            _vertices = squareVertices1;
            _textureI = GLES20.GL_TEXTURE0;
            _textureII = GLES20.GL_TEXTURE1;
            _textureIII = GLES20.GL_TEXTURE2;
            _tIindex = 0;
            _tIIindex = 1;
            _tIIIindex = 2;
            break;
        case 2:
//            _vertices = squareVertices2;
            _textureI = GLES20.GL_TEXTURE3;
            _textureII = GLES20.GL_TEXTURE4;
            _textureIII = GLES20.GL_TEXTURE5;
            _tIindex = 3;
            _tIIindex = 4;
            _tIIIindex = 5;
            break;
        case 3:
//            _vertices = squareVertices3;
            _textureI = GLES20.GL_TEXTURE6;
            _textureII = GLES20.GL_TEXTURE7;
            _textureIII = GLES20.GL_TEXTURE8;
            _tIindex = 6;
            _tIIindex = 7;
            _tIIIindex = 8;
            break;
        case 4:
//            _vertices = squareVertices4;
            _textureI = GLES20.GL_TEXTURE9;
            _textureII = GLES20.GL_TEXTURE10;
            _textureIII = GLES20.GL_TEXTURE11;
            _tIindex = 9;
            _tIIindex = 10;
            _tIIIindex = 11;
            break;
        case 0:
        default:
//            _vertices = squareVertices;
            _textureI = GLES20.GL_TEXTURE0;
            _textureII = GLES20.GL_TEXTURE1;
            _textureIII = GLES20.GL_TEXTURE2;
            _tIindex = 0;
            _tIIindex = 1;
            _tIIIindex = 2;
            break;
        }
    }

    public boolean isProgramBuilt() {
        return isProgBuilt;
    }

    public void buildProgram() {
        if (_program <= 0) {
            _program = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        }

        /*
         * get handle for "vPosition" and "a_texCoord"
         */
        _positionHandle = GLES20.glGetAttribLocation(_program, "vPosition");
        checkGlError("glGetAttribLocation vPosition");
        if (_positionHandle == -1) {
            throw new RuntimeException("Could not get attribute location for vPosition");
        }
        _coordHandle = GLES20.glGetAttribLocation(_program, "a_texCoord");
        checkGlError("glGetAttribLocation a_texCoord");
        if (_coordHandle == -1) {
            throw new RuntimeException("Could not get attribute location for a_texCoord");
        }
        //软解码 矩阵 handler
        mUProjectMatrixHandler = glGetUniformLocation(_program,"uProjectMatrix");

        /*
         * get uniform location for y/u/v, we pass data through these uniforms
         */
        _yhandle = GLES20.glGetUniformLocation(_program, "tex_y");
        checkGlError("glGetUniformLocation tex_y");
        if (_yhandle == -1) {
            throw new RuntimeException("Could not get uniform location for tex_y");
        }
        _uhandle = GLES20.glGetUniformLocation(_program, "tex_u");
        //System.out.println("_uhandle = " + _uhandle);
        checkGlError("glGetUniformLocation tex_u");
        if (_uhandle == -1) {
            throw new RuntimeException("Could not get uniform location for tex_u");
        }
        _vhandle = GLES20.glGetUniformLocation(_program, "tex_v");
        //System.out.println("_vhandle = " + _vhandle);
        checkGlError("glGetUniformLocation tex_v");
        if (_vhandle == -1) {
            throw new RuntimeException("Could not get uniform location for tex_v");
        }

        isProgBuilt = true;
    }

    /**
     * build a set of textures, one for R, one for G, and one for B.
     */
    public void buildTextures(Buffer y, Buffer u, Buffer v, int width, int height) {
    	try {
    		boolean videoSizeChanged = (width != _video_width || height != _video_height);
            if (videoSizeChanged) {
                _video_width = width;
                _video_height = height;
                System.out.println("buildTextures videoSizeChanged: w=" + _video_width + " h=" + _video_height);
            }

            // building texture for Y data
            if (_ytid < 0 || videoSizeChanged) {
                if (_ytid >= 0) {
                    System.out.println("glDeleteTextures Y");
                    GLES20.glDeleteTextures(1, new int[] { _ytid }, 0);
                    checkGlError("glDeleteTextures");
                }
                // GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
                int[] textures = new int[1];
                GLES20.glGenTextures(1, textures, 0);
                checkGlError("glGenTextures");
                _ytid = textures[0];
                //System.out.println("glGenTextures Y = " + _ytid);
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _ytid);
            checkGlError("glBindTexture");
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, _video_width, _video_height, 0,
                    GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, y);
            checkGlError("glTexImage2D");
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            // building texture for U data
            if (_utid < 0 || videoSizeChanged) {
                if (_utid >= 0) {
                    System.out.println("glDeleteTextures U");
                    GLES20.glDeleteTextures(1, new int[] { _utid }, 0);
                    checkGlError("glDeleteTextures");
                }
                int[] textures = new int[1];
                GLES20.glGenTextures(1, textures, 0);
                checkGlError("glGenTextures");
                _utid = textures[0];
                //System.out.println("glGenTextures U = " + _utid);
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _utid);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, _video_width / 2, _video_height / 2, 0,
                    GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, u);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            // building texture for V data
            if (_vtid < 0 || videoSizeChanged) {
                if (_vtid >= 0) {
                    System.out.println("glDeleteTextures V");
                    GLES20.glDeleteTextures(1, new int[] { _vtid }, 0);
                    checkGlError("glDeleteTextures");
                }
                int[] textures = new int[1];
                GLES20.glGenTextures(1, textures, 0);
                checkGlError("glGenTextures");
                _vtid = textures[0];
                //System.out.println("glGenTextures V = " + _vtid);
            }
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _vtid);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, _video_width / 2, _video_height / 2, 0,
                    GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, v);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		} catch (Exception e) {
		}
    }

    /**
     * render the frame
     * the YUV data will be converted to RGB by shader.
     */
    public void drawFrame() {
    	// 添加program到OpenGL ES环境中
        GLES20.glUseProgram(_program);

        GLES20.glVertexAttribPointer(_positionHandle, 2, GLES20.GL_FLOAT, false, 8, _vertice_buffer);
        GLES20.glEnableVertexAttribArray(_positionHandle);

        GLES20.glVertexAttribPointer(_coordHandle, 2, GLES20.GL_FLOAT, false, 8, _coord_buffer);
        GLES20.glEnableVertexAttribArray(_coordHandle);

        // bind textures
        GLES20.glActiveTexture(_textureI);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _ytid);
        GLES20.glUniform1i(_yhandle, _tIindex);

        GLES20.glActiveTexture(_textureII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _utid);
        GLES20.glUniform1i(_uhandle, _tIIindex);

        GLES20.glActiveTexture(_textureIII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _vtid);
        GLES20.glUniform1i(_vhandle, _tIIIindex);

        // 绘制正方形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glFinish();
        GLES20.glDisableVertexAttribArray(_positionHandle);
        GLES20.glDisableVertexAttribArray(_coordHandle);
    }


    public int flag = 0;
    public int flag2 = 0;
    float M_PI  =  3.14f;
    public void draw2() {
//    	gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);//楼下算法
//    	GLES20.glActiveTexture(_textureI);
    	GLES20.glUseProgram(_program);
    	for (int phi = 0; phi<360; phi+=6){
    		for (int theta = 0; theta<60; theta+=6){
    			/*将纹理映射到四边形上*/
    			/*纹理的坐标和四边形顶点的对应*/
    			float s_t = (float)Math.sin(theta/180.0*M_PI);
    			float c_t = (float)Math.cos(theta/180.0*M_PI);
    			float s_p = (float)Math.sin(phi/180.0*M_PI);
    			float c_p = (float)Math.cos(phi/180.0*M_PI);
    			float s_t_n = (float)Math.sin(((theta+6)%360)/180.0*M_PI);
    			float c_t_n = (float)Math.cos(((theta+6)%360)/180.0*M_PI);
    			float s_p_n = (float)Math.sin((phi+6)/180.0*M_PI);
    			float c_p_n = (float)Math.cos((phi+6)/180.0*M_PI);
    			//顶点坐标
    			float vtx1[] = {
    					s_t*c_p ,    s_t*s_p ,    c_t,
    					s_t*c_p_n,   s_t*s_p_n,   c_t,
    					s_t_n*c_p_n, s_t_n*s_p_n, c_t_n,
    					s_t_n*c_p,   s_t_n*s_p,   c_t_n
    			};

    			//纹理坐标
    			float tex1[] = {
    					0.5f+0.5f*s_t*c_p,     1.0f-(0.5f+0.5f*s_t*s_p),
    					0.5f+0.5f*s_t*c_p_n,   1.0f-(0.5f+0.5f*s_t*s_p_n),
    					0.5f+0.5f*s_t_n*c_p_n, 1.0f-(0.5f+0.5f*s_t_n*s_p_n),
    					0.5f+0.5f*s_t_n*c_p,   1.0f-(0.5f+0.5f*s_t_n*s_p)
    			};

    			FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(
    					vtx1.length * 4)
    	                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    			vertexBuffer.put(vtx1).position(0);

    			FloatBuffer textureBuffer = ByteBuffer.allocateDirect(
    					tex1.length * 4)
    	                .order(ByteOrder.nativeOrder()).asFloatBuffer();
    			textureBuffer.put(tex1).position(0);


    			GLES20.glVertexAttribPointer(_positionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
    	        GLES20.glEnableVertexAttribArray(_positionHandle);

    	        GLES20.glVertexAttribPointer(_coordHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
    	        GLES20.glEnableVertexAttribArray(_coordHandle);

    	        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    		}
    	}
    	GLES20.glActiveTexture(_textureI);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _ytid);
        GLES20.glUniform1i(_yhandle, _tIindex);

        GLES20.glActiveTexture(_textureII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _utid);
        GLES20.glUniform1i(_uhandle, _tIIindex);

        GLES20.glActiveTexture(_textureIII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _vtid);
        GLES20.glUniform1i(_vhandle, _tIIIindex);

//        GLES20.glDisableVertexAttribArray(_positionHandle);
//        GLES20.glDisableVertexAttribArray(_coordHandle);
    	//画图结束
    	GLES20.glFinish();
    }

	public void paintGL() {
		GLES20.glUseProgram(_program);
		/*for (int rad = 0; rad < 36; rad++){// 径向量
			// Y平面循环
			for (int rotate = 0; rotate < 36; rotate++){// 旋转量
				if (rad == 0) {
					if (rotate == 35) {
						// 顶点坐标
						float vtx1[] = {
								0, 0, -1,//单位球，半径改变时需要改
		                        m_points[rad][rotate][0],m_points[rad][rotate][1],m_points[rad][rotate][2],
		                        m_points[rad][0][0],m_points[rad][0][1],m_points[rad][0][2],
						};
						// 纹理坐标
						float tex1[] = {
								WC/Image_W, HC/Image_H,// 第一个纹理坐标 (左下角)
		                        texture[rad][rotate][0], texture[rad][rotate][1],
		                        texture[rad][0][0], texture[rad][0][1],
						};

						// 顶点缓冲区
						FloatBuffer vertexBuffer = ByteBuffer
								.allocateDirect(vtx1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						vertexBuffer.put(vtx1).position(0);
						// 纹理缓冲区
						FloatBuffer textureBuffer = ByteBuffer
								.allocateDirect(tex1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						textureBuffer.put(tex1).position(0);

						GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer);
						GLES20.glEnableVertexAttribArray(_positionHandle);
						GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer);
						GLES20.glEnableVertexAttribArray(_coordHandle);
						GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

					} else {
						// 顶点坐标
						float vtx1[] = {
								0, 0, -1,//单位球，半径改变时需要改
		                        m_points[rad][rotate][0],m_points[rad][rotate][1],m_points[rad][rotate][2],
		                        m_points[rad][rotate+1][0],m_points[rad][rotate+1][1],m_points[rad][rotate+1][2],
						};
						// 纹理坐标
						float tex1[] = {
								WC/Image_W, HC/Image_H,// 第一个纹理坐标 (左下角)
		                        texture[rad][rotate][0], texture[rad][rotate][1],
		                        texture[rad][rotate+1][0], texture[rad][rotate+1][1],
						};
						// 顶点缓冲区
						FloatBuffer vertexBuffer = ByteBuffer
								.allocateDirect(vtx1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						vertexBuffer.put(vtx1).position(0);
						// 纹理缓冲区
						FloatBuffer textureBuffer = ByteBuffer
								.allocateDirect(tex1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						textureBuffer.put(tex1).position(0);

						GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer);
						GLES20.glEnableVertexAttribArray(_positionHandle);
						GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer);
						GLES20.glEnableVertexAttribArray(_coordHandle);
						GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

					}
				} else {
					if (rotate == 35) {
						*//****************** 第一个三角形 ********************************//*
						// 顶点坐标
						float vtx1[] = {
								m_points[rad-1][rotate][0],m_points[rad-1][rotate][1],m_points[rad-1][rotate][2],
		                        m_points[rad][rotate][0],m_points[rad][rotate][1],m_points[rad][rotate][2],
		                        m_points[rad][0][0],m_points[rad][0][1],m_points[rad][0][2],
						};
						// 纹理坐标
						float tex1[] = {
								texture[rad-1][rotate][0], texture[rad-1][rotate][1],
		                        texture[rad][rotate][0], texture[rad][rotate][1],
		                        texture[rad][0][0], texture[rad][0][1],
						};

						// 顶点缓冲区
						FloatBuffer vertexBuffer = ByteBuffer
								.allocateDirect(vtx1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						vertexBuffer.put(vtx1).position(0);
						// 纹理缓冲区
						FloatBuffer textureBuffer = ByteBuffer
								.allocateDirect(tex1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						textureBuffer.put(tex1).position(0);

						GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer);
						GLES20.glEnableVertexAttribArray(_positionHandle);
						GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer);
						GLES20.glEnableVertexAttribArray(_coordHandle);
						GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
						*//****************** 第二个三角形 ********************************//*
						// 顶点坐标
						float vtx12[] = {
								m_points[rad-1][rotate][0],m_points[rad-1][rotate][1],m_points[rad-1][rotate][2],
		                        m_points[rad][0][0],m_points[rad][0][1],m_points[rad][0][2],
		                        m_points[rad-1][0][0],m_points[rad-1][0][1],m_points[rad-1][0][2],
						};
						// 纹理坐标
						float tex12[] = {
								texture[rad-1][rotate][0], texture[rad-1][rotate][1],
		                        texture[rad][0][0], texture[rad][0][1],
		                        texture[rad-1][0][0], texture[rad-1][0][1],
						};

						// 顶点缓冲区
						FloatBuffer vertexBuffer2 = ByteBuffer
								.allocateDirect(vtx12.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						vertexBuffer2.put(vtx12).position(0);
						// 纹理缓冲区
						FloatBuffer textureBuffer2 = ByteBuffer
								.allocateDirect(tex12.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						textureBuffer2.put(tex12).position(0);

						GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer2);
						GLES20.glEnableVertexAttribArray(_positionHandle);
						GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer2);
						GLES20.glEnableVertexAttribArray(_coordHandle);
						GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
					} else {
						*//****************** 第一个三角形 ********************************//*
						// 顶点坐标
						float vtx1[] = {
								m_points[rad-1][rotate][0],m_points[rad-1][rotate][1],m_points[rad-1][rotate][2],
		                        m_points[rad][rotate][0],m_points[rad][rotate][1],m_points[rad][rotate][2],
		                        m_points[rad][rotate+1][0],m_points[rad][rotate+1][1],m_points[rad][rotate+1][2],
						};
						// 纹理坐标
						float tex1[] = {
								texture[rad-1][rotate][0], texture[rad-1][rotate][1],
		                        texture[rad][rotate][0], texture[rad][rotate][1],
		                        texture[rad][rotate+1][0], texture[rad][rotate+1][1],
						};

						// 顶点缓冲区
						FloatBuffer vertexBuffer = ByteBuffer
								.allocateDirect(vtx1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						vertexBuffer.put(vtx1).position(0);
						// 纹理缓冲区
						FloatBuffer textureBuffer = ByteBuffer
								.allocateDirect(tex1.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						textureBuffer.put(tex1).position(0);

						GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer);
						GLES20.glEnableVertexAttribArray(_positionHandle);
						GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer);
						GLES20.glEnableVertexAttribArray(_coordHandle);
						GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

						*//****************** 第二个三角形 ********************************//*
						// 顶点坐标
						float vtx12[] = {
								m_points[rad-1][rotate][0],m_points[rad-1][rotate][1],m_points[rad-1][rotate][2],
		                        m_points[rad][rotate+1][0],m_points[rad][rotate+1][1],m_points[rad][rotate+1][2],
		                        m_points[rad-1][rotate+1][0],m_points[rad-1][rotate+1][1],m_points[rad-1][rotate+1][2],
						};
						// 纹理坐标
						float tex12[] = {
								texture[rad-1][rotate][0], texture[rad-1][rotate][1],
		                        texture[rad][rotate+1][0], texture[rad][rotate+1][1],
		                        texture[rad-1][rotate+1][0], texture[rad-1][rotate+1][1],
						};

						// 顶点缓冲区
						FloatBuffer vertexBuffer2 = ByteBuffer
								.allocateDirect(vtx12.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						vertexBuffer2.put(vtx12).position(0);
						// 纹理缓冲区
						FloatBuffer textureBuffer2 = ByteBuffer
								.allocateDirect(tex12.length * 4)
								.order(ByteOrder.nativeOrder()).asFloatBuffer();
						textureBuffer2.put(tex12).position(0);

						GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer2);
						GLES20.glEnableVertexAttribArray(_positionHandle);
						GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer2);
						GLES20.glEnableVertexAttribArray(_coordHandle);
						GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
					}
				}
			}
		}*/

		GLES20.glVertexAttribPointer(_positionHandle, 3,GLES20.GL_FLOAT, false, 0, vertexBuffer);
		GLES20.glEnableVertexAttribArray(_positionHandle);
		GLES20.glVertexAttribPointer(_coordHandle, 2,GLES20.GL_FLOAT, false, 0, textureBuffer);
		GLES20.glEnableVertexAttribArray(_coordHandle);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);

		GLES20.glActiveTexture(_textureI);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _ytid);
		GLES20.glUniform1i(_yhandle, _tIindex);

		GLES20.glActiveTexture(_textureII);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _utid);
		GLES20.glUniform1i(_uhandle, _tIIindex);

		GLES20.glActiveTexture(_textureIII);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _vtid);
		GLES20.glUniform1i(_vhandle, _tIIIindex);
		// 画图结束
		GLES20.glFinish();
	}

    /**
     * create program and load shaders, fragment shader is very important.
     */
    public int createProgram(String vertexSource, String fragmentSource) {
        // create shaders
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        // just check
        System.out.println("vertexShader = " + vertexShader);
        System.out.println("pixelShader = " + pixelShader);

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
//                Utils.LOGE("Could not link program: ", null);
//                Utils.LOGE(GLES20.glGetProgramInfoLog(program), null);
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    /**
     * create shader with given source.
     */
    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
//                Utils.LOGE("Could not compile shader " + shaderType + ":", null);
//                Utils.LOGE(GLES20.glGetShaderInfoLog(shader), null);
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * these two buffers are used for holding vertices, screen vertices and texture vertices.
     */
    void createBuffers(float[] vert) {
//        _vertice_buffer = ByteBuffer.allocateDirect(vert.length * 4);
//        _vertice_buffer.order(ByteOrder.nativeOrder());
//        _vertice_buffer.asFloatBuffer().put(vert);
//        _vertice_buffer.position(0);

    	 _vertice_buffer = ByteBuffer.allocateDirect(squareVertices.length * 4);
         _vertice_buffer.order(ByteOrder.nativeOrder());
         _vertice_buffer.asFloatBuffer().put(squareVertices);
         _vertice_buffer.position(0);

        if (_coord_buffer == null) {
            _coord_buffer = ByteBuffer.allocateDirect(coordVertices.length * 4);
            _coord_buffer.order(ByteOrder.nativeOrder());
            _coord_buffer.asFloatBuffer().put(coordVertices);
            _coord_buffer.position(0);
        }
    }


//    float Image_H=1080.0f;//图像高度（pixels）
//    float Image_W=1920.0f;//图像宽度（pixels）
//    float HC=680.64f;//畸变中心的H方向坐标，左上角为原点（pixels）
//    float WC=946.0f;//畸变中心的W方向坐标，左上角为原点（pixels）
//    double a0=567.4797f;//多项式0次项系数
//    double a2=-7.1035e-4;//多项式2次项系数
//    double a3=3.4579e-7;//多项式3次项系数
//    double a4=-5.1822e-10;//多项式4次项系数
//    double W_div=Image_W/72.0f;//纹理坐标与图像像素坐标转换系数
//    double H_div=Image_H/72.0f;//纹理坐标与图像像素坐标转换系数

    float Image_H=980.0f;//图像高度（pixels）
    float Image_W=1792.0f;//图像宽度（pixels）
    float HC=595.4288f;//畸变中心的H方向坐标，左上角为原点（pixels）
    float WC=897.8561f;//畸变中心的W方向坐标，左上角为原点（pixels）
    double a0=566.3531;//多项式0次项系数
    double a2=-7.9609e-4;//多项式2次项系数
    double a3=5.7513e-7;//多项式3次项系数
    double a4=-6.879e-10;//多项式4次项系数
    double W_div=Image_W/72.0f;//纹理坐标与图像像素坐标转换系数
    double H_div=Image_H/72.0f;//纹理坐标与图像像素坐标转换系数


    //我们将使用points数组来存放网格各顶点独立的x，y，z坐标。这里网格由45×45点形成，
    //换句话说也就是由44格×44格的小方格子依次组成了。
    float texture[][][] = new float[36][180][2];//用于存储纹理顶点数组
    float m_points[][][] = new float[36][180][3]; // 用于存储对应的3D网格顶点数组 Points网格顶点数组
//    private FloatBuffer vertexBuffer; // 顶点缓冲区
//    private FloatBuffer textureBuffer; // 纹理坐标的缓冲区
//    int vCount=0;
    public void init(){
    	ArrayList<Float> alVertix = new ArrayList<Float>();//存放顶点坐标的ArrayList
    	ArrayList<Float> alTexture = new ArrayList<Float>();//存放纹理坐标的ArrayList
    	for(int rad=0; rad<36; rad++){//径向量
	        // Y平面循环 （径向由内到外）
	        for(int rotate=0; rotate<180; rotate++){//旋转量

	            float theta=(float)(rotate)*2.0f*3.141592654f*2.0f/360.0f;//弧度值(2.0*rotate=360)
	            float r_pixel=(float) ((rad+1)*0.96*Image_W/(2*36.0f));//注意：rad+1。径向值，像素为单位，Image_W后期调试可以更改其值，改变的是纹理区域的范围(0.92 切黑边用的  0.97会有一部分的黑边改小解决问题)

	            /*****************构建球面向量*****************/
	            float X_pixel=r_pixel*(float)Math.sin(theta);//X方向值，像素为单位//围绕坐标轴顺时针转动
	            float Y_pixel=r_pixel*(float)Math.cos(theta);//Y方向值，像素为单位
	            float Z_pixel=(float) (a0+a2*r_pixel*r_pixel+a3*r_pixel*r_pixel*r_pixel+a4*r_pixel*r_pixel*r_pixel*r_pixel);//Z方向值，像素为单位
	            float R_pixel= (float) Math.sqrt(X_pixel*X_pixel+Y_pixel*Y_pixel+Z_pixel*Z_pixel);//三维向量的模
	            float X_sphere=X_pixel/R_pixel;//归一化
	            float Y_sphere=Y_pixel/R_pixel;//归一化
	            float Z_sphere=Z_pixel/R_pixel;//归一化

	            /*****************根据畸变中心就对应上述球面顶点的纹理坐标*****************/
	            float x_texture=(WC+X_pixel)/Image_W;//纹理坐标（0,1），原点在左上角
	            float y_texture=(HC-Y_pixel)/Image_H;//方向纹理坐标（0,1），原点在左上角
	            m_points[rad][rotate][0]=X_sphere;//三维数组存储三维向量
	            m_points[rad][rotate][1]=Y_sphere;
	            m_points[rad][rotate][2]=-Z_sphere;

	            texture[rad][rotate][0]=x_texture;
	            texture[rad][rotate][1]=y_texture;
	        }
	    }


    	for (int rad = 0; rad < 36; rad++){// 径向量
			// Y平面循环
			for (int rotate = 0; rotate < 180; rotate++){// 旋转量
				if (rad == 0) {
					if (rotate == 179) {
						// 顶点坐标
						alVertix.add(0f);alVertix.add(0f);alVertix.add(-1f);
						alVertix.add(m_points[rad][rotate][0]);alVertix.add(m_points[rad][rotate][1]);alVertix.add(m_points[rad][rotate][2]);
						alVertix.add(m_points[rad][0][0]);alVertix.add(m_points[rad][0][1]);alVertix.add(m_points[rad][0][2]);
						// 纹理坐标
						alTexture.add(WC/Image_W);alTexture.add(HC/Image_H);
						alTexture.add(texture[rad][rotate][0]);alTexture.add(texture[rad][rotate][1]);
						alTexture.add(texture[rad][0][0]);alTexture.add(texture[rad][0][1]);
					}else{
						// 顶点坐标
						alVertix.add(0f);alVertix.add(0f);alVertix.add(-1f);
						alVertix.add(m_points[rad][rotate][0]);alVertix.add(m_points[rad][rotate][1]);alVertix.add(m_points[rad][rotate][2]);
						alVertix.add(m_points[rad][rotate+1][0]);alVertix.add(m_points[rad][rotate+1][1]);alVertix.add(m_points[rad][rotate+1][2]);
						// 纹理坐标
						alTexture.add(WC/Image_W);alTexture.add(HC/Image_H);
						alTexture.add(texture[rad][rotate][0]);alTexture.add(texture[rad][rotate][1]);
						alTexture.add(texture[rad][rotate+1][0]);alTexture.add(texture[rad][rotate+1][1]);
					}
	            }else{
	            	if (rotate == 179) {
	            		/****************** 第一个三角形 ********************************/
						// 顶点坐标
						alVertix.add(m_points[rad-1][rotate][0]);alVertix.add(m_points[rad-1][rotate][1]);alVertix.add(m_points[rad-1][rotate][2]);
						alVertix.add(m_points[rad][rotate][0]);alVertix.add(m_points[rad][rotate][1]);alVertix.add(m_points[rad][rotate][2]);
						alVertix.add(m_points[rad][0][0]);alVertix.add(m_points[rad][0][1]);alVertix.add(m_points[rad][0][2]);
						// 纹理坐标
						alTexture.add(texture[rad-1][rotate][0]);alTexture.add(texture[rad-1][rotate][1]);
						alTexture.add(texture[rad][rotate][0]);alTexture.add(texture[rad][rotate][1]);
						alTexture.add(texture[rad][0][0]);alTexture.add(texture[rad][0][1]);
						/****************** 第二个三角形 ********************************/
						// 顶点坐标
						alVertix.add(m_points[rad-1][rotate][0]);alVertix.add(m_points[rad-1][rotate][1]);alVertix.add(m_points[rad-1][rotate][2]);
						alVertix.add(m_points[rad][0][0]);alVertix.add(m_points[rad][0][1]);alVertix.add(m_points[rad][0][2]);
						alVertix.add(m_points[rad-1][0][0]);alVertix.add(m_points[rad-1][0][1]);alVertix.add(m_points[rad-1][0][2]);
						// 纹理坐标
						alTexture.add(texture[rad-1][rotate][0]);alTexture.add(texture[rad-1][rotate][1]);
						alTexture.add(texture[rad][0][0]);alTexture.add(texture[rad][0][1]);
						alTexture.add(texture[rad-1][0][0]);alTexture.add(texture[rad-1][0][1]);
	            	}else{
	            		/****************** 第一个三角形 ********************************/
						// 顶点坐标
						alVertix.add(m_points[rad-1][rotate][0]);alVertix.add(m_points[rad-1][rotate][1]);alVertix.add(m_points[rad-1][rotate][2]);
						alVertix.add(m_points[rad][rotate][0]);alVertix.add(m_points[rad][rotate][1]);alVertix.add(m_points[rad][rotate][2]);
						alVertix.add(m_points[rad][rotate+1][0]);alVertix.add(m_points[rad][rotate+1][1]);alVertix.add(m_points[rad][rotate+1][2]);
						// 纹理坐标
						alTexture.add(texture[rad-1][rotate][0]);alTexture.add(texture[rad-1][rotate][1]);
						alTexture.add(texture[rad][rotate][0]);alTexture.add(texture[rad][rotate][1]);
						alTexture.add(texture[rad][rotate+1][0]);alTexture.add(texture[rad][rotate+1][1]);

						/****************** 第二个三角形 ********************************/
						// 顶点坐标
						alVertix.add(m_points[rad-1][rotate][0]);alVertix.add(m_points[rad-1][rotate][1]);alVertix.add(m_points[rad-1][rotate][2]);
						alVertix.add(m_points[rad][rotate+1][0]);alVertix.add(m_points[rad][rotate+1][1]);alVertix.add(m_points[rad][rotate+1][2]);
						alVertix.add(m_points[rad-1][rotate+1][0]);alVertix.add(m_points[rad-1][rotate+1][1]);alVertix.add(m_points[rad-1][rotate+1][2]);
						// 纹理坐标
						alTexture.add(texture[rad-1][rotate][0]);alTexture.add(texture[rad-1][rotate][1]);
						alTexture.add(texture[rad][rotate+1][0]);alTexture.add(texture[rad][rotate+1][1]);
						alTexture.add(texture[rad-1][rotate+1][0]);alTexture.add(texture[rad-1][rotate+1][1]);
	            	}
	            }
			}
    	}

    	vCount=alVertix.size()/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
        //将alVertix中的坐标值转存到一个float数组中
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertix.size();i++){
    		vertices[i]=alVertix.get(i);
    	}
        //创建顶点坐标数据缓冲
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        vertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        vertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        vertexBuffer.position(0);//设置缓冲区起始位置

        // 创建纹理坐标缓冲
        float textureCoors[] = new float[alTexture.size()];// 顶点纹理值数组
        for (int i = 0; i < alTexture.size(); i++) {
            textureCoors[i] = alTexture.get(i);
        }

        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length * 4);
        cbb.order(ByteOrder.nativeOrder());// 设置字节顺序
        textureBuffer = cbb.asFloatBuffer();// 转换为int型缓冲
        textureBuffer.put(textureCoors);// 向缓冲区中放入顶点着色数据
        textureBuffer.position(0);// 设置缓冲区起始位置

    }

    private void checkGlError(String op) {
        /*int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            //Utils.LOGE("***** " + op + ": glError " + error, null);
            throw new RuntimeException(op + ": glError " + error);
        }*/
    }

    public void changeTextures(float zWidth){
    	coordVertices[1] = 1f - zWidth -0.1f;
    	coordVertices[2] = 1f - zWidth;
    	coordVertices[3] = 1f - zWidth -0.1f;
    	coordVertices[6] = 1f - zWidth;

    	_coord_buffer = null;
        _coord_buffer = ByteBuffer.allocateDirect(coordVertices.length * 4);
        _coord_buffer.order(ByteOrder.nativeOrder());
        _coord_buffer.asFloatBuffer().put(coordVertices);
        _coord_buffer.position(0);

    }

    static float[] squareVertices1 = { -1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, }; // left-top

    static float[] squareVertices2 = { 0.0f, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f, }; // right-bottom

    static float[] squareVertices3 = { -1.0f, -1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, }; // left-bottom

    static float[] squareVertices4 = { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, }; // right-top

    //YUV顶点坐标
    private float[] squareVertices = { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, }; // fullscreen
    /**
     * 纹理坐标
     */
    private float[] coordVertices = { 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, };// 坐标数组顺序 1.left-top,2.right-top,3.left-bottom,4.right-bottom
//    private static float[] coordVertices = { 0.0f, 0.7f,
//    										 0.8f, 0.7f,
//    										 0.0f, 0.0f,
//    										 0.8f, 0.0f
//    										};


    //静静的shader
    /* private static final String VERTEX_SHADER =
    		"attribute vec4 vPosition;\n"
    		+ "attribute vec2 a_texCoord;\n"
    		+ "uniform mat4 uProjectMatrix;\n"
            + "varying vec2 tc;\n"
    		+ "void main() {\n"
            + " gl_Position = uProjectMatrix * vPosition;\n"
    		+ " tc = a_texCoord;\n"
            + "}\n";

    private static final String FRAGMENT_SHADER =
    		"varying highp vec2 tc;\n"
    		+"uniform sampler2D tex_y;\n"
    		+"uniform sampler2D tex_u;\n"
    		+"uniform sampler2D tex_v;\n"
    		+"void main(){\n"
    		+"    mediump vec3 yuv;\n"
    		+"    lowp vec3 rgb;\n"
    		+"    yuv.x = texture2D(tex_y, tc).r;\n"
    		+"    yuv.y = texture2D(tex_u, tc).r - 0.5;\n"
    		+"    yuv.z = texture2D(tex_v, tc).r - 0.5;\n"
    		//+"		yuv.x=1.1643*(yuv.x-0.0625);\n"
    		+"    rgb = mat3(1,       1,         1,"
    		+"               0,       -0.39465,  2.03211,"
    		+"               1.13983, -0.58060,  0) * yuv;\n"
    		+"    gl_FragColor = vec4(rgb, 1);\n"
    		+"}\n";*/


    //建华的
    private final String VERTEX_SHADER =
			"attribute vec4 vPosition;\n"
			+ "attribute vec2 a_texCoord;\n"
			+ "uniform mat4 uProjectMatrix;\n"
		    + "varying vec2 tc;\n"
			+ "void main() {\n"
		    + " gl_Position = uProjectMatrix * vPosition;\n"
			+ " tc = a_texCoord;\n"
		    + "}\n";

    private final String FRAGMENT_SHADER =
    		"precision mediump float;\n"
    		+ "uniform sampler2D tex_y;\n"
            + "uniform sampler2D tex_u;\n"
    		+ "uniform sampler2D tex_v;\n"
            + "varying vec2 tc;\n"
    		+ "void main() {\n"
            + "vec4 c = vec4((texture2D(tex_y, tc).r - 16./255.) * 1.164);\n"
            + "vec4 U = vec4(texture2D(tex_u, tc).r - 128./255.);\n"
            + "vec4 V = vec4(texture2D(tex_v, tc).r - 128./255.);\n"
            + "c += V * vec4(1.596, -0.813, 0, 0);\n"
            + "c += U * vec4(0, -0.392, 2.017, 0);\n"
            + "c.a = 1.0;\n"
            + "gl_FragColor = c;\n"
            + "}\n";

}