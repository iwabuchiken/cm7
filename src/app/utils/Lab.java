package app.utils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.util.Log;
import app.items.WavFile;
import app.items.WavFileException;

public class Lab {

	public static void 
	lab_WaveFile
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated method stub
		
		////////////////////////////////

		// setup

		////////////////////////////////
		boolean res = Lab.setup(actv);
		
		if (res == false) {
			
			String msg = "Can't create Lab dir";
			Methods_dlg.dlg_ShowMessage_ThirdDialog(actv, msg, d1, d2);
			
			return;
			
		}
		
		_lab_WaveFile_D_29_V_1_1(actv, d1, d2);
//		_lab_WaveFile_D_29_V_1_0(actv);
		
	}//lab_WaveFile

	private static boolean 
	setup
	(Activity actv) {
		// TODO Auto-generated method stub
		
		File dir_Lab = new File(CONS.DB.dPath_Lab);
		
//		boolean res = true;
		boolean res = true;
		
		if (!dir_Lab.exists()) {
			
			res = dir_Lab.mkdirs();
			
			if (res == true) {
				
				// Log
				String msg_Log = "Lab dir => Created: " + CONS.DB.dPath_Lab;
				Log.d("Lab.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			} else {
				
				// Log
				String msg_Log = "Lab dir => Can't create: " + CONS.DB.dPath_Lab;
				Log.e("Lab.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", msg_Log);
				
			}
			
		} else {

			// Log
			String msg_Log = "Lab dir => exists";
			Log.d("Lab.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
		}
		
		return res;
		
	}//setup

	/******************************
		REF http://www.labbookpages.co.uk/audio/javaWavFiles.html#writing
	 * @param d2 
	 * @param d1 
	 ******************************/
	private static void 
	_lab_WaveFile_D_29_V_1_1
	(Activity actv, Dialog d1, Dialog d2) {
		// TODO Auto-generated method stub
		
		String dpath_Lab = CONS.DB.dPath_Lab;
		
		String fpath = dpath_Lab 
//					+ "/Audios"
					+ "/cm7_test_wave_"
					+ Methods.get_TimeLabel(Methods.getMillSeconds_now())
					+ ".wav";
		
		try {
			
			int sampleRate = 44100;    // Samples per second
			double duration = 5.0;     // Seconds

			// Calculate the number of frames required for specified duration
			long numFrames = (long)(duration * sampleRate);

			// Create a wav file with the name specified as the first argument
			WavFile wavFile = WavFile.newWavFile(
						new File(fpath), 
//						new File(args[0]), 
						2, numFrames, 16, sampleRate);

			// Create a buffer of 100 frames
			double[][] buffer = new double[2][100];

			// Initialise a local frame counter
			long frameCounter = 0;

			// Loop until all frames written
			while (frameCounter < numFrames)
			{
			   // Determine how many frames to write, up to a maximum of the buffer size
			   long remaining = wavFile.getFramesRemaining();
			   int toWrite = (remaining > 100) ? 100 : (int) remaining;

			   // Fill the buffer, one tone per channel
			   int s=0;
			   
			   for (s=0 ; s < (toWrite / 2) ; s++, frameCounter++)
			   {
			      buffer[0][s] = Math.sin(2.0 * Math.PI * 200 * frameCounter / sampleRate);
			      buffer[1][s] = Math.sin(2.0 * Math.PI * 300 * frameCounter / sampleRate);
//			      buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
//			      buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
			   }
			   
			   for (; s < toWrite; s++, frameCounter++)
			   {
				   buffer[0][s] = Math.sin(2.0 * Math.PI * 1000 * frameCounter / sampleRate);
				   buffer[1][s] = Math.sin(2.0 * Math.PI * 1000 * frameCounter / sampleRate);
//			      buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
//			      buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
			   }
			   
//			   for (int s=0 ; s<toWrite ; s++, frameCounter++)
//			   {
//				   buffer[0][s] = Math.sin(2.0 * Math.PI * 200 * frameCounter / sampleRate);
//				   buffer[1][s] = Math.sin(2.0 * Math.PI * 300 * frameCounter / sampleRate);
////			      buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
////			      buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
//			   }

			   // Write the buffer
			   wavFile.writeFrames(buffer, toWrite);
			}

			// Close the wavFile
			wavFile.close();
			
			////////////////////////////////

			// report

			////////////////////////////////
			String msg = "Wav file => created: " + wavFile.get_FileName();
			Methods_dlg.dlg_ShowMessage_ThirdDialog(actv, msg, d1, d2);
			
		} catch (Exception e) {
			
			// Log
			String msg_Log = "Exception";
			Log.e("Lab.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", msg_Log);
			
			e.printStackTrace();
			
			String msg = "Wav file => not created";
			Methods_dlg.dlg_ShowMessage_ThirdDialog(actv, msg, d1, d2);
			
//			System.err.println(e);
		}
		
	}//_lab_WaveFile_D_29_V_1_1
	

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
