package com.example.resume.mvp.facemode;

public class Createuser {
   private String image;
   private String image_type;
   private String group_id;
   private String user_id;
   private String user_info;

    public Createuser(String image, String image_type, String group_id, String user_id, String user_info) {
        this.image = image;
        this.image_type = image_type;
        this.group_id = group_id;
        this.user_id = user_id;
        this.user_info = user_info;
    }

    public Createuser() {
    }
}
