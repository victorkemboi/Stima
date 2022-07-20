package com.mes.stima.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import timber.log.Timber;


public class TimerViewModel extends ViewModel {
    private int timerValue = 0;
    private MutableLiveData<String> timer;
    private Boolean isTimerActive = false;
    private final Thread timerThread = new Thread(new Timer());
    private Boolean timerThreadStarted = false;

    public LiveData<String> getTimer() {
        if (timer == null) {
            timer = new MutableLiveData<>("00 : 00");
        }
        return timer;
    }

    void startTimer() {
        isTimerActive = true;
        if (!timerThreadStarted) {
            timerThread.start();
            timerThreadStarted = true;
        }
    }

    private void postTimerValue() {
        int seconds = timerValue % 60;
        int minutes = (timerValue % 3600) / 60;
        String time = formatNumber(minutes) + " : " + formatNumber(seconds);
        timer.postValue(time);
    }

    void stopTimer() {
        isTimerActive = false;
        timerValue = 0;
        postTimerValue();
    }

    void pauseTimer() {
        isTimerActive = false;
    }

    @NonNull
    private String formatNumber(int value) {
        String formattedValue;
        if (value < 10) {
            formattedValue = "0" + value;
        } else {
            formattedValue = String.valueOf(value);
        }
        return formattedValue;
    }

    public class Timer implements Runnable {
        @Override
        public void run() {
            while (isTimerActive) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Timber.e("InterruptedException Exception %s", e.getMessage());
                }
                if (isTimerActive) {
                    timerValue += 1;
                    postTimerValue();
                }
            }
        }
    }
}