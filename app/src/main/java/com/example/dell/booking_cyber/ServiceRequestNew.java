package com.example.dell.booking_cyber;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.booking_cyber.Constant.LocaleData;
import com.example.dell.booking_cyber.DTO.ConfigurationDTO;
import com.example.dell.booking_cyber.DTO.CustomerDTO;
import com.example.dell.booking_cyber.DTO.CyberGamingDTO;
import com.example.dell.booking_cyber.DTO.RoomDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDTO;
import com.example.dell.booking_cyber.DTO.ServiceRequestDetailDTO;
import com.example.dell.booking_cyber.Fragment.DatePickerFragment;
import com.example.dell.booking_cyber.Fragment.TimePickerFragment;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.Model.ConfigurationManager;
import com.example.dell.booking_cyber.Model.RoomManager;
import com.example.dell.booking_cyber.Model.ServiceRequestManager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ServiceRequestNew extends AppCompatActivity {
  private ImageView imgCybercoreIcon, imgBack;
  private TextView txtCybercoreName, txtAddress;
  private Spinner spNumOfSeat, spRoom, spConfiguration;
  private EditText edtGoingDate, edtGoingTime, edtDuration;
  private Button btnBooking;

  private AccountManager accountManager;
  private CyberGamingDTO cyberGamingDTO;
  private List<ConfigurationDTO> configurationList;
  private List<RoomDTO> roomList;
  private ServiceRequestDetailDTO serviceRequestDetailDTO;
  private ServiceRequestDTO serviceRequestDTO;

  private int numberOfSeat, roomIndex, configurationIndex;
  private double duration;

  private TextView txtRoomDescription, txtCpu, txtGpu, txtRam, txtMouse, txtKeyboard, txtHeadphone, txtPrice;
  private TextView txtBookingError;

  Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_request_new);

    intent = getIntent();

    getCurrentUser();
    identifyElements();
