package com.canyinghao.canquery.view.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    static PendingIntent pendingIntent;
    static  AlarmManager am;
	@Override
	public void onReceive(Context context, Intent intent) {
        QueryWidgetProvider.updateAllWidgets(context,QueryWidgetProvider_2x.class);
		
		scheduleAlarm(context);
	}

	public static void scheduleAlarm(Context context) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 1);
//		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.add(Calendar.DAY_OF_MONTH, 0);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context,
				AlarmReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		am.set(AlarmManager.RTC,1000, pendingIntent);
		am.setRepeating(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis(), 6*60*60*1000, pendingIntent);

	}

    public static void cancelALarm(){

        if (am!=null&&pendingIntent!=null){



            am.cancel(pendingIntent);
        }


    }

}
