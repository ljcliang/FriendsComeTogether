package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class ModifyLuXianXuXieModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"id":"832","title":"明年","content":"哦弄","imgList":[{"pfpID":"2977","imgUrl":"http://www.tongbanapp.com/uploads/xingcheng/20200907/85c1ecfb2d1e7c908e6fa3a96a81a81f6380.jpg","message":""}]}
     */

    private int code;
    private String message;
    private ObjBean obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 832
         * title : 明年
         * content : 哦弄
         * imgList : [{"pfpID":"2977","imgUrl":"http://www.tongbanapp.com/uploads/xingcheng/20200907/85c1ecfb2d1e7c908e6fa3a96a81a81f6380.jpg","message":""}]
         */

        private String id;
        private String title;
        private String content;
        private List<ImgListBean> imgList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public static class ImgListBean {
            /**
             * pfpID : 2977
             * imgUrl : http://www.tongbanapp.com/uploads/xingcheng/20200907/85c1ecfb2d1e7c908e6fa3a96a81a81f6380.jpg
             * message :
             */

            private String pfpID;
            private String imgUrl;
            private String message;

            public String getPfpID() {
                return pfpID;
            }

            public void setPfpID(String pfpID) {
                this.pfpID = pfpID;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
