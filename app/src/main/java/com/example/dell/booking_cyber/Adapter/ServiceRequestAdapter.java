package com.example.dell.booking_cyber.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.booking_cyber.DTO.Booking;
import com.example.dell.booking_cyber.R;

import java.util.ArrayList;

public class ServiceRequestAdapter extends ArrayAdapter<Booking> {
  private Context context;
  private int resource;

  public ServiceRequestAdapter(Context context, int resource, ArrayList<Booking> objects) {
    super(context, resource, objects);
    this.context = context;
    this.resource = resource;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the booking information
    String cybercoreName = getItem(position).getCybercoreName();
    String bookingDate = getItem(position).getBookingDate().toString();
    boolean isPaid = getItem(position).isPaid();
    int price = getItem(position).getPrice();

    LayoutInflater inflater = LayoutInflater.from(this.context);
    convertView = inflater.inflate(this.resource, parent, false);

    TextView txtCybercoreName = convertView.findViewById(R.id.txtCybercoreName);
    TextView txtBookingDate = convertView.findViewById(R.id.txtBookingDate);
    TextView txtBookingPrice = convertView.findViewById(R.id.txtPrice);
    TextView txtIsPaid = convertView.findViewById(R.id.txtIsPaid);

    txtCybercoreName.setText(cybercoreName);
    txtBookingDate.setText(bookingDate);
    txtBookingPrice.setText(Integer.toString(price) + ".000 đ");

    txtIsPaid.setText(isPaid ? "Đã đến" : "Chưa đến");

    int green = ContextCompat.getColor(this.context, R.color.green);
    int orange = ContextCompat.getColor(this.context, R.color.orange);
    txtIsPaid.setTextColor(isPaid ? green : orange);
    return convertView;
  }
}
