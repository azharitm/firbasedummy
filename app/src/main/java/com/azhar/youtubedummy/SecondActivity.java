package com.azhar.youtubedummy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity {

    private TextView profileName, profileAge, profileEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String userID;
    RecyclerView myRecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        profileName = findViewById(R.id.tvProfileName);
        profileAge = findViewById(R.id.tvProfileAge);
        profileEmail = findViewById(R.id.tvProfileEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        userID=user.getUid();




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.child("users").child(userID).getValue(UserProfile.class);
                profileName.setText( userProfile.getUserName());
                profileAge.setText( userProfile.getUserAge());
                profileEmail.setText( userProfile.getUserEmail());
             //   vids vid=dataSnapshot.child("videos").getValue(vids.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
