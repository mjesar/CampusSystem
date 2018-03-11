package com.example.mj.campussystem.admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mj.campussystem.Authentication.Users;
import com.example.mj.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by MJ on 2/9/2018.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecyclerViewHolder>  {

    private static final String TAG = RecordAdapter.class.getSimpleName();
    FirebaseAuth mAuth;
    private List<Users> studentsList;
    private Context context;
    DatabaseReference databaseReference;

    public RecordAdapter(Context context, List<Users> studentsList) {
        this.studentsList = studentsList;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference(user_id);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerViewHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        viewHolder = new RecyclerViewHolder(layoutView, studentsList);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.item_name.setText(studentsList.get(position).getName());

        context = holder.item_name.getContext();
      /*  holder.item_name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);*/
                //   final View dialogView= inflater.inflate(R.layout.dialog,null);
//                alertDialog.setTitle("Add new Record");
//                alertDialog.setView(dialogView);

                /*final Users users = studentsList.get(position);
                final String name = users.getName();
                final String classe = users.getCatogety();
                final String rollno = users.getRollno();
                final String push_id= users.getPush_id();

                final EditText et_sname =(EditText)dialogView.findViewById(R.id.et_sname);
                final EditText et_class =(EditText)dialogView.findViewById(R.id.et_class);
                final EditText  et_rollno =(EditText)dialogView.findViewById(R.id.et_rollno);
*/
                /*et_sname.setText(name);
                et_class.setText(classe);
                et_rollno.setText(rollno);


                alertDialog.setPositiveButton("KO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClicsadaak: "+push_id);

                       String name1= et_sname.getText().toString();
                        String classe1= et_class.getText().toString();
                        String rollno1= et_rollno.getText().toString();
                        Students students1= students1 = new Students(name1,classe1,rollno1,push_id);
                        databaseReference.child(push_id).setValue(students1);


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
                return false;
            }
        });*/
   /*holder.item_delete.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           final AlertDialog.Builder alertDialog =new AlertDialog.Builder(context);
           LayoutInflater inflater= LayoutInflater.from(context);
           final View dialogView= inflater.inflate(R.layout.dialog,null);
           alertDialog.setTitle("Delete");

           final Students students = studentsList.get(position);
           final String name = students.getName();
           final String push_id= students.getPush_id();

           alertDialog.setMessage("Are Sure You want to delete "+ name);
           alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChssange: "+dataSnapshot.child(push_id).getRef());
                        dataSnapshot.child(push_id).getRef().removeValue();

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
       }*/
            }



    @Override
    public int getItemCount() {
        return this.studentsList.size();
    }
}
