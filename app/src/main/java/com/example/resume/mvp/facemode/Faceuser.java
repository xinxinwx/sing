package com.example.resume.mvp.facemode;

public class Faceuser {
    private String image;
    private String image_type;
    private String group_id_list;

    public Faceuser() {
    }

    public Faceuser(String image, String image_type, String group_id_list) {
        this.image = image;
        this.image_type = image_type;
        this.group_id_list = group_id_list;
    }
}
