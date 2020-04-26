package com.mn.bean.restfull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by WIN on 2017/11/9.
 */

public class BaseBean<T> implements Serializable{

    private static final long serialVersionUID = 2376912667949219941L;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void toSerialzable(T obj, String filename){
        //序列化操作1--FileOutputStream
        ObjectOutputStream oos1 = null;
        try {
            oos1 = new ObjectOutputStream(new FileOutputStream(filename));
            oos1.writeObject("Worm storage By FileOutputStream ");
            oos1.writeObject(obj);
            oos1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
