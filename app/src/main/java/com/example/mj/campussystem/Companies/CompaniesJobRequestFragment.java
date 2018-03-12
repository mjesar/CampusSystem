package com.example.mj.campussystem.Companies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mj.campussystem.Authentication.Users;
import com.example.mj.campussystem.Jobs;
import com.example.mj.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompaniesJobRequestFragment extends Fragment {

    private static final String TAG = CompaniesPostJobFragment.class.getSimpleName();
    FirebaseAuth mAuth;
    List<Users> usersArrayList;
    List<Jobs> jobsArrayList;

    RecyclerView recyclerView;
    CompanyAdapter companyAdapter;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_companies_job_request_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Applied");

        usersArrayList = new ArrayList<Users>();
        jobsArrayList = new ArrayList<Jobs>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_students);
        recyclerView.setLayoutManager(linearLayoutManager);

        companyAdapter = new CompanyAdapter(getActivity(), jobsArrayList);

        recyclerView.setAdapter(companyAdapter);
        PopulateRecelerView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Jobs requests");
    }
    public void PopulateRecelerView() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String user_id = mAuth.getCurrentUser().getUid();


                Users users= dataSnapshot.getValue(Users.class);
                Jobs jobs = dataSnapshot.getValue(Jobs.class);
                // users.setUser_id(dataSnapshot.getKey());
                if (jobs.getCompany().equals(user_id)){
                    jobsArrayList.add(jobs);

                    Log.d(TAG, "onChildChangedStudents: "+jobs.getCompany());


                    companyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                Users users= dataSnapshot.getValue(Users.class);
                int index = getItemIndex(dataSnapshot.getKey());
                usersArrayList.set(index, users);
                companyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                int index = getItemIndex(dataSnapshot.getKey());
                usersArrayList.remove(index);
                companyAdapter.notifyDataSetChanged();


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

