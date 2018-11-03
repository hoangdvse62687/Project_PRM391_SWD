package com.example.dell.booking_cyber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.Fragment.GoogleMapApiFragment;
import com.example.dell.booking_cyber.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private GoogleMapApiFragment fragment;
    private List<ServiceRequestDTO> Data = new ArrayList<>();
    public OrderListViewAdapter(Context context, GoogleMapApiFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    public void addItem(final ServiceRequestDTO item) {
        Data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return Data.indexOf(getItem(position));
    }

    private class ViewHolder{
        public ImageView imgCyber;
        public TextView lblShopName;
        public RatingBar ratingShop;
        public TextView lblQuantityOrder;
        public TextView lblTotalMoney;
        public TextView lblTimeStart;
        public Button btnFindWay;

        public void initData(ServiceRequestDTO detail){
            try{
                this.imgCyber.setImageBitmap(LocaleData.loadBitmap("https://image.ibb.co/eZasF0/icon-shop-detail.jpg"));
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                this.lblShopName.setText("Ba Dao 2");
                this.ratingShop.setRating(3);
                this.lblQuantityOrder.setText("3");
                this.lblTotalMoney.setText("10,000d");
                this.lblTimeStart.setText(df.format(new Date()));
                this.btnFindWay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.findDirection();
                    }
                });
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.order_view_component, null);
        viewHolder.imgCyber = convertView.findViewById(R.id.imgCyber);
        viewHolder.lblShopName = convertView.findViewById(R.id.lblShopName);
        viewHolder.ratingShop = convertView.findViewById(R.id.ratingShop);
        viewHolder.lblQuantityOrder = convertView.findViewById(R.id.lblNumOrder);
        viewHolder.lblTotalMoney = convertView.findViewById(R.id.lblMoney);
        viewHolder.lblTimeStart = convertView.findViewById(R.id.lblTimeStart);
        viewHolder.btnFindWay = convertView.findViewById(R.id.btnFindWay);
        ServiceRequestDTO row_pos = Data.get(position);
        viewHolder.initData(row_pos);
        convertView.setTag(viewHolder);
        return convertView;
    }

}
