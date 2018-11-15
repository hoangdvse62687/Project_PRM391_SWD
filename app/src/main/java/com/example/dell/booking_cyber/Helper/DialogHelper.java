package com.example.dell.booking_cyber.Helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.concurrent.Callable;

public class DialogHelper {
  // No handle for choosing option
  // Have only 1 option: yes
  public static AlertDialog.Builder createAlertDialogBuilder(
          Context context,
          String message,
          String yesOption
  ) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setMessage(message);

    if (!yesOption.isEmpty()) {
      alertDialogBuilder.setPositiveButton(yesOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
      });
    }
    return alertDialogBuilder;
  }

  // Yes/no option, handle for yes option
  public static AlertDialog.Builder createAlertDialogBuilder(
          Context context,
          String message,
          String yesOption,
          final Callable actionForYes
  ) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setMessage(message);

    if (!yesOption.isEmpty()) {
      alertDialogBuilder.setPositiveButton(yesOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          try {
            actionForYes.call();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
    return alertDialogBuilder;
  }

  // No handle for choosing option
  // Have 2 options: yes & no
  public static AlertDialog.Builder createAlertDialogBuilder(
          Context context,
          String message,
          String yesOption,
          String noOption
  ) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setMessage(message);

    if (!yesOption.isEmpty()) {
      alertDialogBuilder.setPositiveButton(yesOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
      });
    }

    if (!noOption.isEmpty()) {
      alertDialogBuilder.setNegativeButton(noOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
      });
    }
    return alertDialogBuilder;
  }

  // Yes/no option, handle for yes option
  public static AlertDialog.Builder createAlertDialogBuilder(
          Context context,
          String message,
          String yesOption,
          String noOption,
          final Callable actionForYes
  ) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setMessage(message);

    if (!yesOption.isEmpty()) {
      alertDialogBuilder.setPositiveButton(yesOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          try {
            actionForYes.call();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }

    if (!noOption.isEmpty()) {
      alertDialogBuilder.setNegativeButton(noOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
      });
    }
    return alertDialogBuilder;
  }

  // Handle for each choosing options
  public static AlertDialog.Builder createAlertDialogBuilder(
          Context context,
          String message,
          String yesOption,
          String noOption,
          final Callable actionForYes,
          final Callable actionForNo
  ) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    alertDialogBuilder.setMessage(message);

    if (!yesOption.isEmpty()) {
      alertDialogBuilder.setPositiveButton(yesOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          try {
            actionForYes.call();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }

    if (!noOption.isEmpty()) {
      alertDialogBuilder.setNegativeButton(noOption, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          try {
            actionForNo.call();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
    return alertDialogBuilder;
  }
}
