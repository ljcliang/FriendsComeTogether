package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 */

public class EditorFriendRememberModel {

    /**
     * code : 200
     * message : 操作成功
     * obj : {"FriendsList":{"fmID":"2497","fmtitle":"momo","fmpic":"http://www.tongbanapp.com/uploads/article/20200422/0-1ee4e1ed1ccbe13665b8edf2f88ab0fd4792.png","fmgotime":"","fmendtime":"","percapitacost":"","fmlook":"5","fmfavorite":"0","price":"0","fmtime":"2020-04-22 15:33","fmpartyID":"0","pftitle":"无"},"RenewList":[{"ffID":"383","fftitle":"捂着2","fftime":"2020-04-22 16:10","ffcontect":"嘻嘻嘻嘻","picUrl":"http://www.tongbanapp.com/uploads/header/2020/04/22/f4711cc01c33b92260bc6ba2580689281587543020273.jpg"},{"ffID":"384","fftitle":"ban","fftime":"2020-04-22 16:31","ffcontect":"ooo","picUrl":"http://www.tongbanapp.com/uploads/header/2020/04/22/404df2773191f85d1685fa7a7594d18a1587544300786.jpg"}]}
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
         * FriendsList : {"fmID":"2497","fmtitle":"momo","fmpic":"http://www.tongbanapp.com/uploads/article/20200422/0-1ee4e1ed1ccbe13665b8edf2f88ab0fd4792.png","fmgotime":"","fmendtime":"","percapitacost":"","fmlook":"5","fmfavorite":"0","price":"0","fmtime":"2020-04-22 15:33","fmpartyID":"0","pftitle":"无"}
         * RenewList : [{"ffID":"383","fftitle":"捂着2","fftime":"2020-04-22 16:10","ffcontect":"嘻嘻嘻嘻","picUrl":"http://www.tongbanapp.com/uploads/header/2020/04/22/f4711cc01c33b92260bc6ba2580689281587543020273.jpg"},{"ffID":"384","fftitle":"ban","fftime":"2020-04-22 16:31","ffcontect":"ooo","picUrl":"http://www.tongbanapp.com/uploads/header/2020/04/22/404df2773191f85d1685fa7a7594d18a1587544300786.jpg"}]
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
             * fmID : 2497
             * fmtitle : momo
             * fmpic : http://www.tongbanapp.com/uploads/article/20200422/0-1ee4e1ed1ccbe13665b8edf2f88ab0fd4792.png
             * fmgotime :
             * fmendtime :
             * percapitacost :
             * fmlook : 5
             * fmfavorite : 0
             * price : 0
             * fmtime : 2020-04-22 15:33
             * fmpartyID : 0
             * pftitle : 无
             */

            private String fmID;
            private String fmtitle;
            private String fmpic;
            private String fmgotime;
            private String fmendtime;
            private String percapitacost;
            private String fmlook;
            private String fmfavorite;
            private String price;
            private String fmtime;
            private String fmpartyID;
            private String pftitle;

            public String getFmID() {
                return fmID;
            }

            public void setFmID(String fmID) {
                this.fmID = fmID;
            }

            public String getFmtitle() {
                return fmtitle;
            }

            public void setFmtitle(String fmtitle) {
                this.fmtitle = fmtitle;
            }

            public String getFmpic() {
                return fmpic;
            }

            public void setFmpic(String fmpic) {
                this.fmpic = fmpic;
            }

            public String getFmgotime() {
                return fmgotime;
            }

            public void setFmgotime(String fmgotime) {
                this.fmgotime = fmgotime;
            }

            public String getFmendtime() {
                return fmendtime;
            }

            public void setFmendtime(String fmendtime) {
                this.fmendtime = fmendtime;
            }

            public String getPercapitacost() {
                return percapitacost;
            }

            public void setPercapitacost(String percapitacost) {
                this.percapitacost = percapitacost;
            }

            public String getFmlook() {
                return fmlook;
            }

            public void setFmlook(String fmlook) {
                this.fmlook = fmlook;
            }

            public String getFmfavorite() {
                return fmfavorite;
            }

            public void setFmfavorite(String fmfavorite) {
                this.fmfavorite = fmfavorite;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getFmtime() {
                return fmtime;
            }

            public void setFmtime(String fmtime) {
                this.fmtime = fmtime;
            }

            public String getFmpartyID() {
                return fmpartyID;
            }

            public void setFmpartyID(String fmpartyID) {
                this.fmpartyID = fmpartyID;
            }

            public String getPftitle() {
                return pftitle;
            }

            public void setPftitle(String pftitle) {
                this.pftitle = pftitle;
            }
        }

        public static class RenewListBean {
            /**
             * ffID : 383
             * fftitle : 捂着2
             * fftime : 2020-04-22 16:10
             * ffcontect : 嘻嘻嘻嘻
             * picUrl : http://www.tongbanapp.com/uploads/header/2020/04/22/f4711cc01c33b92260bc6ba2580689281587543020273.jpg
             */

            private String ffID;
            private String fftitle;
            private String fftime;
            private String ffcontect;
            private String picUrl;

            public String getFfID() {
                return ffID;
            }

            public void setFfID(String ffID) {
                this.ffID = ffID;
            }

            public String getFftitle() {
                return fftitle;
            }

            public void setFftitle(String fftitle) {
                this.fftitle = fftitle;
            }

            public String getFftime() {
                return fftime;
            }

            public void setFftime(String fftime) {
                this.fftime = fftime;
            }

            public String getFfcontect() {
                return ffcontect;
            }

            public void setFfcontect(String ffcontect) {
                this.ffcontect = ffcontect;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }
    }
}
