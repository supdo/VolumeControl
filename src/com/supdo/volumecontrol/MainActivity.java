package com.supdo.volumecontrol;

import java.util.HashMap;
import java.util.Map;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	private SeekBar sbCalling;
	private SeekBar sbCalled;
	private SeekBar sbChat;
	private SeekBar sbAlarms;
	private SeekBar sbMedia;
	private SeekBar sbSystem;
	
	private Display defaultSize;
	private AudioManager mAudioManager;
	
	private Layout layoutDlg;
	private View dlgView;
	private Dialog infoDlg;
	private Window dialogWindow;
	private TextView tvVolumeName;
	private TextView tvVolumeSize;
	private Map titleMap;
	private Map topMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViews();
		initialActivity();
		setListeners();
	}

	private void findViews() {
		sbCalling = (SeekBar)findViewById(R.id.sbCalling);
		sbCalled = (SeekBar)findViewById(R.id.sbCalled);
		sbChat = (SeekBar)findViewById(R.id.sbChat);
		sbAlarms = (SeekBar)findViewById(R.id.sbAlarms);
		sbMedia = (SeekBar)findViewById(R.id.sbMedia);
		sbSystem = (SeekBar)findViewById(R.id.sbSystem);
	}
	
	private void initialActivity() {
		initialMap();
		WindowManager wm = getWindowManager();
		defaultSize = wm.getDefaultDisplay(); //为获取屏幕宽、高 
		
		dlgView = LayoutInflater.from(this).inflate(R.layout.dialog_info, null);
		tvVolumeName = (TextView)dlgView.findViewById(R.id.tvVolumeName);
		tvVolumeSize = (TextView)dlgView.findViewById(R.id.tvVolumeSize);
		//int dlgHeight = tvVolumeName.getLayoutParams().height + tvVolumeSize.getLayoutParams().height + 20;
		infoDlg = new Dialog(MainActivity.this, R.style.infoDialog);
		infoDlg.setContentView(dlgView);
		dialogWindow = infoDlg.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //lp.x = 0; // 新位置X坐标
        lp.y = 5; // 新位置Y坐标
        lp.width = Integer.valueOf((int) (defaultSize.getWidth() * 0.6) ); // 宽度

        //lp.height = dlgHeight; // 高度
        lp.alpha = 0.8f; // 透明度
        dialogWindow.setAttributes(lp);
		
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//通话
		int callingMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
		int callingCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
		sbCalling.setMax(callingMax);
		sbCalling.setProgress(callingCurrent);
		//铃声
		int calledMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
		int calledCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_RING);
		sbCalled.setMax(calledMax);
		sbCalled.setProgress(calledCurrent);
		//信息
		int chatMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION );
		int chatCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION );
		sbChat.setMax(chatMax);
		sbChat.setProgress(chatCurrent);
		//闹钟
		int alarmsMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		int alarmsCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM);
		sbAlarms.setMax(alarmsMax);
		sbAlarms.setProgress(alarmsCurrent);
		//媒体
		int mediaMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int mediaCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		sbMedia.setMax(mediaMax);
		sbMedia.setProgress(mediaCurrent);
		//系统
		int systemMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
		int systemCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
		sbSystem.setMax(systemMax);
		sbSystem.setProgress(systemCurrent);
		
	}
	
	private void initialMap(){
		titleMap= new HashMap();
		topMap= new HashMap();
		//通话
		titleMap.put(R.id.sbCalling, "通话音量");
		topMap.put(R.id.sbCalling, 5);
		//铃声
		titleMap.put(R.id.sbCalled, "铃声音量");
		topMap.put(R.id.sbCalled, 80);
		//信息
		titleMap.put(R.id.sbChat, "短信音量");
		topMap.put(R.id.sbChat, 164);
		//闹钟
		titleMap.put(R.id.sbAlarms, "闹钟音量");
		topMap.put(R.id.sbAlarms, 248);
		//媒体
		titleMap.put(R.id.sbMedia, "音乐，电影，游戏音量");
		topMap.put(R.id.sbMedia, 332);
		//系统
		titleMap.put(R.id.sbSystem, "系统其它音量");
		topMap.put(R.id.sbSystem, 416);
	}
	
	private void setListeners() {
		sbCalling.setOnSeekBarChangeListener(sbCallingListener);
		sbCalled.setOnSeekBarChangeListener(sbCalledListener);
		sbChat.setOnSeekBarChangeListener(sbChatListener);
		sbAlarms.setOnSeekBarChangeListener(sbAlarmsListener);
		sbMedia.setOnSeekBarChangeListener(sbMediaListener);
		sbSystem.setOnSeekBarChangeListener(sbSystemListener);
	}
	
	private void setDialogInfo(SeekBar sb){
        int[] location = new  int[2] ;
        sb.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        //sb.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标

        tvVolumeName.setText((String)titleMap.get(sb.getId()));
		String strSize = sb.getProgress() + " / " + sb.getMax();
		tvVolumeSize.setText(strSize);
		
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.y = defaultSize.getHeight() - location[1] + 10;
		dialogWindow.setAttributes(lp);
	}
	
	private  OnSeekBarChangeListener  sbCallingListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress, 1);
			setDialogInfo(seekBar);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			setDialogInfo(seekBar);
			infoDlg.show();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			infoDlg.cancel();
		}
	};
	
	private  OnSeekBarChangeListener  sbCalledListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_RING, progress, AudioManager.FLAG_ALLOW_RINGER_MODES);
			setDialogInfo(seekBar);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			setDialogInfo(seekBar);
			infoDlg.show();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			infoDlg.cancel();
		}
	};
	
	private  OnSeekBarChangeListener  sbChatListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress, AudioManager.FLAG_PLAY_SOUND);
			setDialogInfo(seekBar);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			setDialogInfo(seekBar);
			infoDlg.show();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			infoDlg.cancel();
		}
	};
	
	private  OnSeekBarChangeListener  sbAlarmsListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, progress, 1);
			setDialogInfo(seekBar);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			setDialogInfo(seekBar);
			infoDlg.show();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			infoDlg.cancel();
		}
	};
	
	private  OnSeekBarChangeListener  sbMediaListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 1);
			setDialogInfo(seekBar);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			setDialogInfo(seekBar);
			infoDlg.show();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			infoDlg.cancel();
		}
	};
	
	private  OnSeekBarChangeListener  sbSystemListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, progress, 1);
			setDialogInfo(seekBar);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			setDialogInfo(seekBar);
			infoDlg.show();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			infoDlg.cancel();
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
