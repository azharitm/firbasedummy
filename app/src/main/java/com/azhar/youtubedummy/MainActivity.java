package com.azhar.youtubedummy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText name;
    private EditText password;
    private Button login;
    private TextView txt;
    String message="Wrong credentials !! try again";
    private Button userRegistration;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=(EditText) findViewById(R.id.uname);
        password=(EditText)findViewById(R.id.pass);
        login=(Button)findViewById(R.id.lgn);
        userRegistration=(Button)findViewById(R.id.btRegister);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();

  //      if (user!=null){
  //          finish();
  //          Intent intent=new Intent(MainActivity.this,SecondActivity.class);
  //      }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=name.getText().toString();
                String pass=password.getText().toString();
                if (email.equals("")||pass.equals("")){

                    Toast.makeText(MainActivity.this, "Please fill all fields ", Toast.LENGTH_SHORT).show();

                }else {
                    check(email, pass);
                }
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
    }

    private void check(String userName,String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                }else{
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(MainActivity.this, "Failed Login: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });

    }

}
