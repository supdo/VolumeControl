package com.supdo.volumecontrol;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;  
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SilenceWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		Toast.makeText(context, "点击了widget日历", 1).show();
	}

}
