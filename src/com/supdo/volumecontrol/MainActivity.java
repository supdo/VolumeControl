package com.supdo.volumecontrol;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {
	
	private SeekBar sbCalling;
	
	private AudioManager mAudioManager;


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
		
	}
	
	private void initialActivity() {
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//通话
		int callingMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
		int callingCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
		sbCalling.setMax(callingMax);
		sbCalling.setProgress(callingCurrent);
		
	}
	
	private void setListeners() {
		sbCalling.setOnSeekBarChangeListener(sbCallingListener);
		
	}
	
	private  OnSeekBarChangeListener  sbCallingListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}
		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
	};


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
