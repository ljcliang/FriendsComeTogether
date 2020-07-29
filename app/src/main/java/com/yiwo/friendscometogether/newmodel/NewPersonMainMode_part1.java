package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2019/6/25.
 */

public class NewPersonMainMode_part1 {


    /**
     * code : 200
     * message : 获取成功!
     * obj : {"info":{"wy_accid":"tongban15754633415","age":"25岁","address":"黑龙江省-哈尔滨市","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","username":"花生","autograph":"嗷呜～","sex":"0","userlike":"20","GiveCount":1,"friends":"0","follow":"0","fans":"5","usermarry":"1","usergrade":"1","usercodeok":"1","otherUserId":"4","if_kefu":"0","captain":"1"},"mytag":{"personality":"局气,靠谱","motion":"篮球,台球","Music":"","Delicious":"","Film":"","book":"","Travel":""},"usertag":{"personality":"局气,靠谱","motion":"篮球,台球","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":{"personality":"局气,靠谱,","motion":"篮球,台球,","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":""}},"otherUserId":"4","if_kefu":"0","goods":[{"gid":"24","goodsName":"红包","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/c254efcc66bb814680e04ea4231bc6e65654.jpg","price":"1.00","preferential":"0.01"},{"gid":"20","goodsName":"苹果12345","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/43fb832d2364918fc5fcca3d6b61c8a31099.jpg","price":"20.00","preferential":"10.00"},{"gid":"19","goodsName":"测试编辑商品修改1","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/6c9a7bdae077db74f5e367a6849c733c4630.jpg","price":"100.00","preferential":"99.00"},{"gid":"18","goodsName":"康师傅老坛酸菜面","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200427/9b2ab2939cb5c8a4eeff093ba5dee8f05059.jpeg","price":"3.50","preferential":"3.00"},{"gid":"15","goodsName":"炖猫","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200417/a07dfacdc84109f9fd9f91aa4371e95c9161.jpg","price":"6666.00","preferential":"123.00"},{"gid":"14","goodsName":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200417/87078c4fb1765cb00aabf243e25787298614.jpg","price":"3.00","preferential":"1.00"},{"gid":"4","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","price":"0.02","preferential":"0.01"},{"gid":"3","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","price":"0.02","preferential":"0.01"},{"gid":"2","goodsName":"西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","price":"0.10","preferential":"0.01"},{"gid":"1","goodsName":"大西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","price":"0.10","preferential":"0.01"}]}
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
         * info : {"wy_accid":"tongban15754633415","age":"25岁","address":"黑龙江省-哈尔滨市","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","username":"花生","autograph":"嗷呜～","sex":"0","userlike":"20","GiveCount":1,"friends":"0","follow":"0","fans":"5","usermarry":"1","usergrade":"1","usercodeok":"1","otherUserId":"4","if_kefu":"0","captain":"1"}
         * mytag : {"personality":"局气,靠谱","motion":"篮球,台球","Music":"","Delicious":"","Film":"","book":"","Travel":""}
         * usertag : {"personality":"局气,靠谱","motion":"篮球,台球","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":{"personality":"局气,靠谱,","motion":"篮球,台球,","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":""}}
         * otherUserId : 4
         * if_kefu : 0
         * goods : [{"gid":"24","goodsName":"红包","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/c254efcc66bb814680e04ea4231bc6e65654.jpg","price":"1.00","preferential":"0.01"},{"gid":"20","goodsName":"苹果12345","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/43fb832d2364918fc5fcca3d6b61c8a31099.jpg","price":"20.00","preferential":"10.00"},{"gid":"19","goodsName":"测试编辑商品修改1","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/6c9a7bdae077db74f5e367a6849c733c4630.jpg","price":"100.00","preferential":"99.00"},{"gid":"18","goodsName":"康师傅老坛酸菜面","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200427/9b2ab2939cb5c8a4eeff093ba5dee8f05059.jpeg","price":"3.50","preferential":"3.00"},{"gid":"15","goodsName":"炖猫","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200417/a07dfacdc84109f9fd9f91aa4371e95c9161.jpg","price":"6666.00","preferential":"123.00"},{"gid":"14","goodsName":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200417/87078c4fb1765cb00aabf243e25787298614.jpg","price":"3.00","preferential":"1.00"},{"gid":"4","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","price":"0.02","preferential":"0.01"},{"gid":"3","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","price":"0.02","preferential":"0.01"},{"gid":"2","goodsName":"西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","price":"0.10","preferential":"0.01"},{"gid":"1","goodsName":"大西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","price":"0.10","preferential":"0.01"}]
         */

