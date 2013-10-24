package com.supdo.volumecontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;  
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SilenceWidgetProvider extends AppWidgetProvider {
	//private RemoteViews remoteViews;
	private AudioManager mAudioManager;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.silence_widget);
		Intent  silenceIntent = new Intent("com.supdo.volumecontrol.silence");
		PendingIntent silencePending= PendingIntent.getBroadcast(context, 0, silenceIntent, 0);
		remoteViews.setOnClickPendingIntent(R.id.imgSilence, silencePending);
		
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.silence_widget);
		
		if(intent.getAction().equals("com.supdo.volumecontrol.silence")){
			
			int mute = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			if(mute>0){
				mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
				remoteViews.setImageViewResource(R.id.imgSilence, R.drawable.ic_silence_on);
			}else{
				//mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
				remoteViews.setImageViewResource(R.id.imgSilence, R.drawable.ic_silence_off);
			}
		}
		
		//更新插件界面
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context); 
		appWidgetManager.updateAppWidget(new ComponentName(context, SilenceWidgetProvider.class),  remoteViews);  
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
