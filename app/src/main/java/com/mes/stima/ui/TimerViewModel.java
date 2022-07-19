package com.mes.stima.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import timber.log.Timber;

public class TimerViewModel extends ViewModel {
    private int timerValue = 0;
    private MutableLiveData<String> timer;
    private Boolean isTimerActive = false;

    public LiveData<String> getTimer() {
        if (timer == null) {
            timer = new MutableLiveData<>();
        }
        return timer;
    }

    void startTimer() {
        new Thread(() -> {
            isTimerActive = true;
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
        }).start();
    }

    private void postTimerValue() {
        int seconds = timerValue % 60;
        int minutes = (timerValue % 3600) / 60;
        String time = seconds + " : " + minutes;
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
}