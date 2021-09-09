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

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SupervisorListAdapter extends RecyclerView.Adapter<SupervisorListAdapter.SupervisorViewHolder> {

    ArrayList<Supervisor> supervisors;
    Context context;
    DatabaseReference databaseReference;

    public SupervisorListAdapter(Context c, ArrayList<Supervisor> s) {
        context = c;
        supervisors = s;
    }

    @NonNull
    @Override
    public SupervisorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupervisorViewHolder(LayoutInflater.from(context).inflate(R.layout.supervisor_list, parent, false));
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

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditSupervisorActivity.class);
                intent.putExtra("lastName", supervisors.get(position).getLastName());
                intent.putExtra("firstName", supervisors.get(position).getFirstName());
                intent.putExtra("email", supervisors.get(position).getEmailAddress());
                intent.putExtra("phone", supervisors.get(position).getPhoneNum());
                intent.putExtra("supervisorId", supervisors.get(position).getSupervisorId());

                v.getContext().startActivity(intent);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete Vehicle")
                        .setMessage("Are you sure you want to delete this Vehicle?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                databaseReference = FirebaseDatabase.getInstance().getReference("Supervisor")
                                        .child(supervisorId1);
                                databaseReference.removeValue();

                                supervisors.clear();
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, supervisors.size());
                                notifyDataSetChanged();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
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
