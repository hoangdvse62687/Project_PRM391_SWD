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
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Fragment.GoogleMapApiFragment;
import com.example.dell.booking_cyber.Model.CybercoreManager;
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
    private List<ServiceRequestDetailDTO> Data = new ArrayList<>();
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

    public void addItem(final ServiceRequestDetailDTO item) {
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

        public void initData(ServiceRequestDetailDTO detail, final int position){
            try{
                this.imgCyber.setImageBitmap(LocaleData.loadBitmap("https://image.ibb.co/eZasF0/icon-shop-detail.jpg"));
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                this.lblShopName.setText(detail.getCyberGamingName());
                this.ratingShop.setRating(3);
                this.lblQuantityOrder.setText(detail.getNumberOfServiceSlot().toString());
                this.lblTotalMoney.setText(detail.getTotalPrice().toString());
                this.lblTimeStart.setText(df.format(LocaleData.addHours(detail.getGoingDate(),-LocaleData.TIME_ZONE)));
                this.btnFindWay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<CyberGamingDTO> listCyber = new ArrayList<>();
                        CybercoreManager cybercoreManager = new CybercoreManager();
                        listCyber = cybercoreManager.getAllCybercore();
                        for (CyberGamingDTO item:
                             listCyber) {
                            if(item.getName().equals(Data.get(position).getCyberGamingName())){
                                if(item.getLogitude() == null || item.getLatitude() == null)
                                    return;
                                String origin = "10.8530,106.6296";
                                String destination = item.getLatitude().toString() + "," + item.getLogitude().toString();
                                fragment.findDirection(origin,destination);
                                return;
                            }
                        }
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
        ServiceRequestDetailDTO row_pos = Data.get(position);
        viewHolder.initData(row_pos,position);
        convertView.setTag(viewHolder);
        return convertView;
    }

}
