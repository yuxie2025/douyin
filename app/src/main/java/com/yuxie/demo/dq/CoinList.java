package com.yuxie.demo.dq;

import java.util.List;

public class CoinList {


    /**
     * code : 200
     * msg : 48.06003
     * data : [{"id":"7072783","isNewRecord":false,"remarks":"","createDate":"","count":"6.66666","type":"7","user":{"id":"44042e79e38348919b8da4a6ac1aa252","isNewRecord":false,"remarks":"","createDate":"","autoId":"","name":"用户227617","photo":"","sign":"","phone":"","token":"","tokenTime":0,"state":"","guanzhushu":"","fensishu":"","yaoqingma":"","isGuanzhu":"","leftGood":"","leftFenxiang":"","fenhongzs":"","fenhongje":"","fenhongzongshu":"","bchanshouyi":0,"isDongjie":"","firstUserId":"","secondUserId":"","isJihuo":"","zhifubao":"","ifwechat":"","openid":"","country":"","gender":"","city":"","province":"","countVideo":"","size":"","type":"","password":"","ifpassword":"","tixianzongshu":"","daozhangzongshu":"","qiandaozs":"","prizecount":"","choujiang":"","jiangli":""},"createTime":"2018-11-11 12:00:00","showTime":"2018-11-11 12:00:00","endTime":"2018-11-12 12:00:00","isShouqu":"0","videoId":"","start":""}]
     * data2 : []
     */

    private int code;
    private String msg;
    private List<DataBean> data;
    private List<?> data2;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<?> getData2() {
        return data2;
    }

    public void setData2(List<?> data2) {
        this.data2 = data2;
    }

    public static class DataBean {
        /**
         * id : 7072783
         * isNewRecord : false
         * remarks :
         * createDate :
         * count : 6.66666
         * type : 7
         * user : {"id":"44042e79e38348919b8da4a6ac1aa252","isNewRecord":false,"remarks":"","createDate":"","autoId":"","name":"用户227617","photo":"","sign":"","phone":"","token":"","tokenTime":0,"state":"","guanzhushu":"","fensishu":"","yaoqingma":"","isGuanzhu":"","leftGood":"","leftFenxiang":"","fenhongzs":"","fenhongje":"","fenhongzongshu":"","bchanshouyi":0,"isDongjie":"","firstUserId":"","secondUserId":"","isJihuo":"","zhifubao":"","ifwechat":"","openid":"","country":"","gender":"","city":"","province":"","countVideo":"","size":"","type":"","password":"","ifpassword":"","tixianzongshu":"","daozhangzongshu":"","qiandaozs":"","prizecount":"","choujiang":"","jiangli":""}
         * createTime : 2018-11-11 12:00:00
         * showTime : 2018-11-11 12:00:00
         * endTime : 2018-11-12 12:00:00
         * isShouqu : 0
         * videoId :
         * start :
         */

        private String id;
        private boolean isNewRecord;
        private String remarks;
        private String createDate;
        private String count;
        private String type;
        private UserBean user;
        private String createTime;
        private String showTime;
        private String endTime;
        private String isShouqu;
        private String videoId;
        private String start;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getIsShouqu() {
            return isShouqu;
        }