//    setElementsShowAndDisplay();
    setElementsValueFromCreateNewService();
    setOnClickListener();
  }

  private void getCurrentUser() {
    accountManager = new AccountManager(ServiceRequestNew.this);
    accountManager.getAccount();
  }

  private void identifyElements() {
    imgCybercoreIcon = findViewById(R.id.imgCybercoreIcon);
    txtCybercoreName = findViewById(R.id.txtCybercoreName);
    txtAddress = findViewById(R.id.txtAddress);
    spNumOfSeat = findViewById(R.id.spNumOfSeat);
    spRoom = findViewById(R.id.spRoom);
    spConfiguration = findViewById(R.id.spConfiguration);
    edtGoingDate = findViewById(R.id.edtGoingDate);
    edtGoingTime = findViewById(R.id.edtGoingTime);
    edtDuration = findViewById(R.id.edtDuration);
    btnBooking = findViewById(R.id.btnBooking);
    txtRoomDescription = findViewById(R.id.txtRoomDescription);
    txtCpu = findViewById(R.id.txtCpu);
    txtGpu = findViewById(R.id.txtGpu);
    txtRam = findViewById(R.id.txtRam);
    txtMouse = findViewById(R.id.txtMouse);
    txtKeyboard = findViewById(R.id.txtKeyboard);
    txtHeadphone = findViewById(R.id.txtHeadphone);
    txtPrice = findViewById(R.id.txtPrice);
    imgBack = findViewById(R.id.imgBack);
    txtBookingError = findViewById(R.id.txtBookingError);
  }

  private void setOnClickListener() {
    onCloseListener();
    onGoingDateClick();
    onGoingTimeClick();
    onDurationClick();
    onDurationChanged();
    onNumberOfSeatSelected();
    onRoomSelected();
    onConfigurationSelected();
    onBookingClick();
  }

  private void onGoingTimeClick() {
    edtGoingTime.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showTimePickerDialog(LocaleData.GOING_TIME);
      }
    });
  }

  private void onDurationClick() {
    edtDuration.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showTimePickerDialog(LocaleData.DURATION);
      }
    });
  }

  private void showTimePickerDialog(String editText) {
    Bundle bundle = new Bundle();
    bundle.putString("editText", editText);

    DialogFragment dialogFragment = new TimePickerFragment();
    dialogFragment.setArguments(bundle);
    dialogFragment.show(getSupportFragmentManager(), "Chọn giờ");
  }

  private void onGoingDateClick() {
    edtGoingDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDatePickerDialog(LocaleData.GOING_DATE);
      }
    });
  }

  private void showDatePickerDialog(String editText) {
    Bundle bundle = new Bundle();
    bundle.putString("editText", editText);

    DialogFragment dialogFragment = new DatePickerFragment();
    dialogFragment.setArguments(bundle);

    dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);

    dialogFragment.show(getSupportFragmentManager(), "Chọn ngày");
  }

  // Used when customer want to edit the going or confirmed service request
  private void setElementsValueFromEdit() {

  }

  // Used when customer want to request new service
  private void setElementsValueFromCreateNewService() {
    cyberGamingDTO = (CyberGamingDTO) intent.getSerializableExtra("cyberGamingDTO");

    // Set icon, name & address of cybergaming
    imgCybercoreIcon.setImageBitmap(LocaleData.getImageFromUrl(cyberGamingDTO.getLogo()));
    txtCybercoreName.setText(cyberGamingDTO.getName());
    txtAddress.setText(cyberGamingDTO.getAddress());

    // Get configuration option
    ConfigurationManager configurationManager = new ConfigurationManager();
    configurationList = configurationManager.getConfigurationByCyberId(cyberGamingDTO.getId());

    String[] configurationName = new String[configurationList.size()];
    for (int i = 0; i < configurationList.size(); i++) {
      configurationName[i] = configurationList.get(i).getName();
    }

    ArrayAdapter<String> configurationAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, configurationName);
    spConfiguration.setAdapter(configurationAdapter);

    // Get room option
    RoomManager roomManager = new RoomManager();
    roomList = roomManager.getRoomsByCyberId(cyberGamingDTO.getId());

    String[] roomName = new String[roomList.size()];
    for (int i = 0; i < roomList.size(); i++) {
      roomName[i] = roomList.get(i).getName();
    }

    ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, roomName);
    spRoom.setAdapter(roomAdapter);

    txtBookingError.setText("");
  }

  private void computePrice(int position) {
    if (position > -1) {
      double price = (roomList.get(roomIndex).getPrice()
                      + configurationList.get(configurationIndex).getPrice()
                      + duration * LocaleData.PRICE_PER_MINUTE
      ) * numberOfSeat;
      String strPrice = NumberFormat.getCurrencyInstance(new Locale(LocaleData.VIETNAMESE_LANGUAGE, LocaleData.VIETNAMESE_COUNTRY))
              .format(price).replace("US$", "");
      txtPrice.setText(strPrice);
    } else {
      txtPrice.setText(String.valueOf(LocaleData.NO_OPTION));
    }
  }

  private void computeDuration() {
    if (edtDuration.getText().toString().isEmpty()) {
      duration = 0;
    } else {
      String[] parts = edtDuration.getText().toString().split(LocaleData.HOUR);
      String strhour = parts[0];
      String strMinute = parts[1].split(LocaleData.MINUTE)[0].replace(" ", "");
      duration = Integer.parseInt(strhour) * 60 + Integer.parseInt(strMinute);
      computePrice(1);
    }
  }

  private void onCloseListener() {
    this.imgBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void onNumberOfSeatSelected() {
    spNumOfSeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numberOfSeat = position + 1;
        computePrice(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        numberOfSeat = 0;
        computePrice(-1);
      }
    });
  }

  private void onRoomSelected() {
    spRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roomIndex = position;
        txtRoomDescription.setText(roomList.get((position)).getDescription());
        computePrice(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        roomIndex = -1;
        txtRoomDescription.setText(LocaleData.NO_OPTION);
        computePrice(-1);
      }
    });
  }

  private void onConfigurationSelected() {
    spConfiguration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        configurationIndex = position;
        setConfigurationFields(position);
        computePrice(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        configurationIndex = -1;
        setConfigurationFields(-1);
        computePrice(-1);
      }
    });
  }

  private void setConfigurationFields(int position) {
    if (position > -1) {
      txtCpu.setText(configurationList.get(position).getCpu());
      txtGpu.setText(configurationList.get(position).getGpu());
      txtRam.setText(configurationList.get(position).getRam());
      txtMouse.setText(configurationList.get(position).getMouse());
      txtKeyboard.setText(configurationList.get(position).getKeyboard());
      txtHeadphone.setText(configurationList.get(position).getHeadphone());
    } else {
      txtCpu.setText(LocaleData.NO_OPTION);
      txtGpu.setText(LocaleData.NO_OPTION);
      txtRam.setText(LocaleData.NO_OPTION);
      txtMouse.setText(LocaleData.NO_OPTION);
      txtKeyboard.setText(LocaleData.NO_OPTION);
      txtHeadphone.setText(LocaleData.NO_OPTION);
    }
  }

  private void onDurationChanged() {
    edtDuration.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        computeDuration();
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  private void onBookingClick() {
    btnBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (validateFields()) {
          try {
            CustomerDTO customerDTO = accountManager.customerDTO;
            serviceRequestDTO = new ServiceRequestDTO(
                    0,
                    customerDTO.getId(),
                    cyberGamingDTO.getId(),
                    duration,
                    numberOfSeat,
                    false,
                    false,
                    null,
                    new Date(),
                    getGoingDate(),
                    "",
                    0,
                    0.0,
                    0.0,
                    "QR code",
                    Double.parseDouble(
                            txtPrice.getText()
                                    .toString()
                                    .replace(".", "")
                                    .replace(",00", "")
                                    .replaceAll("\\s+", "")),
                    roomList.get(roomIndex).getId(),
                    configurationList.get(configurationIndex).getId(),
                    false,
                    true,
                    false
            );

            System.out.println("Going date: " + serviceRequestDTO.getGoingDate());

//             Call api to add new service request
            ServiceRequestManager serviceRequestManager = new ServiceRequestManager();
            if (serviceRequestManager.createServiceRequest(serviceRequestDTO)) {
              // Return back
              int green = ContextCompat.getColor(ServiceRequestNew.this, R.color.green);
              txtBookingError.setTextColor(green);
              txtBookingError.setText(LocaleData.BOOKING_SUCCESS);
              finish();
            } else {
              txtBookingError.setText(LocaleData.BOOKING_ERROR);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  private Date getGoingDate() {
    Date goingTime = null;
    try {
      // Get day, mont, year
      String[] strGoingDate = edtGoingDate.getText().toString().split("/");
      String day = strGoingDate[0];
      String month = strGoingDate[1];
      String year = strGoingDate[2];

      // Get hour, minute
      String[] parts = edtGoingTime.getText().toString().split(LocaleData.HOUR);
      int intHour = Integer.parseInt(parts[0]);
      if (intHour >= 7) {
        intHour -= LocaleData.TIME_ZONE;
      } else {
        intHour += (24 - LocaleData.TIME_ZONE);
      }
      String hour = String.valueOf(intHour);
      String minute = parts[1].split(LocaleData.MINUTE)[0].replace(" ", "");

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(LocaleData.DATE_FORMAT);
      goingTime = simpleDateFormat.parse(year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":00.000000");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return goingTime;
    }
  }

  private boolean validateFields() {
    boolean check = true;
    if (
        edtGoingDate.getText().toString().isEmpty()
        || edtGoingTime.getText().toString().isEmpty()
        || edtDuration.getText().toString().isEmpty()
    ) {
      txtBookingError.setText(LocaleData.FIELD_EMPTY);
      check = false;
    } else {
      txtBookingError.setText("");
    }
    return check;
  }

//   private class AddAsyn extends AsyncTask<String, Void, Void>{
//
//     @Override
//     protected Void doInBackground(String... strings) {
//       return null;
//     }
//   }
}


