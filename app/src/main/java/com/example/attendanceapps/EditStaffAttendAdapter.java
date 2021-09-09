package com.example.attendanceapps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EditStaffAttendAdapter extends RecyclerView.Adapter <EditStaffAttendAdapter.EditStaffAttendViewHolder> {

    ArrayList<Attendance> attendances;
    Context c;

    public EditStaffAttendAdapter(ArrayList<Attendance> attendances, Context c) {
        this.attendances = attendances;
        this.c = c;
    }


    @NonNull
    @NotNull
    @Override
    public EditStaffAttendAdapter.EditStaffAttendViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new EditStaffAttendAdapter.EditStaffAttendViewHolder(LayoutInflater.from(c).inflate(R.layout.staff_attend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EditStaffAttendAdapter.EditStaffAttendViewHolder holder, int position) {
        holder.staffLastName.setText(attendances.get(position).getLastName());
        holder.staffFirstName.setText(attendances.get(position).getFirstName());
        holder.attendDate.setText(attendances.get(position).getAttendDate());
        holder.attendStatus.setText(attendances.get(position).getAttendance());
        holder.staffId.setText(attendances.get(position).getStaffId());
        holder.phoneNum.setText(attendances.get(position).getPhoneNum());
        holder.emailAddress.setText(attendances.get(position).getEmailAddress());

        if(holder.attendStatus.getText().toString().contains("Present")){
            holder.attendStatus.setTextColor(Color.parseColor("#00bfa5"));
        } else if (holder.attendStatus.getText().toString().contains("Absent")) {
            holder.attendStatus.setTextColor(Color.parseColor("#FF0000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditStaffAttendActivity.class);
                intent.putExtra("staffLastName", attendances.get(position).getLastName());
                intent.putExtra("staffFirstName", attendances.get(position).getFirstName());
                intent.putExtra("attendDate", attendances.get(position).getAttendDate());
                intent.putExtra("attendance", attendances.get(position).getAttendance());
                intent.putExtra("staffId", attendances.get(position).getStaffId());
                intent.putExtra("phoneNum", attendances.get(position).getPhoneNum());
                intent.putExtra("emailAddress", attendances.get(position).getEmailAddress());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }

    public static class EditStaffAttendViewHolder extends RecyclerView.ViewHolder {

        TextView staffLastName, staffFirstName, staffId, attendDate, attendStatus, phoneNum, emailAddress;

        public EditStaffAttendViewHolder(@NonNull View itemView) {
            super(itemView);

            staffLastName = itemView.findViewById(R.id.staffLastName);
            staffFirstName = itemView.findViewById(R.id.staffFirstName);
            attendStatus = itemView.findViewById(R.id.attendStatus);
            attendDate = itemView.findViewById(R.id.attendDate);
            staffId = itemView.findViewById(R.id.staffId);
            phoneNum = itemView.findViewById(R.id.phoneNum);
            emailAddress = itemView.findViewById(R.id.emailAddress);
        }
    }
}
