package app.views;

import java.util.ArrayList;

import app.utils.CONS_C;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

// �L�����o�X�r���[�N���X
public class CanvasView_4 extends View {
	// �`�悷��_�i�[�p���X�g
	private ArrayList<Point> points;
	
	// �{�[���p�O���f�[�V������`
	private static final RadialGradient RADIAL_GRADIENT_BLUE =
			new RadialGradient(10, 10, CONS_C.Main.RADIUS, Color.CYAN, Color.BLUE,
					Shader.TileMode.MIRROR);
	private static final RadialGradient RADIAL_GRADIENT_RED =
			new RadialGradient(10, 10, CONS_C.Main.RADIUS, Color.YELLOW, Color.RED,
					Shader.TileMode.MIRROR);
	private static final RadialGradient RADIAL_GRADIENT_GREEN =
			new RadialGradient(10, 10, CONS_C.Main.RADIUS, Color.WHITE, Color.GREEN,
					Shader.TileMode.MIRROR);
	private static final RadialGradient RADIAL_GRADIENT_WHITE =
			new RadialGradient(10, 10, CONS_C.Main.RADIUS, Color.WHITE, Color.DKGRAY,
					Shader.TileMode.MIRROR);
	
	////////////////////////////////
	
	// test
	
	////////////////////////////////
	private int RadialGradient_OFFSET_RED_X		= 0;
	private int RadialGradient_OFFSET_RED_Y		= 0;
	
	private int Bounds_OFFSET_RED_LEFT			= 0;
	private int Bounds_OFFSET_RED_TOP			= 0;
	
	// �R���X�g���N�^
	public CanvasView_4(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);