        public void setIsShouqu(String isShouqu) {
            this.isShouqu = isShouqu;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public static class UserBean {
            /**
             * id : 44042e79e38348919b8da4a6ac1aa252
             * isNewRecord : false
             * remarks :
             * createDate :
             * autoId :
             * name : 用户227617
             * photo :
             * sign :
             * phone :
             * token :
             * tokenTime : 0
             * state :
             * guanzhushu :
             * fensishu :
             * yaoqingma :
             * isGuanzhu :
             * leftGood :
             * leftFenxiang :
             * fenhongzs :
             * fenhongje :
             * fenhongzongshu :
             * bchanshouyi : 0
             * isDongjie :
             * firstUserId :
             * secondUserId :
             * isJihuo :
             * zhifubao :
             * ifwechat :
             * openid :
             * country :
             * gender :
             * city :
             * province :
             * countVideo :
             * size :
             * type :
             * password :
             * ifpassword :
             * tixianzongshu :
             * daozhangzongshu :
             * qiandaozs :
             * prizecount :
             * choujiang :
             * jiangli :
             */

            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String autoId;
            private String name;
            private String photo;
            private String sign;
            private String phone;
            private String token;
            private String tokenTime;
            private String state;
            private String guanzhushu;
            private String fensishu;
            private String yaoqingma;
            private String isGuanzhu;
            private String leftGood;
            private String leftFenxiang;
            private String fenhongzs;
            private String fenhongje;
            private String fenhongzongshu;
            private int bchanshouyi;
            private String isDongjie;
            private String firstUserId;
            private String secondUserId;
            private String isJihuo;
            private String zhifubao;
            private String ifwechat;
            private String openid;
            private String country;
            private String gender;
            private String city;
            private String province;
            private String countVideo;
            private String size;
            private String type;
            private String password;
            private String ifpassword;
            private String tixianzongshu;
            private String daozhangzongshu;
            private String qiandaozs;
            private String prizecount;
            private String choujiang;
            private String jiangli;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getAutoId() {
                return autoId;
            }

            public void setAutoId(String autoId) {
                this.autoId = autoId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getTokenTime() {
                return tokenTime;
            }

            public void setTokenTime(String tokenTime) {
                this.tokenTime = tokenTime;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getGuanzhushu() {
                return guanzhushu;
            }

            public void setGuanzhushu(String guanzhushu) {
                this.guanzhushu = guanzhushu;
            }

            public String getFensishu() {
                return fensishu;
            }

            public void setFensishu(String fensishu) {
                this.fensishu = fensishu;
            }

            public String getYaoqingma() {
                return yaoqingma;
            }

            public void setYaoqingma(String yaoqingma) {
                this.yaoqingma = yaoqingma;
            }

            public String getIsGuanzhu() {
                return isGuanzhu;
            }

            public void setIsGuanzhu(String isGuanzhu) {
                this.isGuanzhu = isGuanzhu;
            }

            public String getLeftGood() {
                return leftGood;
            }

            public void setLeftGood(String leftGood) {
                this.leftGood = leftGood;
            }

            public String getLeftFenxiang() {
                return leftFenxiang;
            }

            public void setLeftFenxiang(String leftFenxiang) {
                this.leftFenxiang = leftFenxiang;
            }

            public String getFenhongzs() {
                return fenhongzs;
            }

            public void setFenhongzs(String fenhongzs) {
                this.fenhongzs = fenhongzs;
            }

            public String getFenhongje() {
                return fenhongje;
            }

            public void setFenhongje(String fenhongje) {
                this.fenhongje = fenhongje;
            }

            public String getFenhongzongshu() {
                return fenhongzongshu;
            }

            public void setFenhongzongshu(String fenhongzongshu) {
                this.fenhongzongshu = fenhongzongshu;
            }

            public int getBchanshouyi() {
                return bchanshouyi;
            }

            public void setBchanshouyi(int bchanshouyi) {
                this.bchanshouyi = bchanshouyi;
            }

            public String getIsDongjie() {
                return isDongjie;
            }

            public void setIsDongjie(String isDongjie) {
                this.isDongjie = isDongjie;
            }

            public String getFirstUserId() {
                return firstUserId;
            }

            public void setFirstUserId(String firstUserId) {
                this.firstUserId = firstUserId;
            }

            public String getSecondUserId() {
                return secondUserId;
            }

            public void setSecondUserId(String secondUserId) {
                this.secondUserId = secondUserId;
            }

            public String getIsJihuo() {
                return isJihuo;
            }

            public void setIsJihuo(String isJihuo) {
                this.isJihuo = isJihuo;
            }

            public String getZhifubao() {
                return zhifubao;
            }

            public void setZhifubao(String zhifubao) {
                this.zhifubao = zhifubao;
            }

            public String getIfwechat() {
                return ifwechat;
            }

            public void setIfwechat(String ifwechat) {
                this.ifwechat = ifwechat;
            }

            public String getOpenid() {
                return openid;
            }

            public void setOpenid(String openid) {
                this.openid = openid;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCountVideo() {
                return countVideo;
            }

            public void setCountVideo(String countVideo) {
                this.countVideo = countVideo;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getIfpassword() {
                return ifpassword;
            }

            public void setIfpassword(String ifpassword) {
                this.ifpassword = ifpassword;
            }

            public String getTixianzongshu() {
                return tixianzongshu;
            }

            public void setTixianzongshu(String tixianzongshu) {
                this.tixianzongshu = tixianzongshu;
            }

            public String getDaozhangzongshu() {
                return daozhangzongshu;
            }

            public void setDaozhangzongshu(String daozhangzongshu) {
                this.daozhangzongshu = daozhangzongshu;
            }

            public String getQiandaozs() {
                return qiandaozs;
            }

            public void setQiandaozs(String qiandaozs) {
                this.qiandaozs = qiandaozs;
            }

            public String getPrizecount() {
                return prizecount;
            }

            public void setPrizecount(String prizecount) {
                this.prizecount = prizecount;
            }

            public String getChoujiang() {
                return choujiang;
            }

            public void setChoujiang(String choujiang) {
                this.choujiang = choujiang;
            }

            public String getJiangli() {
                return jiangli;
            }

            public void setJiangli(String jiangli) {
                this.jiangli = jiangli;
            }
        }
    }
}
