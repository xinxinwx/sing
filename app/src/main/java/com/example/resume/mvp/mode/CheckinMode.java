package com.example.resume.mvp.mode;

public class CheckinMode {


    /**
     * code : 200
     * data : {"cid":0,"cockdate":null,"morninglc":"","morningtime":"","night":"","nightlc":"","type":0,"uid":0}
     * message : 您今天已经打过卡了
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
         * cid : 0
         * cockdate : null
         * morninglc :
         * morningtime :
         * night :
         * nightlc :
         * type : 0
         * uid : 0
         */

        private int cid;
        private Object cockdate;
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

        public Object getCockdate() {
            return cockdate;
        }

        public void setCockdate(Object cockdate) {
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
