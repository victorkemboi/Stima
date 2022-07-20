package com.mes.stima.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimerViewModelTest {
    TimerViewModel timerViewModel;

    @Before
    public void setUp() {
        timerViewModel = new TimerViewModel();
    }

    @Test
    public void testGetTimer() {
        String timeValue = timerViewModel.getTimer().getValue();
        assertSame(timeValue, "00 : 00");
    }

    @Test
    public void startTimer() {
        new Thread(() -> {
            timerViewModel.startTimer();
            try {
                Thread.sleep(1000);
                assertSame(
                        timerViewModel.getTimer().getValue(),
                        "00 : 01"
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void stopTimer() {
        new Thread(() -> {
            timerViewModel.startTimer();
            try {
                Thread.sleep(1000);
                assertSame(
                        timerViewModel.getTimer().getValue(),
                        "00 : 01"
                );
                timerViewModel.stopTimer();
                Thread.sleep(1000);
                assertSame(
                        timerViewModel.getTimer().getValue(),
                        "00 : 00"
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pauseTimer() {
        new Thread(() -> {
            timerViewModel.startTimer();
            try {
                Thread.sleep(1000);
                timerViewModel.pauseTimer();
                assertSame(
                        timerViewModel.getTimer().getValue(),
                        "00 : 01"
                );
                Thread.sleep(1000);
                assertSame(
                        timerViewModel.getTimer().getValue(),
                        "00 : 01"
                );
                timerViewModel.startTimer();
                Thread.sleep(1000);
                assertSame(
                        timerViewModel.getTimer().getValue(),
                        "00 : 02"
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}