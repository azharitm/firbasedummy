package com.azhar.youtubedummy;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName,userPassword,userEmail,userAge;
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email, name, age, password;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        firebaseAuth=FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                sendUserData();
                                Toast.makeText(RegistrationActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }
                            else{
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast.makeText(RegistrationActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });

    }

    private void setupUIViews()
    {
        userName=(EditText) findViewById(R.id.etuserName);
        userPassword=(EditText)findViewById(R.id.etUserPassword);
        userEmail=(EditText)findViewById(R.id.etUserEmail);
        regButton=(Button)findViewById(R.id.btnRegister);
        userAge=(EditText)findViewById(R.id.etAge);
        userProfilePic=(ImageView)findViewById(R.id.ivProfile);
    }

    private boolean validate(){
        Boolean result=false;
        name=userName.getText().toString();
        password=userPassword.getText().toString();
        email=userEmail.getText().toString();
        age = userAge.getText().toString();


        if (name.isEmpty()||password.isEmpty()||email.isEmpty()||age.isEmpty()){
            Toast.makeText(this,"Please fill all the feilds",Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return result;
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        userID=user.getUid();
 //       StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
 //       UploadTask uploadTask = imageReference.putFile(imagePath);
 //       uploadTask.addOnFailureListener(new OnFailureListener() {
 //           @Override
 //           public void onFailure(@NonNull Exception e) {
 //               Toast.makeText(RegistrationActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
 //           }
 //       }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
 //           @Override
 //           public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
 //               Toast.makeText(RegistrationActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
 //           }
 //       });
        UserProfile userProfile = new UserProfile(age, email, name);
        myRef.child("users").child(userID).setValue(userProfile);
    }

}
