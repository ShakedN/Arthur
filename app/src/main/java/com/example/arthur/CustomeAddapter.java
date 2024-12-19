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

    private ArrayList<dataMoudle> originalDataset; // Original unfiltered dataset
    private ArrayList<dataMoudle> filteredDataset; // Dataset used for filtering

    public CustomeAddapter(ArrayList<dataMoudle> dataset) {
        this.originalDataset = new ArrayList<>(dataset);
        this.filteredDataset = dataset; // Initially, both lists are the same
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    String message = "Name: " + clickedItem.getName() + "\n" +
                            "Description: " + clickedItem.getDescription();
                    builder.setTitle("Details")
                            .setMessage(message)
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

    /**
     * Updates the dataset to display only items that match the search query.
     *
     * @param query The search query.
     */
    public void filter(String query) {
        filteredDataset = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            // If query is empty, show all items
            filteredDataset.addAll(originalDataset);
        } else {
            // Filter items that contain the query (case-insensitive)
            String lowerCaseQuery = query.toLowerCase();
            for (dataMoudle item : originalDataset) {
                if (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                        item.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                    filteredDataset.add(item);
                }
            }
        }
        // Notify the adapter to update the RecyclerView
        notifyDataSetChanged();
    }
}
