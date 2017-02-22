package com.boo.app.api.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by razir on 5/27/2016.
 */
public class PostData {
    @SerializedName("user_id")
    Long userID;
    @SerializedName("ref_id")
    Long postID;

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }
}