		_Setup_Drawables();
		
	}

	private void 
	_Setup_Drawables() {
		// TODO Auto-generated method stub
        // ShapeDrawable�̃C���X�^���X����
        CONS_C.Main.drawables = new ShapeDrawable[CONS_C.Main.NUM_OF_BALLS];
//        drawables = new ShapeDrawable[NUM_OF_BALLS];

        // ���̕`��
        CONS_C.Main.drawables[CONS_C.Main.RED_OVAL] = new ShapeDrawable(new OvalShape());
//        CONS_Canvas.Main.drawables[RED_OVAL] = new ShapeDrawable(new OvalShape());
        CONS_C.Main.drawables[CONS_C.Main.GREEN_OVAL] = new ShapeDrawable(new OvalShape());
        CONS_C.Main.drawables[CONS_C.Main.BLUE_OVAL] = new ShapeDrawable(new OvalShape());
        CONS_C.Main.drawables[CONS_C.Main.WHITE_OVAL] = new ShapeDrawable(new OvalShape());

        // ���̐F�̐ݒ�
        CONS_C.Main.drawables[CONS_C.Main.RED_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.RED,
                Shader.TileMode.MIRROR));
        CONS_C.Main.drawables[CONS_C.Main.GREEN_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.GREEN,
                Shader.TileMode.MIRROR));
        CONS_C.Main.drawables[CONS_C.Main.BLUE_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.BLUE,
                Shader.TileMode.MIRROR));
        CONS_C.Main.drawables[CONS_C.Main.WHITE_OVAL].getPaint().setShader(
            new RadialGradient(25, 25, 20, Color.BLACK, Color.WHITE,
                Shader.TileMode.MIRROR));
        
	}//_Setup_Drawables()

	// onMeasure���\�b�h(�r���[�̃T�C�Y�ݒ菈��)
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		
		// Log
		String msg_Log = "Dimension => set";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}

	// onDraw���\�b�h(�`�揈��)
	@Override
	protected void 
	onDraw
	(Canvas canvas) {
		
		// Log
		String msg_Log = "onDraw";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		// �L�����o�X�̔w�i�𔒂ɐݒ�
//		canvas.drawColor(Color.WHITE);
		
		////////////////////////////////

		// DrawableView

		// 4�F�̌�����ʂɕ\��
//        drawColorHole(canvas, 100, 100, 50, 50);
        
        ////////////////////////////////

		// draw line

		////////////////////////////////
        if (CONS_C.Line.drawLine == true) {
			
        	canvas.drawLine(
        			CONS_C.Line.x1, CONS_C.Line.y1, 
        			CONS_C.Line.x2, CONS_C.Line.y2, 
        			CONS_C.Line.p_Line);
        	
        	
        	CONS_C.Line.drawLine = false;
        	
		}

        ////////////////////////////////
        
        // draw lines
        
        ////////////////////////////////
        if (CONS_C.Line.drawLines == true) {
        	
        	canvas.drawLines(
        			CONS_C.Line.pnt_Lines, 
        			CONS_C.Line.p_Lines);
        	
        	CONS_C.Line.drawLines = false;
        	
        }
        
        // Log
		int canvas_Height = canvas.getHeight();
		int canvas_Width = canvas.getWidth();
		
		// Log
		msg_Log = String.format("h = %d, w = %d", canvas_Height, canvas_Width);
//		String msg_Log = "height => " + canvas_Height;
		Log.d("CanvasActv.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
        
        
	}//onDraw

	public void _go() {
		
		// Log
		String msg_Log = "_onDraw_DrawLine => started";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//        drawables[RED_OVAL].setBounds(200, 200, 250, 250);
//        drawables[RED_OVAL].draw(this.c);

		CONS_C.Main.DIAMETER += 10;
//		CONS_Canvas.Main.DIAMETER = 80;
		
		CONS_C.Main.RadialGradient_OFFSET_RED_X += 10;
		this.RadialGradient_OFFSET_RED_Y += 10;

		_go__BoundsOffset();
//		CONS_Canvas.Main.BOUNDS_OFFSET_RED_LEFT		+= 10;
//		CONS_Canvas.Main.BOUNDS_OFFSET_RED_TOP		+= 10;
//		this.Bounds_OFFSET_RED_LEFT		+= 10;
//		this.Bounds_OFFSET_RED_TOP		+= 10;
		
        // Log
		msg_Log = "_onDraw_DrawLine => done";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		this.invalidate();
		
	}
	
	private void _go__BoundsOffset() {
		// TODO Auto-generated method stub
		
		CONS_C.Main.BOUNDS_OFFSET_RED_LEFT		+= 10;
		CONS_C.Main.BOUNDS_OFFSET_RED_TOP		+= 10;

		CONS_C.Main.BOUNDS_OFFSET_GREEN_LEFT		+= 10;
		CONS_C.Main.BOUNDS_OFFSET_GREEN_TOP		+= 10;
		
	}
	
	private void _clear__BoundsOffset_() {
		// TODO Auto-generated method stub
		
		CONS_C.Main.BOUNDS_OFFSET_RED_LEFT		-= 10;
		CONS_C.Main.BOUNDS_OFFSET_RED_TOP		-= 10;
		
		CONS_C.Main.BOUNDS_OFFSET_GREEN_LEFT		-= 10;
		CONS_C.Main.BOUNDS_OFFSET_GREEN_TOP		-= 10;
		
	}

	public void _clear() {
		
		// Log
		String msg_Log = "_clear => started";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
//        drawables[RED_OVAL].setBounds(200, 200, 250, 250);
//        drawables[RED_OVAL].draw(this.c);
		
		CONS_C.Main.DIAMETER -= 10;
//		CONS_Canvas.Main.DIAMETER = 80;

		CONS_C.Main.RadialGradient_OFFSET_RED_X -= 10;
		this.RadialGradient_OFFSET_RED_Y -= 10;

		this._clear__BoundsOffset_();
		
//		CONS_Canvas.Main.BOUNDS_OFFSET_RED_LEFT		-= 10;
//		CONS_Canvas.Main.BOUNDS_OFFSET_RED_TOP		-= 10;
//		this.Bounds_OFFSET_RED_LEFT		-= 10;
//		this.Bounds_OFFSET_RED_TOP		-= 10;
		
		// Log
		msg_Log = "_clear => done";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
		this.invalidate();
		
	}
	
	// clearDrawList���\�b�h(�N���A����)
	public void clearDrawList() {
		points.clear();
		this.invalidate();
	}


	////////////////////////////////

	// DrawableView.java

	private void 
    drawColorHole
    (Canvas canvas, 
    	int width, int height, int cX, int cY) {

            // ��ʏ�[�ɐԋʂ��󂯂錊

            // �n�_(����)�̍��W
            int left = cX;
            int top = CONS_C.Main.OFFSET_Y;

            // �I�_(�E��)�̍��W
            int right = cX + CONS_C.Main.DIAMETER;
            int bottom = CONS_C.Main.OFFSET_Y + CONS_C.Main.DIAMETER;

            _drawColorHole__Red(canvas);
            
//            // ����\��
//            CONS_Canvas.Main.drawables[CONS_Canvas.Main.RED_OVAL].getPaint().setShader(
//                    new RadialGradient(
//                    		25 + CONS_Canvas.Main.RadialGradient_OFFSET_RED_X, 
//                    		25 + this.RadialGradient_OFFSET_RED_Y, 
//                    		20, Color.BLACK, Color.RED,
////                    		new RadialGradient(25, 25, 20, Color.BLACK, Color.RED,
//                        Shader.TileMode.MIRROR));
//
//            CONS_Canvas.Main.drawables[CONS_Canvas.Main.RED_OVAL].setBounds(
//            					left + this.Bounds_OFFSET_RED_LEFT, 
//            					top + this.Bounds_OFFSET_RED_TOP, 
//            					right, bottom);
//            
//            CONS_Canvas.Main.drawables[CONS_Canvas.Main.RED_OVAL].draw(canvas);

            // ��ʉE�[�ɗ΋ʂ��󂯂錊

            // �n�_(����)�̍��W
            left = width - CONS_C.Main.DIAMETER;
            top = cY;

            // �I�_(�E��)�̍��W
            right = width;
            bottom = cY + CONS_C.Main.DIAMETER;

            ////////////////////////////////

			// Green

			////////////////////////////////
			this._drawColorHole__Green(canvas);
//            // ����\��
//            CONS_Canvas.Main.drawables[CONS_Canvas.Main.GREEN_OVAL].setBounds(left, top, right, bottom);
//            CONS_Canvas.Main.drawables[CONS_Canvas.Main.GREEN_OVAL].draw(canvas);

            // ��ʉ��[�ɐʂ��󂯂錊

            // �n�_(����)�̍��W
            left = cX;
            top = height - CONS_C.Main.DIAMETER;

            // �I�_(�E��)�̍��W
            right = cX + CONS_C.Main.DIAMETER;
            bottom = height;

            // ����\��
            CONS_C.Main.drawables[CONS_C.Main.BLUE_OVAL].setBounds(left, top, right, bottom);
            CONS_C.Main.drawables[CONS_C.Main.BLUE_OVAL].draw(canvas);

            // ��ʍ��[�ɔ��ʂ��󂯂錊

            // �n�_(����)�̍��W
            left = CONS_C.Main.OFFSET_X;
            top = cY;

            // �I�_(�E��)�̍��W
            right = CONS_C.Main.OFFSET_X + CONS_C.Main.DIAMETER;
            bottom = cY + CONS_C.Main.DIAMETER;

            // ����\��
            CONS_C.Main.drawables[CONS_C.Main.WHITE_OVAL].setBounds(left, top, right, bottom);
            CONS_C.Main.drawables[CONS_C.Main.WHITE_OVAL].draw(canvas);
            
	}//drawColorHole

	private void 
	_drawColorHole__Red(Canvas canvas) {
		////////////////////////////////

		// Shader

		////////////////////////////////
        CONS_C.Main.drawables[CONS_C.Main.RED_OVAL].getPaint().setShader(
                new RadialGradient(
                		25 + CONS_C.Main.RadialGradient_OFFSET_RED_X, 
                		25 + this.RadialGradient_OFFSET_RED_Y, 
                		20, Color.BLACK, Color.RED,
//                		new RadialGradient(25, 25, 20, Color.BLACK, Color.RED,
                    Shader.TileMode.MIRROR));

        ////////////////////////////////

		// Bounds

		////////////////////////////////
        CONS_C.Main.drawables[CONS_C.Main.RED_OVAL].setBounds(
        					CONS_C.Main.BOUNDS_ORIG_RED_LEFT
        						+ CONS_C.Main.BOUNDS_OFFSET_RED_LEFT,
        					CONS_C.Main.BOUNDS_ORIG_RED_TOP
        						+ CONS_C.Main.BOUNDS_OFFSET_RED_TOP,
        					CONS_C.Main.BOUNDS_ORIG_RED_LEFT
        						+ CONS_C.Main.DIAMETER, 
        						CONS_C.Main.BOUNDS_ORIG_RED_TOP
        						+ CONS_C.Main.DIAMETER 
        					);
        
        CONS_C.Main.drawables[CONS_C.Main.RED_OVAL].draw(canvas);

	}
	
	private void 
	_drawColorHole__Green(Canvas canvas) {
		////////////////////////////////
		
		// Shader
		
		////////////////////////////////
		CONS_C.Main.drawables[CONS_C.Main.GREEN_OVAL].getPaint().setShader(
				new RadialGradient(
						25 + CONS_C.Main.RadialGradient_OFFSET_GREEN_X, 
						25 + CONS_C.Main.RadialGradient_OFFSET_GREEN_Y, 
						20, Color.BLACK, Color.GREEN,
//                		new RadialGradient(25, 25, 20, Color.BLACK, Color.RED,
						Shader.TileMode.MIRROR));
		
		////////////////////////////////
		
		// Bounds
		
		////////////////////////////////
		CONS_C.Main.drawables[CONS_C.Main.GREEN_OVAL].setBounds(
				CONS_C.Main.BOUNDS_ORIG_GREEN_LEFT
					+ CONS_C.Main.BOUNDS_OFFSET_GREEN_LEFT,
				CONS_C.Main.BOUNDS_ORIG_GREEN_TOP
					+ CONS_C.Main.BOUNDS_OFFSET_GREEN_TOP,
				CONS_C.Main.BOUNDS_ORIG_GREEN_LEFT
					+ CONS_C.Main.DIAMETER, 
				CONS_C.Main.BOUNDS_ORIG_GREEN_TOP
					+ CONS_C.Main.DIAMETER 
				);
		
		CONS_C.Main.drawables[CONS_C.Main.GREEN_OVAL].draw(canvas);
		
	}//_drawColorHole__Green(Canvas canvas)

	public void 
	_drawLine
	(float x1, float y1, float x2, float y2, Paint p) {
		
//		this.drawLine(10, 10, 100, 100, p);
		
		////////////////////////////////

		// switch

		////////////////////////////////
		CONS_C.Line.drawLine = true;
		
		////////////////////////////////

		// set vals

		////////////////////////////////
		CONS_C.Line.x1	= x1;
		CONS_C.Line.x2	= x2;
		CONS_C.Line.y1	= y1;
		CONS_C.Line.y2	= y2;
		
		CONS_C.Line.p_Line	= p;
		
		
		////////////////////////////////

		// invalidate

		////////////////////////////////
		this.invalidate();

		////////////////////////////////
		
		// switch
		
		////////////////////////////////
//		CONS_C.Line.drawLine = false;

		// Log
		String msg_Log = "drawLine() => done";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//drawLine

	public void 
	_drawLines
	(float[] data, Paint p) {
		
//		this.drawLine(10, 10, 100, 100, p);
		
		////////////////////////////////
		
		// switch
		
		////////////////////////////////
		CONS_C.Line.drawLines	= true;
		
		////////////////////////////////
		
		// set vals
		
		////////////////////////////////
//		CONS_Canvas.Line.x1	= x1;
//		CONS_Canvas.Line.x2	= x2;
//		CONS_Canvas.Line.y1	= y1;
//		CONS_Canvas.Line.y2	= y2;
		
		
		CONS_C.Line.pnt_Lines	= data;
		CONS_C.Line.p_Lines	= p;
		
		////////////////////////////////
		
		// invalidate
		
		////////////////////////////////
		this.invalidate();

		////////////////////////////////
		
		// switch
		
		////////////////////////////////
//		CONS_C.Line.drawLines	= false;

		// Log
		String msg_Log = "drawLine() => done";
		Log.d("CanvasView_4.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", msg_Log);
		
	}//drawLine
	
}
