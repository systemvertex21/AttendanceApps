package com.example.attendanceapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class StaffSelectAdapter extends RecyclerView.Adapter <StaffSelectAdapter.StaffSelectViewHolder> {

    List<Staff> staff;
    Context c;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

//    public int selectedPosition = 0;
//    private final ArrayList<Integer> selectCheck = new ArrayList<>();

    public StaffSelectAdapter(Context c, List<Staff> staff) {
        this.staff = staff;
        this.c = c;

//        for (int i = 0; i < staff.size(); i++) {
//            selectCheck.add(0);
//        }
    }


    @NonNull
    @NotNull
    @Override
    public StaffSelectAdapter.StaffSelectViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new StaffSelectAdapter.StaffSelectViewHolder(LayoutInflater.from(c).inflate(R.layout.staff_select, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StaffSelectAdapter.StaffSelectViewHolder holder, int position) {
        holder.staffSelectFirstName.setText(staff.get(position).getFirstName());
        holder.staffSelectLastName.setText(staff.get(position).getLastName());
        holder.phoneNum.setText(staff.get(position).getPhoneNum());
        holder.emailAddress.setText(staff.get(position).getEmailAddress());
        holder.staffId.setText(staff.get(position).getStaffId());

        final String staffSelectFirstName1 = holder.staffSelectFirstName.getText().toString();
        final String staffSelectLastName1 = holder.staffSelectLastName.getText().toString();
        final String phoneNum1 = holder.phoneNum.getText().toString();
        final String emailAddress1 = holder.emailAddress.getText().toString();
        final String staffId1 = holder.staffId.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser rUser = firebaseAuth.getCurrentUser();
        assert rUser != null;
        String userId = rUser.getUid();

        final String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
        databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(userId).child(currentDate);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("supervisorId", userId);
        hashMap.put("attendDate", currentDate);
        hashMap.put("firstName", staffSelectFirstName1);
        hashMap.put("lastName", staffSelectLastName1);
        hashMap.put("phoneNum", phoneNum1);
        hashMap.put("emailAddress", emailAddress1);
        hashMap.put("attendance", "Absent");
        hashMap.put("staffId", staffId1);
        databaseReference.child(staffId1).setValue(hashMap);

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.checkbox.setChecked(true);
                    final String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
                    databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(userId).child(currentDate);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("attendDate", currentDate);
                    hashMap.put("firstName", staffSelectFirstName1);
                    hashMap.put("lastName", staffSelectLastName1);
                    hashMap.put("phoneNum", phoneNum1);
                    hashMap.put("emailAddress", emailAddress1);
                    hashMap.put("attendance", "Present");
                    hashMap.put("staffId", staffId1);
                    databaseReference.child(staffId1).setValue(hashMap);
                } else {
                    holder.checkbox.setChecked(false);
                    final String currentDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
                    databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(userId).child(currentDate);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("attendDate", currentDate);
                    hashMap.put("firstName", staffSelectFirstName1);
                    hashMap.put("lastName", staffSelectLastName1);
                    hashMap.put("phoneNum", phoneNum1);
                    hashMap.put("emailAddress", emailAddress1);
                    hashMap.put("attendance", "Absent");
                    hashMap.put("staffId", staffId1);
                    databaseReference.child(staffId1).setValue(hashMap);

//                    final String currentDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
//                    databaseReference = FirebaseDatabase.getInstance().getReference("Attendance").child(currentDate);
//                    databaseReference.child(staffId1).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return staff.size();
    }

    public static class StaffSelectViewHolder extends RecyclerView.ViewHolder {

        TextView staffSelectLastName, staffSelectFirstName, phoneNum, emailAddress, staffId;
        CheckBox checkbox;

        public StaffSelectViewHolder(@NonNull View itemView) {
            super(itemView);

            staffSelectLastName = itemView.findViewById(R.id.staffSelectLastName);
            staffSelectFirstName = itemView.findViewById(R.id.staffSelectFirstName);
            phoneNum = itemView.findViewById(R.id.phoneNum);
            emailAddress = itemView.findViewById(R.id.emailAddress);
            staffId = itemView.findViewById(R.id.staffId);
            checkbox = itemView.findViewById(R.id.checkboxStaff);
        }
    }
}
