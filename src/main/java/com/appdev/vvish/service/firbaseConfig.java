/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdev.vvish.service;

import com.appdev.vvish.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.client.methods.RequestBuilder.options;
import org.json.simple.parser.JSONParser;
import static org.springframework.http.RequestEntity.options;

/**
 *
 * @author Abhi
 */
public class firbaseConfig  {
    
    public void firebaseConfiguration() throws FileNotFoundException{
        System.err.println("Configuring...");
       FileInputStream serviceAccount = new FileInputStream("./assets/serviceAccountKey.json");
        JSONParser parser = new JSONParser();
        
try{
    
   // Object obj = parser.parse(new FileReader("./assets/serviceAccountKey.json") );
  FirebaseOptions options = new FirebaseOptions.Builder()
  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
  .setDatabaseUrl("https://vvish-91286.firebaseio.com")
  .build();

FirebaseApp.initializeApp(options);
// if you are running your code within Google Cloud Platform, you can make use of Google Application Default
// Credentials to automatically have the Admin SDKs themselves fetch a service account on your behalf:
//FirebaseOptions options = new FirebaseOptions.Builder()
//  .setCredential(FirebaseCredentials.applicationDefault())
//  .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
//  .build();
//
//FirebaseApp.initializeApp(options);


// Get a reference to our posts
final FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference ref = database.getReference("users");

// Attach a listener to read the data at our posts reference
List<User> userList = new ArrayList<>();
ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot snapshot) {
        userList.clear();
        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
            User user = postSnapshot.getValue(User.class);
            userList.add(user);
               System.err.println(user.toString());
            // here you can access to name property like university.name

        }
    }

      @Override
      public void onCancelled(DatabaseError de) {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
  });
        }
catch(Exception e){
    System.out.println("exception"+e);
}

    
    
    }   
    
}
