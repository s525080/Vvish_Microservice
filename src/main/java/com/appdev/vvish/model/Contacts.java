package com.appdev.vvish.model;


import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)

public class Contacts {

private String displayName;

private String photoUrl;

private String photoURL;

private String videoUrl;

private String tel;

private String uid;

private ArrayList<String> mediaFiles;



public Contacts() {

super();

}


@Override

public String toString() {

return "Contacts [displayName=" + displayName + ", photoUrl=" + photoUrl + ", videoUrl=" + videoUrl + ", tel="

+ tel + ", uid=" + uid + ", mediaFiles=" + mediaFiles + "]";

}


public String getDisplayName() {

return displayName;

}

public void setDisplayName(String displayName) {

this.displayName = displayName;

}

public String getPhotoUrl() {

return photoUrl;

}

public void setPhotoUrl(String photoUrl) {

this.photoUrl = photoUrl;

}

public String getPhotoURL() {

return photoURL;

}

public void setPhotoURL(String photoURL) {

this.photoURL = photoURL;

}

public String getVideoUrl() {

return videoUrl;

}

public void setVideoUrl(String videoUrl) {

this.videoUrl = videoUrl;

}

public String getTel() {

return tel;

}

public void setTel(String tel) {

this.tel = tel;

}

public String getUid() {

return uid;

}

public void setUid(String uid) {

this.uid = uid;

}

public ArrayList<String> getMediaFiles() {

return mediaFiles;

}

public void setMediaFiles(ArrayList<String> mediaFiles) {

this.mediaFiles = mediaFiles;

}



}