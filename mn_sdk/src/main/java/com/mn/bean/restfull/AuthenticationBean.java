package com.mn.bean.restfull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WIN on 2017/10/13.
 */

public class AuthenticationBean extends BaseBean {
    private static final long serialVersionUID = 112970449959997381L;
    private List<UrlsBean> urls;

    public List<UrlsBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlsBean> urls) {
        this.urls = urls;
    }

    public static class UrlsBean implements Serializable {
        /**
         * presignedurl : https://manniu-cn-north-1.s3.cn-north-1.amazonaws.com.cn/003/1527048548/1511742063761.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20171127T035634Z&X-Amz-SignedHeaders=host&X-Amz-Expires=86400&X-Amz-Credential=AKIAOVQUXF4JJYZ63LCA%2F20171127%2Fcn-north-1%2Fs3%2Faws4_request&X-Amz-Signature=0f9a8989c470627ecb7440a20c6a6c68394b8855eb3a64208638a9407ae97a5f
         * expiration : 3600
         * file_size : 261353
         */

        private String presignedurl;
        private int expiration;
        private int file_size;

        public String getPresignedurl() {
            return presignedurl;
        }

        public void setPresignedurl(String presignedurl) {
            this.presignedurl = presignedurl;
        }

        public int getExpiration() {
            return expiration;
        }

        public void setExpiration(int expiration) {
            this.expiration = expiration;
        }

        public int getFile_size() {
            return file_size;
        }

        public void setFile_size(int file_size) {
            this.file_size = file_size;
        }
    }
}
