package com.mn.bean.restfull;

import java.util.List;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class FavoriteDelteBean extends BaseBean{

    /**
     * count : 1
     * list : ["0"]
     */

    private int count;
    private List<String> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
