package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class LocalWebInfoModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"str":"uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg$$$@@@uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|&|&|118|&|&|Jin|&|&|LV0|&|&| |&|&|2019-04-16&nbsp;0阅读|&|&|+关注$$$@@@哈尔滨|&|&|\u201c今天的开心源是自己。\u201d$$$@@@uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg$$$@@@1970-01-01~1970-01-01|&|&|$$$@@@$$$@@@$$$@@@$$$@@@uploads/article/20190423/0-477bb905f72b5d485631c0cead0439fa9644.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|要成为一个不用听话就有小红花的小朋友|@|@|0|@|@|0|@|@|no|@|@|1588|&|&|uploads/article/20190423/0-f0c107790b927e89e9e04061e4fe5f653072.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|一闪一闪小星星 简简单单一辈子|@|@|0|@|@|0|@|@|no|@|@|1557|&|&|uploads/article/20190422/0-aba85ce91d5425a05b7bb8bf2df569741668.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|充实的一天 便当蛋糕超好吃|@|@|0|@|@|0|@|@|no|@|@|1471|&|&|uploads/article/20190422/0-f20b16858ede17fa32f80bcc89c12e554531.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|\u201c对长得好看的人具有天然的信任感。\u201d \u200b\u200b\u200b|@|@|2|@|@|0|@|@|no|@|@|1359","picArr":["http://www.tongbanapp.com/uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg","http://www.tongbanapp.com/uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg","http://www.tongbanapp.com/uploads/article/20190423/0-477bb905f72b5d485631c0cead0439fa9644.fmpic0","http://www.tongbanapp.com/uploads/article/20190423/0-f0c107790b927e89e9e04061e4fe5f653072.fmpic0","http://www.tongbanapp.com/uploads/article/20190422/0-aba85ce91d5425a05b7bb8bf2df569741668.fmpic0","http://www.tongbanapp.com/uploads/article/20190422/0-f20b16858ede17fa32f80bcc89c12e554531.fmpic0"]}
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
         * str : uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg$$$@@@uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|&|&|118|&|&|Jin|&|&|LV0|&|&| |&|&|2019-04-16&nbsp;0阅读|&|&|+关注$$$@@@哈尔滨|&|&|“今天的开心源是自己。”$$$@@@uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg$$$@@@1970-01-01~1970-01-01|&|&|$$$@@@$$$@@@$$$@@@$$$@@@uploads/article/20190423/0-477bb905f72b5d485631c0cead0439fa9644.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|要成为一个不用听话就有小红花的小朋友|@|@|0|@|@|0|@|@|no|@|@|1588|&|&|uploads/article/20190423/0-f0c107790b927e89e9e04061e4fe5f653072.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|一闪一闪小星星 简简单单一辈子|@|@|0|@|@|0|@|@|no|@|@|1557|&|&|uploads/article/20190422/0-aba85ce91d5425a05b7bb8bf2df569741668.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|充实的一天 便当蛋糕超好吃|@|@|0|@|@|0|@|@|no|@|@|1471|&|&|uploads/article/20190422/0-f20b16858ede17fa32f80bcc89c12e554531.fmpic0|@|@|uploads/header/2019/04/15/7cb5f251175203beaa4e031084fc8d56155531616014.png|@|@|118|@|@|Jin|@|@|Lv.0|@|@||@|@|哈尔滨|@|@|“对长得好看的人具有天然的信任感。” ​​​|@|@|2|@|@|0|@|@|no|@|@|1359
         * picArr : ["http://www.tongbanapp.com/uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg","http://www.tongbanapp.com/uploads/article/20190416/0-595163d333eefb926e0561face0778026001.jpg","http://www.tongbanapp.com/uploads/article/20190423/0-477bb905f72b5d485631c0cead0439fa9644.fmpic0","http://www.tongbanapp.com/uploads/article/20190423/0-f0c107790b927e89e9e04061e4fe5f653072.fmpic0","http://www.tongbanapp.com/uploads/article/20190422/0-aba85ce91d5425a05b7bb8bf2df569741668.fmpic0","http://www.tongbanapp.com/uploads/article/20190422/0-f20b16858ede17fa32f80bcc89c12e554531.fmpic0"]
         */

        private String str;
        private List<String> picArr;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public List<String> getPicArr() {
            return picArr;
        }

        public void setPicArr(List<String> picArr) {
            this.picArr = picArr;
        }
    }
}
