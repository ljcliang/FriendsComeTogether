package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class DuiZhangZhuanShuModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","username":"花生","usergrade":"1","levelName":"0","shopName":"哈尔滨观光国际旅行社有限公司","pfList":[{"pfID":"79","pftitle":"品味芽庄8日游","phaseList":[{"phase_id":"525","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"84","pftitle":"欢乐趣味亲子游芽庄","phaseList":[{"phase_id":"524","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"85","pftitle":"玩转芽庄8日游","phaseList":[{"phase_id":"523","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"86","pftitle":"缅甸四飞8日游","phaseList":[{"phase_id":"522","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"87","pftitle":"曼芭普+斯米兰 曼记忆11日游(A团)","phaseList":[{"phase_id":"521","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"88","pftitle":"曼芭普+斯米兰 曼记忆11日游(B团)","phaseList":[{"phase_id":"520","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"89","pftitle":"曼芭普+斯米兰 曼记忆11日游(C团)","phaseList":[{"phase_id":"519","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"90","pftitle":"帝王普吉斯米兰岛境外5晚7日游","phaseList":[{"phase_id":"517","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"91","pftitle":"帝王普吉斯米兰岛境外4晚7日游","phaseList":[{"phase_id":"516","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"92","pftitle":"帝王普吉斯米兰岛境外5晚8日游","phaseList":[{"phase_id":"515","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"93","pftitle":"帝王普吉斯米兰岛境外6晚8日游","phaseList":[{"phase_id":"514","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"95","pftitle":"曼芭沙7日游(武汉转机)","phaseList":[{"phase_id":"512","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"96","pftitle":"曼芭沙7日游(深圳转机)","phaseList":[{"phase_id":"511","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"98","pftitle":"曼芭沙春秋8日游","phaseList":[{"phase_id":"510","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"99","pftitle":"芽庄一地8日游","phaseList":[{"phase_id":"509","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"101","pftitle":"印象巴厘岛厦航7日游","phaseList":[{"phase_id":"508","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"102","pftitle":"印象巴厘岛厦航8日游","phaseList":[{"phase_id":"506","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"103","pftitle":"印象巴厘岛南航7日游","phaseList":[{"phase_id":"505","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"104","pftitle":"印象巴厘岛厦航8日游","phaseList":[{"phase_id":"503","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"105","pftitle":"花漾巴厘岛厦航7日游","phaseList":[{"phase_id":"502","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"106","pftitle":"至尊巴厘岛南航7日游","phaseList":[{"phase_id":"501","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"107","pftitle":"蓝梦巴厘岛南航7日游","phaseList":[{"phase_id":"500","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]}],"ifover":"0","youJi":"0","video":"0","zhibo":"0","share":"0","jindu":"0"}
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
         * userpic : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
         * username : 花生
         * usergrade : 1
         * levelName : 0
         * shopName : 哈尔滨观光国际旅行社有限公司
         * pfList : [{"pfID":"79","pftitle":"品味芽庄8日游","phaseList":[{"phase_id":"525","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"84","pftitle":"欢乐趣味亲子游芽庄","phaseList":[{"phase_id":"524","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"85","pftitle":"玩转芽庄8日游","phaseList":[{"phase_id":"523","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"86","pftitle":"缅甸四飞8日游","phaseList":[{"phase_id":"522","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"87","pftitle":"曼芭普+斯米兰 曼记忆11日游(A团)","phaseList":[{"phase_id":"521","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"88","pftitle":"曼芭普+斯米兰 曼记忆11日游(B团)","phaseList":[{"phase_id":"520","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"89","pftitle":"曼芭普+斯米兰 曼记忆11日游(C团)","phaseList":[{"phase_id":"519","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"90","pftitle":"帝王普吉斯米兰岛境外5晚7日游","phaseList":[{"phase_id":"517","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"91","pftitle":"帝王普吉斯米兰岛境外4晚7日游","phaseList":[{"phase_id":"516","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"92","pftitle":"帝王普吉斯米兰岛境外5晚8日游","phaseList":[{"phase_id":"515","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"93","pftitle":"帝王普吉斯米兰岛境外6晚8日游","phaseList":[{"phase_id":"514","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"95","pftitle":"曼芭沙7日游(武汉转机)","phaseList":[{"phase_id":"512","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"96","pftitle":"曼芭沙7日游(深圳转机)","phaseList":[{"phase_id":"511","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"98","pftitle":"曼芭沙春秋8日游","phaseList":[{"phase_id":"510","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"99","pftitle":"芽庄一地8日游","phaseList":[{"phase_id":"509","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"101","pftitle":"印象巴厘岛厦航7日游","phaseList":[{"phase_id":"508","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"102","pftitle":"印象巴厘岛厦航8日游","phaseList":[{"phase_id":"506","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"103","pftitle":"印象巴厘岛南航7日游","phaseList":[{"phase_id":"505","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"104","pftitle":"印象巴厘岛厦航8日游","phaseList":[{"phase_id":"503","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"105","pftitle":"花漾巴厘岛厦航7日游","phaseList":[{"phase_id":"502","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"106","pftitle":"至尊巴厘岛南航7日游","phaseList":[{"phase_id":"501","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]},{"pfID":"107","pftitle":"蓝梦巴厘岛南航7日游","phaseList":[{"phase_id":"500","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]}]
         * ifover : 0
         * youJi : 0
         * video : 0
         * zhibo : 0
         * share : 0
         * jindu : 0
         */

        private String userpic;
        private String username;
        private String usergrade;
        private String levelName;
        private String shopName;
        private String ifover;
        private String youJi;
        private String video;
        private String zhibo;
        private String share;
        private String jindu;
        private List<PfListBean> pfList;

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

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getIfover() {
            return ifover;
        }

        public void setIfover(String ifover) {
            this.ifover = ifover;
        }

        public String getYouJi() {
            return youJi;
        }

        public void setYouJi(String youJi) {
            this.youJi = youJi;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getZhibo() {
            return zhibo;
        }

        public void setZhibo(String zhibo) {
            this.zhibo = zhibo;
        }

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }

        public String getJindu() {
            return jindu;
        }

        public void setJindu(String jindu) {
            this.jindu = jindu;
        }

        public List<PfListBean> getPfList() {
            return pfList;
        }

        public void setPfList(List<PfListBean> pfList) {
            this.pfList = pfList;
        }

        public static class PfListBean {
            /**
             * pfID : 79
             * pftitle : 品味芽庄8日游
             * phaseList : [{"phase_id":"525","phase_begin_time":"2020-12-31","phase_over_time":"2020-12-31"}]
             */

            private String pfID;
            private String pftitle;
            private List<PhaseListBean> phaseList;

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

            public List<PhaseListBean> getPhaseList() {
                return phaseList;
            }

            public void setPhaseList(List<PhaseListBean> phaseList) {
                this.phaseList = phaseList;
            }

            public static class PhaseListBean {
                /**
                 * phase_id : 525
                 * phase_begin_time : 2020-12-31
                 * phase_over_time : 2020-12-31
                 */

                private String phase_id;
                private String phase_begin_time;
                private String phase_over_time;

                public String getPhase_id() {
                    return phase_id;
                }

                public void setPhase_id(String phase_id) {
                    this.phase_id = phase_id;
                }

                public String getPhase_begin_time() {
                    return phase_begin_time;
                }

                public void setPhase_begin_time(String phase_begin_time) {
                    this.phase_begin_time = phase_begin_time;
                }

                public String getPhase_over_time() {
                    return phase_over_time;
                }

                public void setPhase_over_time(String phase_over_time) {
                    this.phase_over_time = phase_over_time;
                }
            }
        }
    }
}
