package com.example.dell.booking_cyber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.booking_cyber.Model.CommentDetail;
import com.example.dell.booking_cyber.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentCyberViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;

    private List<CommentDetail> Data = new ArrayList<>();
    public CommentCyberViewAdapter(Context context) {
        this.context = context;
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

    public void addItem(final CommentDetail item) {
        Data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return Data.indexOf(getItem(position));
    }

    private class ViewHolder{
        public ImageView imgUserComment;
        public TextView lblUsername;
        public RatingBar rbtUser;
        public TextView lblTimeComment;
        public TextView lblDescription;

        public void initData(CommentDetail detail){
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            //this.imgUserComment.setImageResource();
            this.lblUsername.setText(detail.getUser().getUsername());
            this.rbtUser.setRating(detail.getRating());
            this.lblTimeComment.setText(df.format(detail.getTimeComment()));
            this.lblDescription.setText(detail.getDescription());
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder();
        convertView = mInflater.inflate(R.layout.comment_cyber_component, null);
        viewHolder.lblUsername = convertView.findViewById(R.id.lblUsername);
        viewHolder.rbtUser = convertView.findViewById(R.id.rbtUser);
        viewHolder.lblTimeComment = convertView.findViewById(R.id.lblTimeComment);
        viewHolder.lblDescription = convertView.findViewById(R.id.lblDescription);
        CommentDetail row_pos = Data.get(position);
        viewHolder.initData(row_pos);
        convertView.setTag(viewHolder);
        return convertView;
    }
}
