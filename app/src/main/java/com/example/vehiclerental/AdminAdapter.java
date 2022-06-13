package com.example.vehiclerental;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Admin> movie;
    Context context;

    public AdminAdapter( Context context, List<Admin> movie) {
        this.inflater = LayoutInflater.from(context);
        this.movie = movie;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.admin_list,parent,false);
        return new AdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Admin admin = movie.get(position);
        holder.vehicleName.setText(movie.get(position).getAdminName());
        holder.vehicleSource.setText(movie.get(position).getSource());
        holder.vehicleDestination.setText(movie.get(position).getDestination());
        holder.rentalTime.setText(movie.get(position).getRentalTime() + " Days");
        holder.rating_starAdmin.setRating(Float.parseFloat(movie.get(position).getAdminRating()));
        holder.firebase_id.setText(movie.get(position).getFirebase_id());
        Picasso.get().load(movie.get(position).getImageAdmin()).into(holder.vehicleImage);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> AdminVehicleList =new ArrayList<>();
                AdminVehicleList.add(admin.getAdminName());
                AdminVehicleList.add(admin.getSource());
                AdminVehicleList.add(admin.getDestination());
                AdminVehicleList.add(admin.getFirebase_id());
                AdminVehicleList.add(admin.getImageAdmin());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vehicleImage;
        TextView vehicleName, vehicleSource, vehicleDestination, rentalTime, firebase_id;
        RatingBar rating_starAdmin;
        RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.nameTxt);
            vehicleImage = itemView.findViewById(R.id.AdminImage);
            vehicleSource = itemView.findViewById(R.id.Adminsource);
            vehicleDestination = itemView.findViewById(R.id.Admindestination);
            rentalTime = itemView.findViewById(R.id.rentalTime);
            firebase_id = itemView.findViewById(R.id.firebaseID);
            rating_starAdmin = itemView.findViewById(R.id.rating_starAdmin);
            relativeLayout = itemView.findViewById(R.id.Admin_layout);
        }
    }
}

