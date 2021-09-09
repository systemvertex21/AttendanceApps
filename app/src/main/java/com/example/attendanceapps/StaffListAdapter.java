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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.StaffViewHolder>{

    ArrayList<Staff> staff;
    Context context;
    DatabaseReference databaseReference;

    public StaffListAdapter(Context c, ArrayList<Staff> s) {
        context = c;
        staff = s;
    }

    @NonNull
    @NotNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new StaffListAdapter.StaffViewHolder(LayoutInflater.from(context).inflate(R.layout.staff_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StaffListAdapter.StaffViewHolder holder, int position) {
        holder.lName.setText(staff.get(position).getLastName());
        holder.fName.setText(staff.get(position).getFirstName());
        holder.email.setText(staff.get(position).getEmailAddress());
        holder.phone.setText(staff.get(position).getPhoneNum());
        holder.staffId.setText(staff.get(position).getStaffId());

        holder.itemView.setTag(staff);

        final String staffId1 = holder.staffId.getText().toString();

        holder.update.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditStaffActivity.class);
            intent.putExtra("lastName", staff.get(position).getLastName());
            intent.putExtra("firstName", staff.get(position).getFirstName());
            intent.putExtra("email", staff.get(position).getEmailAddress());
            intent.putExtra("phone", staff.get(position).getPhoneNum());
            intent.putExtra("staffId", staff.get(position).getStaffId());
            v.getContext().startActivity(intent);

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
                                databaseReference = FirebaseDatabase.getInstance().getReference("Staff")
                                        .child(staffId1);
                                databaseReference.removeValue();

                                staff.clear();
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, staff.size());
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
        return staff.size();
    }

    static class StaffViewHolder extends RecyclerView.ViewHolder {

        TextView lName, fName, email, phone, staffId;
        Button update, delete;

        public StaffViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            lName = itemView.findViewById(R.id.sLName);
            fName = itemView.findViewById(R.id.sFName);
            email = itemView.findViewById(R.id.sEmail);
            phone = itemView.findViewById(R.id.sPhone);
            staffId = itemView.findViewById(R.id.staffId);

            update = (Button) itemView.findViewById(R.id.update);
            delete = (Button) itemView.findViewById(R.id.delete);

        }
    }
}
