package com.samirk433.quotebook.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samirk433 on 4/24/2018.
 */

public class PhotoUnsplash {

    public String id;

    @SerializedName("user")
    public UserInfo userInfo;

    @SerializedName("urls")
    public PhotoUrls urls;

    @SerializedName("links")
    public Links links;


    public class PhotoUrls {
        public String regular;
        public String thumb;
    }

    public class Links {
        public String download_location;
    }


    public class UserInfo {
        public String name;

        @SerializedName("profile_image")
        public UserPhoto profileImage;

        @SerializedName("links")
        public UserProfile links;
    }

    public class UserPhoto {

        @SerializedName("small")
        public String small_profile_image;
    }

    public class UserProfile {
        public String html;
    }


}