        private InfoBean info;
        private MytagBean mytag;
        private UsertagBean usertag;
        private String otherUserId;
        private String if_kefu;
        private List<GoodsBean> goods;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public MytagBean getMytag() {
            return mytag;
        }

        public void setMytag(MytagBean mytag) {
            this.mytag = mytag;
        }

        public UsertagBean getUsertag() {
            return usertag;
        }

        public void setUsertag(UsertagBean usertag) {
            this.usertag = usertag;
        }

        public String getOtherUserId() {
            return otherUserId;
        }

        public void setOtherUserId(String otherUserId) {
            this.otherUserId = otherUserId;
        }

        public String getIf_kefu() {
            return if_kefu;
        }

        public void setIf_kefu(String if_kefu) {
            this.if_kefu = if_kefu;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class InfoBean {
            /**
             * wy_accid : tongban15754633415
             * age : 25岁
             * address : 黑龙江省-哈尔滨市
             * userpic : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
             * username : 花生
             * autograph : 嗷呜～
             * sex : 0
             * userlike : 20
             * GiveCount : 1
             * friends : 0
             * follow : 0
             * fans : 5
             * usermarry : 1
             * usergrade : 1
             * usercodeok : 1
             * otherUserId : 4
             * if_kefu : 0
             * captain : 1
             */

            private String wy_accid;
            private String age;
            private String address;
            private String userpic;
            private String username;
            private String autograph;
            private String sex;
            private String userlike;
            private int GiveCount;
            private String friends;
            private String follow;
            private String fans;
            private String usermarry;
            private String usergrade;
            private String usercodeok;
            private String otherUserId;
            private String if_kefu;
            private String captain;

            public String getWy_accid() {
                return wy_accid;
            }

            public void setWy_accid(String wy_accid) {
                this.wy_accid = wy_accid;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getUserlike() {
                return userlike;
            }

            public void setUserlike(String userlike) {
                this.userlike = userlike;
            }

            public int getGiveCount() {
                return GiveCount;
            }

            public void setGiveCount(int GiveCount) {
                this.GiveCount = GiveCount;
            }

            public String getFriends() {
                return friends;
            }

            public void setFriends(String friends) {
                this.friends = friends;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public String getUsermarry() {
                return usermarry;
            }

            public void setUsermarry(String usermarry) {
                this.usermarry = usermarry;
            }

            public String getUsergrade() {
                return usergrade;
            }

            public void setUsergrade(String usergrade) {
                this.usergrade = usergrade;
            }

            public String getUsercodeok() {
                return usercodeok;
            }

            public void setUsercodeok(String usercodeok) {
                this.usercodeok = usercodeok;
            }

            public String getOtherUserId() {
                return otherUserId;
            }

            public void setOtherUserId(String otherUserId) {
                this.otherUserId = otherUserId;
            }

            public String getIf_kefu() {
                return if_kefu;
            }

            public void setIf_kefu(String if_kefu) {
                this.if_kefu = if_kefu;
            }

            public String getCaptain() {
                return captain;
            }

            public void setCaptain(String captain) {
                this.captain = captain;
            }
        }

        public static class MytagBean {
            /**
             * personality : 局气,靠谱
             * motion : 篮球,台球
             * Music :
             * Delicious :
             * Film :
             * book :
             * Travel :
             */

            private String personality;
            private String motion;
            private String Music;
            private String Delicious;
            private String Film;
            private String book;
            private String Travel;

            public String getPersonality() {
                return personality;
            }

            public void setPersonality(String personality) {
                this.personality = personality;
            }

            public String getMotion() {
                return motion;
            }

            public void setMotion(String motion) {
                this.motion = motion;
            }

            public String getMusic() {
                return Music;
            }

            public void setMusic(String Music) {
                this.Music = Music;
            }

            public String getDelicious() {
                return Delicious;
            }

            public void setDelicious(String Delicious) {
                this.Delicious = Delicious;
            }

            public String getFilm() {
                return Film;
            }

            public void setFilm(String Film) {
                this.Film = Film;
            }

            public String getBook() {
                return book;
            }

            public void setBook(String book) {
                this.book = book;
            }

            public String getTravel() {
                return Travel;
            }

            public void setTravel(String Travel) {
                this.Travel = Travel;
            }
        }

        public static class UsertagBean implements Serializable {
            /**
             * personality : 局气,靠谱
             * motion : 篮球,台球
             * Music :
             * Delicious :
             * Film :
             * book :
             * Travel :
             * Same : {"personality":"局气,靠谱,","motion":"篮球,台球,","Music":"","Delicious":"","Film":"","book":"","Travel":"","Same":""}
             */

            private String personality;
            private String motion;
            private String Music;
            private String Delicious;
            private String Film;
            private String book;
            private String Travel;
            private SameBean Same;

            public String getPersonality() {
                return personality;
            }

            public void setPersonality(String personality) {
                this.personality = personality;
            }

            public String getMotion() {
                return motion;
            }

            public void setMotion(String motion) {
                this.motion = motion;
            }

            public String getMusic() {
                return Music;
            }

            public void setMusic(String Music) {
                this.Music = Music;
            }

            public String getDelicious() {
                return Delicious;
            }

            public void setDelicious(String Delicious) {
                this.Delicious = Delicious;
            }

            public String getFilm() {
                return Film;
            }

            public void setFilm(String Film) {
                this.Film = Film;
            }

            public String getBook() {
                return book;
            }

            public void setBook(String book) {
                this.book = book;
            }

            public String getTravel() {
                return Travel;
            }

            public void setTravel(String Travel) {
                this.Travel = Travel;
            }

            public SameBean getSame() {
                return Same;
            }

            public void setSame(SameBean Same) {
                this.Same = Same;
            }

            public static class SameBean implements Serializable{
                /**
                 * personality : 局气,靠谱,
                 * motion : 篮球,台球,
                 * Music :
                 * Delicious :
                 * Film :
                 * book :
                 * Travel :
                 * Same :
                 */

                private String personality;
                private String motion;
                private String Music;
                private String Delicious;
                private String Film;
                private String book;
                private String Travel;
                private String Same;

                public String getPersonality() {
                    return personality;
                }

                public void setPersonality(String personality) {
                    this.personality = personality;
                }

                public String getMotion() {
                    return motion;
                }

                public void setMotion(String motion) {
                    this.motion = motion;
                }

                public String getMusic() {
                    return Music;
                }

                public void setMusic(String Music) {
                    this.Music = Music;
                }

                public String getDelicious() {
                    return Delicious;
                }

                public void setDelicious(String Delicious) {
                    this.Delicious = Delicious;
                }

                public String getFilm() {
                    return Film;
                }

                public void setFilm(String Film) {
                    this.Film = Film;
                }

                public String getBook() {
                    return book;
                }

                public void setBook(String book) {
                    this.book = book;
                }

                public String getTravel() {
                    return Travel;
                }

                public void setTravel(String Travel) {
                    this.Travel = Travel;
                }

                public String getSame() {
                    return Same;
                }

                public void setSame(String Same) {
                    this.Same = Same;
                }
            }
        }

        public static class GoodsBean {
            /**
             * gid : 24
             * goodsName : 红包
             * goodsImg : http://www.tongbanapp.com/uploads/goods/20200428/c254efcc66bb814680e04ea4231bc6e65654.jpg
             * price : 1.00
             * preferential : 0.01
             */

            private String gid;
            private String goodsName;
            private String goodsImg;
            private String price;
            private String preferential;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPreferential() {
                return preferential;
            }

            public void setPreferential(String preferential) {
                this.preferential = preferential;
            }
        }
    }
}
