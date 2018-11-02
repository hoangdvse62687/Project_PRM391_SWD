package com.example.dell.booking_cyber.Fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.dell.booking_cyber.Adapter.CommentCyberViewAdapter;
import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.CommentDetail;
import com.example.dell.booking_cyber.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class CyberCommentFragment extends Fragment {
    AccountManager accountManager;
    Integer shopId;

    ListView listView;
    RelativeLayout formComment;
    public CyberCommentFragment() {
        // Required empty public constructor
    }
    public static final CyberCommentFragment newInstance(int shopId)
    {
        CyberCommentFragment fragment = new CyberCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", shopId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        shopId = getArguments().getInt("ID");
        View view = inflater.inflate(R.layout.fragment_cyber_comment, container, false);
        formComment = view.findViewById(R.id.formComment);
        listView = view.findViewById(R.id.BaseListView);
        CommentCyberViewAdapter adapter = new CommentCyberViewAdapter(getActivity());
        listView.setAdapter(adapter);
        //Load data here

        AccountDTO accountDetail = new AccountDTO("user@fpt.edu.vn","Hoang", LocaleData.ROLE_USER,true,false);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        CommentDetail detail = new CommentDetail(1,accountDetail,3,"Đồ ăn quá tệ",cal.getTime());
        CommentDetail detail2 = new CommentDetail(1,accountDetail,4,"Đồ ăn quá tệ2",cal.getTime());
        adapter.addItem(detail);
        adapter.addItem(detail2);
        setListViewHeightBasedOnChildren(listView);
        accountManager = new AccountManager(getActivity());
        if(!accountManager.isLogin()){
            formComment.setVisibility(View.GONE);
        }else{
            formComment.setVisibility(View.VISIBLE);
        }
        return view;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
