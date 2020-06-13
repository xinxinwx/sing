package com.example.resume.mvp.mode;

public class GetToken {

    /**
     * refresh_token : 25.17fb94f93baf8f4f868899457d940cef.315360000.1904707647.282335-19206014
     * expires_in : 2592000
     * session_key : 9mzdDcBuvzVYUeRsi46v8ie3sHUKjb3XezR488GDzom13FY6M6kUyB8LF7vQw+zHZSUyhjWWSJL4jn7/oOCIZ7sdseBptw==
     * access_token : 24.66c23eaed27885910d0ae1e4d9012051.2592000.1591939647.282335-19206014
     * scope : public brain_all_scope vis-faceverify_faceverify_h5-face-liveness vis-faceverify_FACE_V3 vis-faceverify_idl_face_merge wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理
     * session_secret : 98f3446d6caddc50af1110adeef97469
     */

    private String refresh_token;
    private String expires_in;
    private String session_key;
    private String access_token;
    private String scope;
    private String session_secret;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSession_secret() {
        return session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }
}
