package com.example.mj.campussystem.Companies;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mj.campussystem.Authentication.Users;
import com.example.mj.campussystem.R;
import com.example.mj.campussystem.admin.RecordAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompaniesPostJobFragment extends Fragment {

    private static final String TAG = CompaniesPostJobFragment.class.getSimpleName();

    FirebaseAuth mAuth;
    List<Users> usersArrayList;

    RecordAdapter recordAdapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.activity_companies_post_job_fragment,container,false);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab1);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Jobs");

        usersArrayList = new ArrayList<Users>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_students);
        recyclerView.setLayoutManager(linearLayoutManager);

        recordAdapter = new RecordAdapter (getActivity(), usersArrayList);

        recyclerView.setAdapter(recordAdapter);
        PopulateRecelerView();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View dialogView = inflater.inflate(R.layout.dialog, null);
                alertDialog.setTitle("Add new Job");
                alertDialog.setView(dialogView);


                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String user_id = mAuth.getCurrentUser().getUid();

                        String push_id = databaseReference.child(user_id).push().getKey();




                       EditText et_sname = (EditText) dialogView.findViewById(R.id.et_job_name);


                        String nameJob = et_sname.getText().toString();
                        String job= "job";
                        if (TextUtils.isEmpty(nameJob)) {
                            Toast.makeText(getActivity(), "Provide Complete Info", Toast.LENGTH_SHORT).show();
                        }else {


                            Users users = new Users(nameJob, user_id, job);
                            databaseReference.child(push_id).setValue(users);

                        }
                    }

                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick (DialogInterface dialog,int which){


                    }

                });


                alertDialog.create();
                alertDialog.show();


            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Post Job");
    }

    public void PopulateRecelerView() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String user_id = mAuth.getCurrentUser().getUid();


                Users users= dataSnapshot.getValue(Users.class);

               // users.setUser_id(dataSnapshot.getKey());
                if (users.getCatogety().equals("job")&& users.getUser_id().equals(user_id)){
                    Log.d(TAG, "onChildAddedwqwqw: "+users.getNameJob());
                    usersArrayList.add(users);
                    recordAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                Users users= dataSnapshot.getValue(Users.class);
                int index = getItemIndex(dataSnapshot.getKey());
                usersArrayList.set(index, users);
                recordAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                int index = getItemIndex(dataSnapshot.getKey());
                usersArrayList.remove(index);
                recordAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public int getItemIndex(String key) {
        int index = -1;
        for (int i = 0; i < usersArrayList.size(); i++) {
            if (usersArrayList.get(i).getUser_id().equals(key)) {
                index = i;
                break;
            }
        }
        return index;

    }
}
