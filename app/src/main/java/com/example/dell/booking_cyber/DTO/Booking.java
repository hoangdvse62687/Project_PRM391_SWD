package com.example.dell.booking_cyber.DTO;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.dell.booking_cyber.R;

import java.time.LocalDate;
import java.util.Random;

public class Booking {
  private static int count = 0;
  private static int randomNumber;

  private int id;

  // For booking list rednerring
  private String cybercoreName;
  private String bookingDate;
  private boolean isPaid;

  // For booking list detail
  private int cybercoreIcon;
  private String address;
  private int pointEvaluation;
  private int star_01,star_02, star_03, star_04, star_05;
  private int total;
  private int numOfSeat;
  private int hour;
  private int minute;
  private int discount;

  private final int PRICE_PER_HOUR = 12;
  private int price;

  // For evaluation
  private String textEvaluation;

  // Use for random price
  private Random random = new Random();

  // List of available cybercore's name
  private String[] cybercoreNameList = new String[] {
    "Bá Đạo Quán",
    "Thiên Long Quán",
    "Thiên Sứ Quán",
    "Đại Sơn Quán"
  };

  // List of address correspond to cybercore's name
  private String[] addressList = new String[] {
    "71 Tô Ký",
    "32 Trần Hưng Đạo",
    "63 Quang Trung",
    "14 Phan Huy Ích"
  };

  // List of images/icons correspond to cybercore's name
  private int[] cybercoreIconList = new int[] {
    R.drawable.cybercore_icon_01,
    R.drawable.cybercore_icon_02,
    R.drawable.cybercore_icon_03,
    R.drawable.cybercore_icon_04
  };

  private int[] starTypeList = new int[] {
    R.drawable.ic_star,
    R.drawable.ic_star_half,
    R.drawable.ic_star_border,
  };

  // List of good evaluation
  private String[] goodEvaluation = new String[]{
    "Phòng net view đẹp, máy cấu hình cực tốt",
    "Đồ ăn và nước uống ở đây khá đầy đủ",
    "Quán net tách riêng phòng hút thuốc và không hút thuốc. Mình rất thích điều này",
    "Quán nét có thiết kế đẹp, đèn nhìn lung linh ảo diệu, và ngầu"
  };;

  private int[] minuteRange = new int[] {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
  private int[] discountRange = new int[] {10, 20, 30, 40, 50};

  public Booking() {
    count++;
    randomNumber = random.nextInt(cybercoreNameList.length);

    this.id = count;
    this.cybercoreName = cybercoreNameList[randomNumber];
    this.bookingDate = "01/01/2011";
    this.cybercoreIcon = cybercoreIconList[randomNumber];
    this.address = addressList[randomNumber];
    this.isPaid = random.nextBoolean();
    this.pointEvaluation = isPaid ? random.nextInt(5) + 1 : 0;
    this.numOfSeat = random.nextInt(20) + 1;
    this.hour = random.nextInt(5) + 1;
    this.minute = minuteRange[random.nextInt(minuteRange.length)];
    this.discount = discountRange[random.nextInt(discountRange.length)];
    this.total = (hour * PRICE_PER_HOUR + minute * PRICE_PER_HOUR / 60) * numOfSeat;
    this.price = total - total * discount / 100;
    this.textEvaluation = pointEvaluation > 0 ? goodEvaluation[random.nextInt(goodEvaluation.length)] : "";

    // set stars
    switch (pointEvaluation) {
      case 1:
        this.star_01 = starTypeList[0];
        this.star_02 = this.star_03 = this.star_04 = this.star_05 = starTypeList[2];
        break;
      case 2:
        this.star_01 = this.star_02 = starTypeList[0];
        this.star_03 = this.star_04 = this.star_05 = starTypeList[2];
        break;
      case 3:
        this.star_01 = this.star_02 = this.star_03 = starTypeList[0];
        this.star_04 = this.star_05 = starTypeList[2];
        break;
      case 4:
        this.star_01 = this.star_02 = this.star_03 = this.star_04 = starTypeList[0];
        this.star_05 = starTypeList[2];
        break;
      case 5:
        this.star_01 = this.star_02 = this.star_03 = this.star_04 = this.star_05 = starTypeList[0];
        break;
      default:
        this.star_01 = this.star_02 = this.star_03 = this.star_04 = this.star_05 = starTypeList[2];
        break;
    }
  }

  // Generate random number in range
  private int getRandomIntBetween(int start, int end) {
    return start + (int) Math.round(Math.random() * (end - start));
  }

  // Generate random date
  @RequiresApi(api = Build.VERSION_CODES.O)
  private LocalDate getRandomDate(int startYear, int endYear) {
    int day = getRandomIntBetween(1, 28);
    int month = getRandomIntBetween(1, 12);
    int year = getRandomIntBetween(startYear, endYear);
    return LocalDate.of(year, month, day);
  }

  public static int getCount() {
    return count;
  }

  public static void setCount(int count) {
    Booking.count = count;
  }

  public static int getRandomNumber() {
    return randomNumber;
  }

  public static void setRandomNumber(int randomNumber) {
    Booking.randomNumber = randomNumber;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCybercoreName() {
    return cybercoreName;
  }

  public void setCybercoreName(String cybercoreName) {
    this.cybercoreName = cybercoreName;
  }

  public String getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public void setPaid(boolean paid) {
    isPaid = paid;
  }

  public int getCybercoreIcon() {
    return cybercoreIcon;
  }

  public void setCybercoreIcon(int cybercoreIcon) {
    this.cybercoreIcon = cybercoreIcon;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getPointEvaluation() {
    return pointEvaluation;
  }

  public void setPointEvaluation(int pointEvaluation) {
    this.pointEvaluation = pointEvaluation;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public String getTextEvaluation() {
    return textEvaluation;
  }

  public void setTextEvaluation(String textEvaluation) {
    this.textEvaluation = textEvaluation;
  }

  public int getNumOfSeat() {
    return numOfSeat;
  }

  public void setNumOfSeat(int numOfSeat) {
    this.numOfSeat = numOfSeat;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public int getDiscount() {
    return discount;
  }

  public void setDiscount(int discount) {
    this.discount = discount;
  }

  public int getPrice() {
    return price;
  }

  public int getStar_01() {
    return star_01;
  }

  public void setStar_01(int star_01) {
    this.star_01 = star_01;
  }

  public int getStar_02() {
    return star_02;
  }

  public void setStar_02(int star_02) {
    this.star_02 = star_02;
  }

  public int getStar_03() {
    return star_03;
  }

  public void setStar_03(int star_03) {
    this.star_03 = star_03;
  }

  public int getStar_04() {
    return star_04;
  }

  public void setStar_04(int star_04) {
    this.star_04 = star_04;
  }

  public int getStar_05() {
    return star_05;
  }

  public void setStar_05(int star_05) {
    this.star_05 = star_05;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Booking{" +
      "id=" + id +
      ", cybercoreName='" + cybercoreName + '\'' +
      ", bookingDate=" + bookingDate +
      ", isPaid=" + isPaid +
      ", cybercoreIcon=" + cybercoreIcon +
      ", address='" + address + '\'' +
      ", pointEvaluation=" + pointEvaluation +
      ", total=" + total +
      ", textEvaluation='" + textEvaluation + '\'' +
      '}';
  }
}

