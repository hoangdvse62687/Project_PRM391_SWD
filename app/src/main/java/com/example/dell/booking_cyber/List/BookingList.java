package com.example.dell.booking_cyber.List;

import com.example.dell.booking_cyber.DTO.Booking;

import java.util.ArrayList;

public class BookingList {
  public static ArrayList<Booking> bookingList = new ArrayList<>();

  public static void addToList() {
    for (int i = 0; i < 13; i++ ) {
      bookingList.add(new Booking());
    }
  }
}
