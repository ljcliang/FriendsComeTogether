package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class ActivityEditorModel {
    /**
     * code : 200
     * message : 操作成功
     * obj : {"FriendsList":{"pfID":"166","pftitle":"厕所","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20200907/3f53117d8f67ebb4ca98b6132c1442e74144.jpg","pfaddress":"going","gotime":"2020-09-07","price":"100.00"},"RenewList":[{"pfptime":"2020-09-07 13:29","id":"832","pfptitle":"明年","pfpcontent":"哦弄","pfpurl":"http://www.tongbanapp.com/uploads/xingcheng/20200907/85c1ecfb2d1e7c908e6fa3a96a81a81f6380.jpg"},{"pfptime":"2020-09-07 13:31","id":"833","pfptitle":"厕所续写","pfpcontent":"","pfpurl":"http://www.tongbanapp.com/uploads/xingcheng/20200907/2f26892d67f31ff9a059a01207946e967022.jpg"}]}
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
         * FriendsList : {"pfID":"166","pftitle":"厕所","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20200907/3f53117d8f67ebb4ca98b6132c1442e74144.jpg","pfaddress":"going","gotime":"2020-09-07","price":"100.00"}
         * RenewList : [{"pfptime":"2020-09-07 13:29","id":"832","pfptitle":"明年","pfpcontent":"哦弄","pfpurl":"http://www.tongbanapp.com/uploads/xingcheng/20200907/85c1ecfb2d1e7c908e6fa3a96a81a81f6380.jpg"},{"pfptime":"2020-09-07 13:31","id":"833","pfptitle":"厕所续写","pfpcontent":"","pfpurl":"http://www.tongbanapp.com/uploads/xingcheng/20200907/2f26892d67f31ff9a059a01207946e967022.jpg"}]
         */

        private FriendsListBean FriendsList;
        private List<RenewListBean> RenewList;

        public FriendsListBean getFriendsList() {
            return FriendsList;
        }

        public void setFriendsList(FriendsListBean FriendsList) {
            this.FriendsList = FriendsList;
        }

        public List<RenewListBean> getRenewList() {
            return RenewList;
        }

        public void setRenewList(List<RenewListBean> RenewList) {
            this.RenewList = RenewList;
        }

        public static class FriendsListBean {
            /**
             * pfID : 166
             * pftitle : 厕所
             * pfpic : http://www.tongbanapp.com/uploads/xingcheng/20200907/3f53117d8f67ebb4ca98b6132c1442e74144.jpg
             * pfaddress : going
             * gotime : 2020-09-07
             * price : 100.00
             */

            private String pfID;
            private String pftitle;
            private String pfpic;
            private String pfaddress;
            private String gotime;
            private String price;

            public String getPfID() {
                return pfID;
            }

            public void setPfID(String pfID) {
                this.pfID = pfID;
            }

            public String getPftitle() {
                return pftitle;
            }

            public void setPftitle(String pftitle) {
                this.pftitle = pftitle;
            }

            public String getPfpic() {
                return pfpic;
            }

            public void setPfpic(String pfpic) {
                this.pfpic = pfpic;
            }

            public String getPfaddress() {
                return pfaddress;
            }

            public void setPfaddress(String pfaddress) {
                this.pfaddress = pfaddress;
            }

            public String getGotime() {
                return gotime;
            }

            public void setGotime(String gotime) {
                this.gotime = gotime;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }

        public static class RenewListBean {
            /**
             * pfptime : 2020-09-07 13:29
             * id : 832
             * pfptitle : 明年
             * pfpcontent : 哦弄
             * pfpurl : http://www.tongbanapp.com/uploads/xingcheng/20200907/85c1ecfb2d1e7c908e6fa3a96a81a81f6380.jpg
             */

            private String pfptime;
            private String id;
            private String pfptitle;
            private String pfpcontent;
            private String pfpurl;

            public String getPfptime() {
                return pfptime;
            }

            public void setPfptime(String pfptime) {
                this.pfptime = pfptime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPfptitle() {
                return pfptitle;
            }

            public void setPfptitle(String pfptitle) {
                this.pfptitle = pfptitle;
            }

            public String getPfpcontent() {
                return pfpcontent;
            }

            public void setPfpcontent(String pfpcontent) {
                this.pfpcontent = pfpcontent;
            }

            public String getPfpurl() {
                return pfpurl;
            }

            public void setPfpurl(String pfpurl) {
                this.pfpurl = pfpurl;
            }
        }
    }
}
