package app.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;

public class CONS_C {
	
	public static class Main {

		////////////////////////////////

		// Common

		////////////////////////////////
	    public static int DIAMETER = 50;
//	    public static final int DIAMETER = 50;

	    // �~�̔��a
	    public static final int RADIUS = DIAMETER / 2;

	    ////////////////////////////////

		// Offsets

		////////////////////////////////
	    // ��ʏ㉺�̃}�[�W��
	    public static final int OFFSET_Y = 100;

	    // ��ʍ��E�̃}�[�W��
	    public static final int OFFSET_X = 0;

	    // ���ƕ`��I�u�W�F�N�g�p�z��
	    public static ShapeDrawable[] drawables;

	    ////////////////////////////////

		// Balls

		////////////////////////////////
	    // �ԋʂ��󂯂錊�̔z��ԍ�
	    public static final int RED_OVAL = 0;

	    // �΋ʂ��󂯂錊�̔z��ԍ�
	    public static final int GREEN_OVAL = 1;

	    // �ʂ��󂯂錊�̔z��ԍ�
	    public static final int BLUE_OVAL = 2;

	    // ���ʂ��󂯂錊�̔z��ԍ�
	    public static final int WHITE_OVAL = 3;

	    // �{�[���̌��݂̐F
	    public static int currentColor;

	    // �{�[���p�O���f�[�V������`
	    public static final RadialGradient RADIAL_GRADIENT_BLUE =
	        new RadialGradient(10, 10, RADIUS, Color.CYAN, Color.BLUE,
	            Shader.TileMode.MIRROR);
	    public static final RadialGradient RADIAL_GRADIENT_RED =
	        new RadialGradient(10, 10, RADIUS, Color.YELLOW, Color.RED,
	            Shader.TileMode.MIRROR);
	    public static final RadialGradient RADIAL_GRADIENT_GREEN =
	        new RadialGradient(10, 10, RADIUS, Color.WHITE, Color.GREEN,
	            Shader.TileMode.MIRROR);
	    public static final RadialGradient RADIAL_GRADIENT_WHITE =
	        new RadialGradient(10, 10, RADIUS, Color.WHITE, Color.DKGRAY,
	            Shader.TileMode.MIRROR);

	    ////////////////////////////////

		// test

		////////////////////////////////
	    public static final int NUM_OF_BALLS				= 4;

	    ////////////////////////////////
	    
	    // Offset: Radial gradient
	    
	    ////////////////////////////////
		public static int RadialGradient_OFFSET_RED_X		= 0;
		public static int RadialGradient_OFFSET_RED_Y		= 0;
		
		public static int RadialGradient_OFFSET_GREEN_X		= 0;
		public static int RadialGradient_OFFSET_GREEN_Y		= 0;
		
		public static int RadialGradient_OFFSET_BLUE_X		= 0;
		public static int RadialGradient_OFFSET_BLUE_Y		= 0;
		
		////////////////////////////////
		
		// Offset: Bounds
		
		////////////////////////////////
		public static int BOUNDS_OFFSET_RED_LEFT			= 0;
		public static int BOUNDS_OFFSET_RED_TOP			= 0;
		
		public static int BOUNDS_OFFSET_GREEN_LEFT			= 0;
		public static int BOUNDS_OFFSET_GREEN_TOP			= 0;
		
		public static int BOUNDS_OFFSET_BLUE_LEFT			= 0;
		public static int BOUNDS_OFFSET_BLUE_TOP			= 0;
		
		////////////////////////////////

		// Bounds

		////////////////////////////////
		public static int BOUNDS_ORIG_RED_LEFT			= 200;
		public static int BOUNDS_ORIG_RED_TOP			= 100;
		
		public static int BOUNDS_ORIG_GREEN_LEFT		= 100;
		public static int BOUNDS_ORIG_GREEN_TOP		= 100;
		
		public static int BOUNDS_ORIG_BLUE_LEFT			= 300;
		public static int BOUNDS_ORIG_BLUE_TOP			= 100;
		
		
	}//public static class Main
	
	public static class 
	Line {

		public static boolean drawLine = false;
		public static boolean drawLines	= false;
		
		public static Paint p_Line;
		public static Paint p_Lines;
		
		public static float x1;
		
		public static float x2;
		
		public static float y1;
		
		public static float y2;
		
		public static float[] pnt_Lines;

		
	}//Line

	public static class 
	Pref {
		
		public static final String pnake_Canvas = "pnake_Canvas";
		
		public static final String pkey_Canvas_OpID = "pkey_Canvas_OpID";
		
		public static final int OPID_DRAW_LINE		= 1;
		
		public static final int OPID_DRAW_LINES		= 2;
		
	}//Pref
	
}//public class CONS
