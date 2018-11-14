package com.example.dell.booking_cyber.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

  public DatePickerFragment() {
  }

  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    final Calendar calendar = Calendar.getInstance();
    int yy = calendar.get(Calendar.YEAR);
    int mm = calendar.get(Calendar.MONTH);
    int dd = calendar.get(Calendar.DAY_OF_MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, this, yy, mm, dd);
    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()- 1000);
    return datePickerDialog;
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    String editText = getArguments().getString("editText");
    switch (editText) {
      case LocaleData.GOING_DATE:
        EditText edtGoingDate = getActivity().findViewById(R.id.edtGoingDate);
        edtGoingDate.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
        break;
      default:
        break;
    }
  }
}
