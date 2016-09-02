package com.indulge.freedom.who.view.textsurface.animations;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.indulge.freedom.who.view.textsurface.Debug;
import com.indulge.freedom.who.view.textsurface.Text;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.contants.Side;
import com.indulge.freedom.who.view.textsurface.utils.Utils;


/**
 * Created by Eugene Levenetc.
 */
public class SideCut implements ShapeReveal.IRevealShape, ValueAnimator.AnimatorUpdateListener {

	private float diffX;
	private Text text;
	private static final float CUT_SIZE = Utils.dpToPx(20);
	private Path clipPath = new Path();
	private ValueAnimator animator;
	private TextSurface textSurface;
	private final boolean toShow;
	private final int side;

	public static SideCut show(int side) {
		return new SideCut(true, side);
	}

	public static SideCut hide(int side) {
		return new SideCut(false, side);
	}

	private SideCut(boolean toShow, int side) {
		this.toShow = toShow;
		this.side = side;
	}

	public void setText(Text text) {
		this.text = text;
	}

	@Override public void setTextSurface(TextSurface textSurface) {
		this.textSurface = textSurface;
	}

	public ValueAnimator getAnimator() {
		final float width = text.getWidth();
		float toX = 0;
		float fromX = 0;

		if (toShow) {
			if (side == Side.LEFT) {
				fromX = -(width + CUT_SIZE);
			} else if (side == Side.RIGHT) {
				fromX = width;
				toX = -CUT_SIZE;
			}
		} else {
			if (side == Side.LEFT) {
				fromX = -CUT_SIZE;
				toX = width;
			} else if (side == Side.RIGHT) {
				fromX = width;
				toX = -CUT_SIZE;
			}
		}


		animator = ValueAnimator.ofFloat(fromX, toX);
		animator.addUpdateListener(this);
		return animator;
	}

	@Override public void clip(Canvas canvas, String textValue, float x, float y, Paint paint) {
		final float width = text.getWidth();
		final float height = text.getHeight();

		if (toShow) {
			if (side == Side.LEFT) {
				clipPath.reset();
				clipPath.moveTo(x + diffX, y - height);
				clipPath.rLineTo(width, 0);
				clipPath.rLineTo(CUT_SIZE, height + text.getFontDescent());
				clipPath.rLineTo(-(width + CUT_SIZE), 0);
				clipPath.close();
			} else if (side == Side.RIGHT) {
				clipPath.reset();
				clipPath.moveTo(x + diffX, y - height);
				clipPath.rLineTo(CUT_SIZE, height);
				clipPath.rLineTo(width, 0);
				clipPath.rLineTo(0, -height);
				clipPath.close();
			}
		} else {
			if (side == Side.LEFT) {
				clipPath.reset();
				clipPath.moveTo(x + diffX, y - height);
				clipPath.rLineTo(width + CUT_SIZE, 0);
				clipPath.rLineTo(0, height + text.getFontDescent());
				clipPath.rLineTo(-width, 0);
				clipPath.close();
			} else if (side == Side.RIGHT) {
				clipPath.reset();
				clipPath.moveTo(x + diffX, y - height);
				clipPath.rLineTo(CUT_SIZE, height);
				clipPath.rLineTo(width, 0);
				clipPath.rLineTo(0, -height);
				clipPath.close();
			}
		}

		if (Debug.ENABLED) canvas.drawPath(clipPath, Debug.BLUE_STROKE);

		canvas.translate(0, -(text.getFontDescent()));
		canvas.clipPath(clipPath);
	}

	@Override public boolean isToShow() {
		return toShow;
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		diffX = (float) animation.getAnimatedValue();
		textSurface.invalidate();
	}
}
