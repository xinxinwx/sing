package com.example.resume.mvp.mode;

import java.util.List;

public class SingListMode {

    /**
     * code : 200
     * data : {"current":1,"hitCount":false,"orders":[],"pages":2,"records":[{"cid":1,"cockdate":"2019-05-18","morninglc":"北京市","morningtime":"2019-5-18 09:02:03","night":"2019-05-18 19:23:25","nightlc":"邯郸市","type":2,"uid":4},{"cid":31,"cockdate":"2019-05-19","morninglc":"北京市","morningtime":"2019-5-18 09:02:03","night":"2019-5-18 18:03:02","nightlc":"天津市","type":2,"uid":4},{"cid":32,"cockdate":"2019-05-20","morninglc":"2020-05-20 08:03","morningtime":"2020-05-20 08:03","night":"","nightlc":"","type":1,"uid":4},{"cid":33,"cockdate":"2019-05-21","morninglc":"你猜","morningtime":"","night":"2020-05-21","nightlc":"哈哈","type":2,"uid":4},{"cid":34,"cockdate":"2019-05-30","morninglc":"你猜","morningtime":"2019-05-30 08:23:15","night":"2019-05-30 19:25:30","nightlc":"神奇","type":2,"uid":4},{"cid":35,"cockdate":"2019-05-31","morninglc":"厉害","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"厉害","type":2,"uid":4},{"cid":36,"cockdate":"2019-05-01","morninglc":"厉害","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"厉害","type":2,"uid":4},{"cid":37,"cockdate":"2019-05-02","morninglc":"神奇","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"神奇","type":2,"uid":4},{"cid":38,"cockdate":"2019-05-03","morninglc":"优秀","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"优秀","type":2,"uid":4},{"cid":39,"cockdate":"2019-05-04","morninglc":"优都是","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"优都是","type":2,"uid":4}],"searchCount":true,"size":10,"total":13}
     * message : 获取列表成功
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
         * current : 1
         * hitCount : false
         * orders : []
         * pages : 2
         * records : [{"cid":1,"cockdate":"2019-05-18","morninglc":"北京市","morningtime":"2019-5-18 09:02:03","night":"2019-05-18 19:23:25","nightlc":"邯郸市","type":2,"uid":4},{"cid":31,"cockdate":"2019-05-19","morninglc":"北京市","morningtime":"2019-5-18 09:02:03","night":"2019-5-18 18:03:02","nightlc":"天津市","type":2,"uid":4},{"cid":32,"cockdate":"2019-05-20","morninglc":"2020-05-20 08:03","morningtime":"2020-05-20 08:03","night":"","nightlc":"","type":1,"uid":4},{"cid":33,"cockdate":"2019-05-21","morninglc":"你猜","morningtime":"","night":"2020-05-21","nightlc":"哈哈","type":2,"uid":4},{"cid":34,"cockdate":"2019-05-30","morninglc":"你猜","morningtime":"2019-05-30 08:23:15","night":"2019-05-30 19:25:30","nightlc":"神奇","type":2,"uid":4},{"cid":35,"cockdate":"2019-05-31","morninglc":"厉害","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"厉害","type":2,"uid":4},{"cid":36,"cockdate":"2019-05-01","morninglc":"厉害","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"厉害","type":2,"uid":4},{"cid":37,"cockdate":"2019-05-02","morninglc":"神奇","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"神奇","type":2,"uid":4},{"cid":38,"cockdate":"2019-05-03","morninglc":"优秀","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"优秀","type":2,"uid":4},{"cid":39,"cockdate":"2019-05-04","morninglc":"优都是","morningtime":"2019-05-31 19:25:30","night":"2019-05-31 19:25:30","nightlc":"优都是","type":2,"uid":4}]
         * searchCount : true
         * size : 10
         * total : 13
         */

        private int current;
        private boolean hitCount;
        private int pages;
        private boolean searchCount;
        private int size;
        private int total;
        private List<?> orders;
        private List<RecordsBean> records;

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isHitCount() {
            return hitCount;
        }

        public void setHitCount(boolean hitCount) {
            this.hitCount = hitCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * cid : 1
             * cockdate : 2019-05-18
             * morninglc : 北京市
             * morningtime : 2019-5-18 09:02:03
             * night : 2019-05-18 19:23:25
             * nightlc : 邯郸市
             * type : 2
             * uid : 4
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
}
