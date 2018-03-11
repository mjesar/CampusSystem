package com.example.mj.campussystem.admin;

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
import com.example.mj.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJ on 3/7/2018.
 */

public class AdminCompaniesFragment extends Fragment {

    private static final String TAG = AdminStudentFragment.class.getSimpleName();
    View myView;
    FirebaseAuth mAuth;
    List<Users> usersArrayList;

    RecordAdapter recordAdapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_students,container,false);

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        usersArrayList = new ArrayList<Users>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_students);
        recyclerView.setLayoutManager(linearLayoutManager);

        recordAdapter = new RecordAdapter(getActivity(), usersArrayList);

        recyclerView.setAdapter(recordAdapter);
        PopulateRecelerView();

        return view;


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Companies");
    }
    public void PopulateRecelerView() {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                Log.d(TAG, "onChildAdded:saaa " + dataSnapshot.getValue().toString());
                Users users= dataSnapshot.getValue(Users.class);
                users.setUser_id(dataSnapshot.getKey());
                Log.d(TAG, "onChildAdded: " + users.getName());
                if (users.getCatogety().equals("company")){
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
