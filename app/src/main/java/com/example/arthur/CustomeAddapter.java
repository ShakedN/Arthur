package com.example.arthur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomeAddapter extends RecyclerView.Adapter<CustomeAddapter.MyViewHolder> {

    private ArrayList<dataMoudle> originalDataset;
    private ArrayList<dataMoudle> filteredDataset;

    public CustomeAddapter(ArrayList<dataMoudle> dataset) {
        this.originalDataset = dataset;
        this.filteredDataset = new ArrayList<>(dataset);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewName;
        TextView textViewDescription;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRes);
            textViewName = itemView.findViewById(R.id.textView);
            textViewDescription = itemView.findViewById(R.id.textView2);
            imageViewIcon = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        dataMoudle clickedItem = filteredDataset.get(position);


                        LayoutInflater inflater = LayoutInflater.from(v.getContext());
                        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);


                        ImageView imageView = dialogView.findViewById(R.id.imageView_alert_dialog); // Set ID in custom layout
                        TextView textViewName = dialogView.findViewById(R.id.textView_name_alert_dialog); // Set ID in custom layout
                        TextView textViewDescription = dialogView.findViewById(R.id.textView_description_alert_dialog); // Set ID in custom layout

                        imageView.setImageResource(clickedItem.getImage());
                        textViewName.setText(clickedItem.getName());
                        textViewDescription.setText(clickedItem.getDescription());


                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setView(dialogView)
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();

                    }
                });
            }
        }

    @NonNull
    @Override
    public CustomeAddapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAddapter.MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        TextView textViewDescription = holder.textViewDescription;
        ImageView imageView = holder.imageViewIcon;
        dataMoudle currentItem = filteredDataset.get(position);
        textViewName.setText(currentItem.getName());
        textViewDescription.setText(currentItem.getDescription());
        imageView.setImageResource(currentItem.getImage());
    }

    @Override
    public int getItemCount() {
        return filteredDataset.size();
    }


    public void filter(String query) {
        filteredDataset.clear();

        if (query == null || query.isEmpty()) {
            filteredDataset.addAll(originalDataset); // Add all original items
        } else {
            String lowerCaseQuery = query.trim().toLowerCase(); // Trim spaces!
            for (dataMoudle item : originalDataset) {
                String itemName = item.getName().trim().toLowerCase(); // Trim spaces!


                if (itemName.contains(lowerCaseQuery) ) {
                    filteredDataset.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
