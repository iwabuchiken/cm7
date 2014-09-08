package app.utils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import app.items.WavFile;
import app.items.WavFileException;

public class Lab {

	public static void 
	lab_WaveFile
	(Activity actv) {
		// TODO Auto-generated method stub
		
		_lab_WaveFile_D_29_V_1_0(actv);
		
//		////////////////////////////////
//
//		// setup
//
//		////////////////////////////////
//		String msg_Log;
//		
//		String dpath_SDCard = Environment.getExternalStorageDirectory().getPath();
//		
//		String fpath = dpath_SDCard + "/tapeatalk_records/14-08-28-20-44-56.wav";
//		
//		////////////////////////////////
//
//		// get file
//
//		////////////////////////////////
//		File f = new File(fpath);
//		
//		// Log
//		msg_Log = "file exists => " + f.exists();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		// Log
//		msg_Log = "sdcard path => " + Environment.getExternalStorageDirectory().getPath() ;
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		////////////////////////////////
//
//		// open
//
//		////////////////////////////////
//		WavFile wavFile = null;
//		
//		try {
//			
//			wavFile = WavFile.openWavFile(f);
//			
//			Lab._lab_WaveFile__ShowData(actv, wavFile);
//			
////			Lab.read_Wave(actv, wavFile);
//			
//			wavFile.close();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (WavFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
	}//lab_WaveFile

	private static void 
	_lab_WaveFile_D_29_V_1_0
	(Activity actv) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// setup

		////////////////////////////////
		String msg_Log;
		
		String dpath_SDCard = Environment.getExternalStorageDirectory().getPath();
		
		String fpath = dpath_SDCard + "/tapeatalk_records/14-08-28-20-44-56.wav";
		
		////////////////////////////////

		// get file

		////////////////////////////////
		File f = new File(fpath);
		
		// Log
		msg_Log = "file exists => " + f.exists();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// Log
		msg_Log = "sdcard path => " + Environment.getExternalStorageDirectory().getPath() ;
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		////////////////////////////////

		// open

		////////////////////////////////
		WavFile wavFile = null;
		
		try {
			
			wavFile = WavFile.openWavFile(f);
			
			Lab._lab_WaveFile__ShowData(actv, wavFile);
			
//			Lab.read_Wave(actv, wavFile);
			
			wavFile.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WavFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//_lab_WaveFile_D_29_V_1_0

	private static void 
	read_Wave
	(Activity actv, WavFile wavFile) {
		// TODO Auto-generated method stub
		
		
		
		
		
	}//read_Wave

	private static void 
	_lab_WaveFile__ShowData
	(Activity actv, WavFile wavFile) {
		// TODO Auto-generated method stub
		String msg_Log;
		
		// Log
		msg_Log = "File name => " + wavFile.get_FileName();
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		msg_Log = String.format(
						Locale.JAPAN,
						"channels ==> %d / Frames => %d", 
						wavFile.getNumChannels(),
						wavFile.getNumFrames()
						);
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);

		
		
//		msg_Log = "getSampleRate => " + wavFile.getSampleRate();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
//		msg_Log = "getNumChannels => " + wavFile.getNumChannels();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//
//		msg_Log = "getValidBits => " + wavFile.getValidBits();
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", msg_Log);
//		
		
		
	}//_lab_WaveFile__ShowData


	
}
