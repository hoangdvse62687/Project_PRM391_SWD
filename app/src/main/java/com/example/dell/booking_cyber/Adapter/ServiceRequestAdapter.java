package com.example.dell.booking_cyber.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceRequestAdapter extends ArrayAdapter<ServiceRequestDetailDTO> {
  private Context context;
  private int resource;

  public ServiceRequestAdapter(Context context, int resource, List<ServiceRequestDetailDTO> objects) {
    super(context, resource, objects);
    this.context = context;
    this.resource = resource;
  }

  @NonNull
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the booking information
    String cybercoreName = getItem(position).getCyberGamingName();

    String bookingDate = new SimpleDateFormat("dd/MM/yyyy").format(getItem(position).getDateRequest());;
    boolean isPaid = getItem(position).getPaid();
    boolean isExpired = getItem(position).getGoingDate().compareTo(new Date()) < 0;
    boolean isApproved = getItem(position).getApproved();
    double price = getItem(position).getTotalPrice();

    LayoutInflater inflater = LayoutInflater.from(this.context);
    convertView = inflater.inflate(this.resource, parent, false);

    TextView txtCybercoreName = convertView.findViewById(R.id.txtCybercoreName);
    TextView txtBookingDate = convertView.findViewById(R.id.txtBookingDate);
    TextView txtBookingPrice = convertView.findViewById(R.id.txtPrice);
    TextView txtIsPaid = convertView.findViewById(R.id.txtIsPaid);

    txtCybercoreName.setText(cybercoreName);
    txtBookingDate.setText(bookingDate);

    txtBookingPrice.setText(NumberFormat.getCurrencyInstance(new Locale("vi", "VI")).format(price).replace("US$", ""));

    String check = isPaid ? "Đã đến" : (isExpired ? "Không hoàn thành" :(isApproved ? "Đang chờ kiểm duyệt" : "Chưa đến"));

    txtIsPaid.setText(isPaid ? "Đã đến" : (isExpired ? "Không hoàn thành" :(isApproved ? "Chưa đến" : "Đang chờ kiểm duyệt")));

    int green = ContextCompat.getColor(this.context, R.color.green);
    int orange = ContextCompat.getColor(this.context, R.color.orange);
    int red = ContextCompat.getColor(this.context, R.color.red);
    int blue = ContextCompat.getColor(this.context, R.color.blue);
    txtIsPaid.setTextColor(isPaid ? green : (isExpired ? red :(isApproved ? orange : blue)));
    return convertView;
  }
}
