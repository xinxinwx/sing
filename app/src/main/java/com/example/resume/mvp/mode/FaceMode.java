package com.example.resume.mvp.mode;

import java.util.List;

public class FaceMode {
    /**
     * error_code : 0
     * error_msg : SUCCESS
     * log_id : 240483475
     * timestamp : 1535533440
     * cached : 0
     * result : {"face_num":2,"face_list":[{"face_token":"6fe19a6ee0c4233db9b5bba4dc2b9233","location":{"left":31.95568085,"top":120.3764267,"width":87,"height":85,"rotation":-5},"user_list":[{"group_id":"group1","user_id":"5abd24fd062e49bfa906b257ec40d284","user_info":"userinfo1","score":69.85684967041},{"group_id":"group1","user_id":"2abf89cffb31473a9948268fde9e1c3f","user_info":"userinfo2","score":66.586112976074}]},{"face_token":"fde61e9c074f48cf2bbb319e42634f41","location":{"left":219.4467773,"top":104.7486954,"width":81,"height":77,"rotation":3},"user_list":[{"group_id":"group1","user_id":"088717532b094c3990755e91250adf7d","user_info":"userinfo","score":65.154159545898}]}]}
     */

    private int error_code;
    private String error_msg;
    private String log_id;
    private int timestamp;
    private int cached;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * face_num : 2
         * face_list : [{"face_token":"6fe19a6ee0c4233db9b5bba4dc2b9233","location":{"left":31.95568085,"top":120.3764267,"width":87,"height":85,"rotation":-5},"user_list":[{"group_id":"group1","user_id":"5abd24fd062e49bfa906b257ec40d284","user_info":"userinfo1","score":69.85684967041},{"group_id":"group1","user_id":"2abf89cffb31473a9948268fde9e1c3f","user_info":"userinfo2","score":66.586112976074}]},{"face_token":"fde61e9c074f48cf2bbb319e42634f41","location":{"left":219.4467773,"top":104.7486954,"width":81,"height":77,"rotation":3},"user_list":[{"group_id":"group1","user_id":"088717532b094c3990755e91250adf7d","user_info":"userinfo","score":65.154159545898}]}]
         */

        private int face_num;
        private List<FaceListBean> face_list;

        public int getFace_num() {
            return face_num;
        }

        public void setFace_num(int face_num) {
            this.face_num = face_num;
        }

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class FaceListBean {
            /**
             * face_token : 6fe19a6ee0c4233db9b5bba4dc2b9233
             * location : {"left":31.95568085,"top":120.3764267,"width":87,"height":85,"rotation":-5}
             * user_list : [{"group_id":"group1","user_id":"5abd24fd062e49bfa906b257ec40d284","user_info":"userinfo1","score":69.85684967041},{"group_id":"group1","user_id":"2abf89cffb31473a9948268fde9e1c3f","user_info":"userinfo2","score":66.586112976074}]
             */

            private String face_token;
            private LocationBean location;
            private List<UserListBean> user_list;

            public String getFace_token() {
                return face_token;
            }

            public void setFace_token(String face_token) {
                this.face_token = face_token;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public List<UserListBean> getUser_list() {
                return user_list;
            }

            public void setUser_list(List<UserListBean> user_list) {
                this.user_list = user_list;
            }

            public static class LocationBean {
                /**
                 * left : 31.95568085
                 * top : 120.3764267
                 * width : 87
                 * height : 85
                 * rotation : -5
                 */

                private double left;
                private double top;
                private int width;
                private int height;
                private int rotation;

                public double getLeft() {
                    return left;
                }

                public void setLeft(double left) {
                    this.left = left;
                }

                public double getTop() {
                    return top;
                }

                public void setTop(double top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getRotation() {
                    return rotation;
                }

                public void setRotation(int rotation) {
                    this.rotation = rotation;
                }
            }

            public static class UserListBean {
                /**
                 * group_id : group1
                 * user_id : 5abd24fd062e49bfa906b257ec40d284
                 * user_info : userinfo1
                 * score : 69.85684967041
                 */

                private String group_id;
                private String user_id;
                private String user_info;
                private double score;

                public String getGroup_id() {
                    return group_id;
                }

                public void setGroup_id(String group_id) {
                    this.group_id = group_id;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getUser_info() {
                    return user_info;
                }

                public void setUser_info(String user_info) {
                    this.user_info = user_info;
                }

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }
            }
        }
    }
}
