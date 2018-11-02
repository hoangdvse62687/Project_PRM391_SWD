package com.example.dell.booking_cyber.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.booking_cyber.R;

import java.util.ArrayList;

public class GalleryViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInFlater;
    private ArrayList<Integer> imgIDs = new ArrayList<>();

    public GalleryViewPagerAdapter(Context context,ArrayList<Integer> imgIDs) {
        this.context = context;
        this.imgIDs = imgIDs;
    }

    @Override
    public int getCount() {
        return imgIDs.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInFlater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInFlater.inflate(R.layout.gallery_view_pager,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageGalleryViewPager);
        imageView.setImageResource(imgIDs.get(position));
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view,0);

        //Set event for view
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Click Banner:"+position,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
