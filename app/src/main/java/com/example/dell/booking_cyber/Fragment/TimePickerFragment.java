package com.example.dell.booking_cyber.Fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

  public TimePickerFragment() {
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current time as the default values for the picker
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    // Create a new instance of TimePickerDialog and return it
    TimePickerDialog timePickerDialog = new TimePickerDialog(
            getActivity(),
            R.style.DialogTheme,
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(getActivity()));
    return timePickerDialog;
  }

  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    String editText = getArguments().getString("editText");
    switch (editText) {
      case LocaleData.GOING_TIME:
        EditText edtGoingTime = getActivity().findViewById(R.id.edtGoingTime);
        edtGoingTime.setText(String.valueOf(hourOfDay) + LocaleData.HOUR + " " + String.valueOf(minute) + LocaleData.MINUTE);
        break;
      case LocaleData.DURATION:
        EditText edtDuration = getActivity().findViewById(R.id.edtDuration);
        edtDuration.setText(String.valueOf(hourOfDay) + LocaleData.HOUR + " " + String.valueOf(minute) + LocaleData.MINUTE);
        break;
      default:
        break;
    }
  }
}