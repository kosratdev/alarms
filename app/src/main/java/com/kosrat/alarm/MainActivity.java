package com.kosrat.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import com.kosrat.alarm.managers.AlarmManagerUtils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Create alarm with specified options in the UI.
     *
     * @param view Unused view.
     */
    public void setAlarm(View view) {

        EditText time = (EditText) findViewById(R.id.et_alarm_time);
        EditText interval = (EditText) findViewById(R.id.et_alarm_interval);
        Switch isRepeating = (Switch) findViewById(R.id.st_alarm_repeat);
        Switch isExact = (Switch) findViewById(R.id.st_alarm_exact);

        long millisecondInterval = Long.parseLong(interval.getText().toString()) * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Integer.parseInt(time.getText().toString()));

        if (isRepeating.isChecked() && isExact.isChecked()) {

            AlarmManagerUtils.createExactRepeatingAlarm(this, calendar, millisecondInterval);

        } else if (!isRepeating.isChecked() && !isExact.isChecked()) {

            AlarmManagerUtils.createInexactAlarm(this, calendar);

        } else if (isRepeating.isChecked()) {

            AlarmManagerUtils.createInexactRepeatingAlarm(this, calendar, millisecondInterval);

        } else if (isExact.isChecked()) {

            AlarmManagerUtils.createExactAlarm(this, calendar);
        }
    }
}
