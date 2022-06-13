package com.example.vehiclerental;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Vehicle> movie;
    Context context;


    public Adapter(Context context, List<Vehicle> movie){
        this.inflater = LayoutInflater.from(context);
        this.movie = movie;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vehicle mov = movie.get(position);
        String capital = mov.getName();
        String output = capital.substring(0, 1).toUpperCase() + capital.substring(1);
        holder.vehicleName.setText(output);
//        holder.vehicleRating.setText(movie.get(position).getRating());
        holder.contactNumber.setText(movie.get(position).getPhone());
        holder.vehicleType.setText(movie.get(position).getType());
//        holder.vehicleCategory.setText(movie.get(position).getCategory());
        holder.ratingBar.setRating(Float.parseFloat(movie.get(position).getRating()));
        Picasso.get().load(movie.get(position).getImage()).into(holder.vehicleImage);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> vehicleList =new ArrayList<>();
                vehicleList.add(mov.getName());
                vehicleList.add(mov.getImage());
                vehicleList.add(mov.getDescription());
                vehicleList.add(mov.getRating());
                vehicleList.add(mov.getType());
                vehicleList.add(mov.getPhone());
                vehicleList.add(mov.getCategory());
                Log.d("hDesc", "onClick: " + mov.getRating());
                Intent in  = new Intent(context, Detail.class);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putStringArrayListExtra("vehicles_Rental",vehicleList);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView vehicleName,vehicleType, contactNumber, vehicleCategory;
        RatingBar ratingBar;
        ImageView vehicleImage;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.txtTitle);
//            vehicleRating = itemView.findViewById(R.id.txtRating);
            contactNumber = itemView.findViewById(R.id.txtPhone);
            vehicleType = itemView.findViewById(R.id.txtType);
            ratingBar = itemView.findViewById(R.id.rating_star);
            vehicleImage = itemView.findViewById(R.id.image);
//            vehicleCategory = itemView.findViewById(R.id.txtCat);
            relativeLayout = itemView.findViewById(R.id.main_layout);

        }


    }



}

