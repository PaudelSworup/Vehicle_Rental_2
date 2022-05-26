package com.example.vehiclerental;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
        holder.vehicleRating.setText(movie.get(position).getAdminRating());
        holder.vehicleDestination.setText(movie.get(position).getDestination());
        holder.rentalTime.setText(movie.get(position).getRentalTime());
        holder.rating_starAdmin.setRating(Float.parseFloat(movie.get(position).getAdminRating()));
        holder.firebase_id.setText(movie.get(position).getFirebase_id());
        Picasso.get().load(movie.get(position).getImageAdmin()).into(holder.vehicleImage);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vehicleImage;
        TextView vehicleName, vehicleRating, vehicleDestination, rentalTime, firebase_id;
        RatingBar rating_starAdmin;
        public ViewHolder(View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.nameTxt);
            vehicleImage = itemView.findViewById(R.id.AdminImage);
            vehicleRating = itemView.findViewById(R.id.ratingTxt);
            vehicleDestination = itemView.findViewById(R.id.Admindestination);
            rentalTime = itemView.findViewById(R.id.rentalTime);
            firebase_id = itemView.findViewById(R.id.firebaseID);
            rating_starAdmin = itemView.findViewById(R.id.rating_starAdmin);
        }
    }
}

