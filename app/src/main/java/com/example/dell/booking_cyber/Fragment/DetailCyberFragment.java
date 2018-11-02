package com.example.dell.booking_cyber.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.booking_cyber.Adapter.GalleryViewPagerAdapter;
import com.example.dell.booking_cyber.DTO.ConfigurationDTO;
import com.example.dell.booking_cyber.MainActivity;
import com.example.dell.booking_cyber.Model.ConfigurationManager;
import com.example.dell.booking_cyber.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCyberFragment extends Fragment {
    TextView txtCPU;
    TextView txtGPU;
    TextView txtRAM;
    TextView txtKeyboard;
    TextView txtMouse;
    TextView txtScreen;
    TextView txtAdditionInfo;
    ViewPager GalleryViewPager;
    Spinner spnConfiguration;

    ConfigurationManager configurationManager = new ConfigurationManager();
    List<ConfigurationDTO> configurationDTOs;
    Integer cyberId;

    public DetailCyberFragment() {
        // Required empty public constructor
    }
    public static final DetailCyberFragment newInstance(int shopId)
    {
        DetailCyberFragment fragment = new DetailCyberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", shopId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_cyber, container, false);
        cyberId = getArguments().getInt("ID");
        txtCPU = view.findViewById(R.id.txtCPU);
        txtGPU = view.findViewById(R.id.txtGPU);
        txtRAM = view.findViewById(R.id.txtRAM);
        txtKeyboard = view.findViewById(R.id.txtKeyboard);
        txtMouse = view.findViewById(R.id.txtMouse);
        txtScreen = view.findViewById(R.id.txtScreen);
        txtAdditionInfo = view.findViewById(R.id.txtAdditionInfo);
        GalleryViewPager = view.findViewById(R.id.GalleryViewPager);
        spnConfiguration = view.findViewById(R.id.spnConfiguration);
        HandleData();
        return view;
    }

    private void getData(){
        configurationDTOs = configurationManager.getConfigurationByCyberId(cyberId);
    }

    private void HandleData(){
        getData();
        if(configurationDTOs.size() != 0){
            renderView(configurationDTOs.get(0));
            ArrayList<String> resource = new ArrayList<>();
            for (ConfigurationDTO item:
                 configurationDTOs) {
                resource.add(item.getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item,resource);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnConfiguration.setAdapter(adapter);
            spnConfiguration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Đang tải...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    final int resourceId = position;
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    try{
                                        renderView(configurationDTOs.get(resourceId));
                                    }finally {
                                        progressDialog.dismiss();
                                    }
                                }
                            }
                            ,1000);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void renderView(ConfigurationDTO detail){
        txtCPU.setText(detail.getCpu());
        txtGPU.setText(detail.getGpu());
        txtRAM.setText(detail.getRam());
        txtKeyboard.setText(detail.getKeyboard());
        txtMouse.setText(detail.getMouse());
        txtScreen.setText(detail.getHeadphone());
        txtAdditionInfo.setText("Giá "+detail.getPrice().toString() +"đ"+"/1h");
        ArrayList<Integer> listImgId = new ArrayList<>();
        listImgId.add(R.drawable.ffq_view);
        listImgId.add(R.drawable.ffq_view2);
        listImgId.add(R.drawable.ffq_view3);
        listImgId.add(R.drawable.ffq_view4);
        GalleryViewPagerAdapter viewPagerAdapter = new GalleryViewPagerAdapter(getContext(),listImgId);
        GalleryViewPager.setAdapter(viewPagerAdapter);
    }
}
