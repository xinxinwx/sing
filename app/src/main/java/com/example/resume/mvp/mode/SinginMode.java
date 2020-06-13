package com.example.resume.mvp.mode;

public class SinginMode {

    /**
     * code : 200
     * data : {"cid":31,"cockdate":"2019-05-19","morninglc":"北京市","morningtime":"2019-5-18 09:02:03","night":"2019-5-18 18:03:02","nightlc":"天津市","type":2,"uid":0}
     * message : 获取成功
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * cid : 31
         * cockdate : 2019-05-19
         * morninglc : 北京市
         * morningtime : 2019-5-18 09:02:03
         * night : 2019-5-18 18:03:02
         * nightlc : 天津市
         * type : 2
         * uid : 0
         */

        private int cid;
        private String cockdate;
        private String morninglc;
        private String morningtime;
        private String night;
        private String nightlc;
        private int type;
        private int uid;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getCockdate() {
            return cockdate;
        }

        public void setCockdate(String cockdate) {
            this.cockdate = cockdate;
        }

        public String getMorninglc() {
            return morninglc;
        }

        public void setMorninglc(String morninglc) {
            this.morninglc = morninglc;
        }

        public String getMorningtime() {
            return morningtime;
        }

        public void setMorningtime(String morningtime) {
            this.morningtime = morningtime;
        }

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        public String getNightlc() {
            return nightlc;
        }

        public void setNightlc(String nightlc) {
            this.nightlc = nightlc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
