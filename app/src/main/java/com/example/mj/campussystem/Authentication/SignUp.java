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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mj.campussystem.R;
import com.example.mj.campussystem.admin.AdminCompaniesFragment;
import com.example.mj.campussystem.admin.AdminStudentFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = SignUp.class.getSimpleName() ;
    EditText et_name;
    EditText et_email1;
    EditText et_password1;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String catogery ="";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       progressDialog = new ProgressDialog(this);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email1 = (EditText)findViewById(R.id.et_email1);
        et_password1 = (EditText)findViewById(R.id.et_password1);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();











    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.studentRB:
                if (checked)
                    catogery="student";

                    Toast.makeText(SignUp.this," Catogery "+catogery,Toast.LENGTH_LONG).show();
                break;
            case R.id.companyRb:
                if (checked)
                    catogery="company";
                    Toast.makeText(SignUp.this," Catogery "+catogery,Toast.LENGTH_LONG).show();
                break;
        }
    }


    public void Gotologin(View view) {


        Intent intent = new Intent(SignUp.this,SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    public void RegisterUser(View view) {


        final String email = et_email1.getText().toString().trim();
        String password = et_password1.getText().toString().trim();
        final String name = et_name.getText().toString().trim();



        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {



            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressDialog.show();
                    if (task.isSuccessful()) {

                        String user_id = mAuth.getCurrentUser().getUid();
                        Users users = new Users(name, email, user_id,catogery);
                        databaseReference.child(user_id).setValue(users);
                        Log.d(TAG, "onComplete: "+users.getCatogety().toString());

                        if (users.getCatogety().toString()=="student"){
                            Intent intent = new Intent(SignUp.this, AdminStudentFragment.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(SignUp.this, "time to goto Student", Toast.LENGTH_SHORT).show();
                        }else if (users.getCatogety().toString()=="company"){
                            Toast.makeText(SignUp.this, "time to goto Company", Toast.LENGTH_SHORT).show();

                            Intent intent1 = new Intent(SignUp.this, AdminCompaniesFragment.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);

                        }
                            progressDialog.dismiss();


                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignUp.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            });


        } else {
            Toast.makeText(this, "Please fill all fields ", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }
}
