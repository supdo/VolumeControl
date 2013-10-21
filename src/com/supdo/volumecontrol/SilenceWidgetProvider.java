package com.supdo.volumecontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;  
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SilenceWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.silence_widget);
		Intent  silenceIntent = new Intent("com.supdo.volumecontrol.silence");
		PendingIntent silencePending= PendingIntent.getBroadcast(context, 0, silenceIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.btnSilence, silencePending);
		
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if(intent.getAction().equals("com.supdo.volumecontrol.silence")){
			Toast.makeText(context, "点击了widget日历", 1).show();
		}
	}
	
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	

}
