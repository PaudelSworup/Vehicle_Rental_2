package com.example.vehiclerental;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Users> movie;
    Context context;

    public UserAdapter( Context context, List<Users> movie) {
        this.inflater = LayoutInflater.from(context);
        this.movie = movie;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Users u = movie.get(position);
            holder.vehicleName.setText(movie.get(position).getUserName());
            holder.vehicleDestination.setText(movie.get(position).getUserDestination());
            holder.rentalTime.setText(movie.get(position).getUserRental() + " Days");
            holder.dates.setText(movie.get(position).getUserDate());
            holder.firebase_id.setText(movie.get(position).getUserFid());
            holder.userAmount.setText("Your amount is "+movie.get(position).getUserAmount());
            Picasso.get().load(movie.get(position).getUserImage()).into(holder.userImage);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    ArrayList<String> userVehicleList =new ArrayList<>();
                    userVehicleList.add(u.getUserName());
                    userVehicleList.add(u.getUserImage());
                    userVehicleList.add(u.getUserDate());
                    userVehicleList.add(u.getUserAmount());
                    userVehicleList.add(u.getUserDestination());
                    userVehicleList.add(u.getUserFid());
                    userVehicleList.add(u.getUserID());
                    Intent in  = new Intent(context, UserDetail.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.putStringArrayListExtra("vehicles",userVehicleList);
                    context.startActivity(in);
                }
            });

    }

    @Override
    public int getItemCount() {
        return movie.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView vehicleName,  vehicleDestination, rentalTime, firebase_id,dates,approved,userAmount;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.UserNameTxt);
            userImage = itemView.findViewById(R.id.UserImage);
            vehicleDestination = itemView.findViewById(R.id.UserDestination);
            rentalTime = itemView.findViewById(R.id.UserRentalTime);
            firebase_id = itemView.findViewById(R.id.UserFirebaseID);
            dates = itemView.findViewById(R.id.UserDate);
            userAmount = itemView.findViewById(R.id.userMoney);
            approved = itemView.findViewById(R.id.approvedText);
            relativeLayout = itemView.findViewById(R.id.userDetail_layout);
        }
    }
}
