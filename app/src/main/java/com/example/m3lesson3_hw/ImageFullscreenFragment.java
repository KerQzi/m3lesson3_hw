package com.example.m3lesson3_hw;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.m3lesson3_hw.databinding.FragmentImageFullscreenBinding;


public class ImageFullscreenFragment extends Fragment {

    private FragmentImageFullscreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentImageFullscreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            Glide.with(binding.imageView).load(getArguments().getString("cityUrl")).into(binding.imageView);
        }

        setSharedElementEnterTransition(
                TransitionInflater.from(requireContext())
                        .inflateTransition(android.R.transition.move));
    }
}