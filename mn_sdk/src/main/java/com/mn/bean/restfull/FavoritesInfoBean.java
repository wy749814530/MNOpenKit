package com.mn.bean.restfull;

import java.util.List;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class FavoritesInfoBean extends BaseBean {

    private List<FavoritePointBean> list;

    public List<FavoritePointBean> getList() {
        return list;
    }

    public void setList(List<FavoritePointBean> list) {
        this.list = list;
    }
}
