package com.example.mj.campussystem.Companies;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mj.campussystem.Authentication.Users;
import com.example.mj.campussystem.Jobs;
import com.example.mj.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by MJ on 3/12/2018.
 */





    public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder>{

        FirebaseAuth mAuth;
        TextView item_apply;



        private List<Users> studentsList;
    private List<Jobs> jobsList;

    private Context context;
        DatabaseReference databaseReference;
        DatabaseReference databaseReference2;

        public CompanyAdapter(Context context, List<Jobs> studentsList) {
            this.jobsList = studentsList;
            this.context = context;
            mAuth = FirebaseAuth.getInstance();

            databaseReference= FirebaseDatabase.getInstance().getReference("Users");
            databaseReference2 = FirebaseDatabase.getInstance().getReference("Applied");
        }



        @Override
        public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CompanyViewHolder viewHolder = null;
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jobs_layout, parent, false);
            viewHolder = new CompanyViewHolder(layoutView, studentsList);

            return viewHolder;
        }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, final int position) {
        holder.item_name_job.setText(jobsList.get(position).getStudent_name());

        context = holder.item_name_job.getContext();
        holder.item_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                alertDialog.setTitle("Do you want to apply for this job");


                final String user_id = mAuth.getCurrentUser().getUid();
                item_apply = (TextView) v.findViewById(R.id.item_delete);
                final String push_id = databaseReference2.child(user_id).push().getKey();



                final Users users = studentsList.get(position);
                final String name = users.getName();
                final String companyName =users.getUser_id();



                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                Users users2 = dataSnapshot.child(user_id).getValue(Users.class);
                                final String student_name= users2.getName();



                                Jobs jobs = new Jobs(name,companyName,student_name);
                                databaseReference2.child(push_id).setValue(jobs);
                                item_apply.setText("Applied");
                                item_apply.setTextColor(Color.GREEN);
                                Toast.makeText(context, "Applied for "+name+" "+companyName, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                alertDialog.create();
                alertDialog.show();



            }
        });

    }

    @Override
    public int getItemCount() {
        return this.jobsList.size();
    }
    }

