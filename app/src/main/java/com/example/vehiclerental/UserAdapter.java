package com.example.vehiclerental;

import android.content.Context;
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
        Users u = new Users();
        holder.vehicleName.setText(movie.get(position).getUserName());
        holder.vehicleDestination.setText(movie.get(position).getUserDestination());
        holder.rentalTime.setText(movie.get(position).getUserRental() + " Days");
        holder.dates.setText(movie.get(position).getUserDate());
        holder.firebase_id.setText(movie.get(position).getUserFid());
        Picasso.get().load(movie.get(position).getUserImage()).into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return movie.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView vehicleName,  vehicleDestination, rentalTime, firebase_id,dates,approved;
        public ViewHolder(View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.UserNameTxt);
            userImage = itemView.findViewById(R.id.UserImage);
            vehicleDestination = itemView.findViewById(R.id.UserDestination);
            rentalTime = itemView.findViewById(R.id.UserRentalTime);
            firebase_id = itemView.findViewById(R.id.UserFirebaseID);
            dates = itemView.findViewById(R.id.UserDate);
            approved = itemView.findViewById(R.id.approvedText);


        }
    }


}
