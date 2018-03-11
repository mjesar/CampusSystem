package com.example.mj.campussystem.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mj.campussystem.Companies.Companies;
import com.example.mj.campussystem.R;
import com.example.mj.campussystem.Students.Students;
import com.example.mj.campussystem.admin.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {


    private static final String TAG = SignIn.class.getSimpleName() ;
    EditText et_email;
    EditText et_password;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressDialog=new  ProgressDialog(this);

        et_email =(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        mAuth =  FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

            handleUsers();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        handleUsers();

    }

    public void LoginUser(View view) {

        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please provide email and password", Toast.LENGTH_SHORT).show();
        } else {


            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");

                            handleUsers();


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                            progressDialog.dismiss();

                    }
                });


            }
        }
    }
    public void GotoRegister(View view) {
        Intent intent = new Intent(SignIn.this,SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void handleUsers(){


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (mAuth.getCurrentUser() != null) {
                    progressDialog.show();
                    final String user_id = mAuth.getCurrentUser().getUid();

                    Users users = dataSnapshot.child(user_id).getValue(Users.class);

                    Log.d(TAG, "onDataChange: " + users.getCatogety());


                    if (users.getCatogety().toString().equals("student")) {
                        Log.d(TAG, "onDataChangesad: " + "successfull opened Student");
                        Intent intent = new Intent(SignIn.this, Students.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);


                    } else if (users.getCatogety().toString().equals("company")) {

                        Log.d(TAG, "onDataChangesad: " + "successfull opened company");

                        Intent intent1 = new Intent(SignIn.this, Companies.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                    } else if (users.getCatogety().equals("admin")) {
                        Log.d(TAG, "onDataChangesad: " + "successfull opened admin");

                        Intent intent2 = new Intent(SignIn.this, Admin.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
