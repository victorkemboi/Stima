package com.mes.stima.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mes.stima.R;
import com.mes.stima.databinding.FragmentTimerFragmentBinding;

import timber.log.Timber;

public class TimerFragment extends Fragment {

    private FragmentTimerFragmentBinding binding;
    private TimerViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TimerViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentTimerFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStartTimerButtonOnClickListener();
        observeTimerState();
    }

    private void setStartTimerButtonOnClickListener() {
        binding.fabStartTimer.setImageResource(R.drawable.ic_play);
        binding.fabStartTimer.setOnClickListener(view -> {
            viewModel.startTimer();
            setStopTimerButtonOnClickListener();
            setPauseTimerButtonOnClickListener();
        });
    }

    private void setPauseTimerButtonOnClickListener() {
        binding.fabStartTimer.setImageResource(R.drawable.ic_pause);
        binding.fabStartTimer.setOnClickListener(view -> {
            viewModel.pauseTimer();
            setStartTimerButtonOnClickListener();
        });
    }

    private void setStopTimerButtonOnClickListener() {
        binding.fabStopTimer.setVisibility(View.VISIBLE);
        binding.fabStopTimer.setOnClickListener(view -> {
            viewModel.stopTimer();
            setStartTimerButtonOnClickListener();
            binding.fabStopTimer.setVisibility(View.GONE);
        });
    }

    private void observeTimerState() {
        viewModel.getTimer().observe(
                getViewLifecycleOwner(),
                string -> {
                    binding.textViewCounter.setText(string);
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}