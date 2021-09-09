package com.example.attendanceapps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SupervisorListAdapter2 extends RecyclerView.Adapter<SupervisorListAdapter2.SupervisorViewHolder> {

    ArrayList<Supervisor> supervisors;
    Context context;
    DatabaseReference databaseReference;

    public SupervisorListAdapter2(Context c, ArrayList<Supervisor> s) {
        context = c;
        supervisors = s;
    }

    @NonNull
    @Override
    public SupervisorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupervisorViewHolder(LayoutInflater.from(context).inflate(R.layout.supervisor_list2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SupervisorViewHolder holder, int position) {
        holder.fName.setText(supervisors.get(position).getFirstName());
        holder.lName.setText(supervisors.get(position).getLastName());
        holder.supervisorId.setText(supervisors.get(position).getSupervisorId());
        holder.email.setText(supervisors.get(position).getEmailAddress());
        holder.phone.setText(supervisors.get(position).getPhoneNum());

        holder.itemView.setTag(supervisors);

        final String supervisorId1 = holder.supervisorId.getText().toString();
        final String lname1 = holder.lName.getText().toString();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ChooseDateActivity.class);
                intent.putExtra("supervisorId", supervisorId1);
                intent.putExtra("lname", lname1);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return supervisors.size();
    }


    static class SupervisorViewHolder extends RecyclerView.ViewHolder {

        TextView lName, fName, email, phone, supervisorId;
        Button update, delete;

        public SupervisorViewHolder(@NonNull View itemView) {
            super(itemView);

            lName = itemView.findViewById(R.id.sLName);
            fName = itemView.findViewById(R.id.sFName);
            email = itemView.findViewById(R.id.sEmail);
            phone = itemView.findViewById(R.id.sPhone);
            supervisorId = itemView.findViewById(R.id.supervisorId);

            update = (Button) itemView.findViewById(R.id.update);
            delete = (Button) itemView.findViewById(R.id.delete);
        }
    }
}
