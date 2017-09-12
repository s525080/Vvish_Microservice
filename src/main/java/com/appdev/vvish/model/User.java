/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdev.vvish.model;

/**
 *
 * @author Abhi
 */
public class User {
    
    String displayName;
    String photoURL;
    String uid;

    public User(String displayName, String photoURL, String uid) {
        this.displayName = displayName;
        this.photoURL = photoURL;
        this.uid = uid;
    }
    
    
    
}
