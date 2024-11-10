package com.example.m3lesson3_hw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.m3lesson3_hw.databinding.ItemLocationBinding;

import java.util.List; // Замените на свой путь

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> items;
    private Context context;
    private onItemClick onItemClick;

    public LocationAdapter(List<Location> items, Context context, com.example.m3lesson3_hw.onItemClick onItemClick) {
        this.items = items;
        this.context = context;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Используем ViewBinding для инфлейта
        ItemLocationBinding binding = ItemLocationBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location item = items.get(position);
        holder.bind(item);// Метод привязки данных в ViewHolder
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder с использованием ViewBinding
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLocationBinding binding;

        public ViewHolder(ItemLocationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemClick != null && position != RecyclerView.NO_POSITION) {
                    onItemClick.onClick(position); // Передаем позицию элемента при клике
                }
            });
        }

        public void bind(Location item) {
            binding.textViewName.setText(item.getName());
            Glide.with(context).load(item.getImageUrl()).into(binding.imageView);
        }
    }
}