package com.indulge.freedom.who.view.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.indulge.freedom.who.view.CustomEditText;


/**
 * This animation fades the view out by animating its alpha property to 0. On
 * animation end, the view is restored to its original state and is set to
 * <code>View.INVISIBLE</code>.
 * 
 * @author SiYao
 * 
 */
public class FadeOutAnimation extends Animation implements Combinable {

	private  CustomEditText AfterEditTextClear;
	private int inDuration;
	private  Context context;
	private  String dongzuo = "";
	private  View nextView = null;
	TimeInterpolator interpolator;
	long duration;
	AnimationListener listener;


	public FadeOutAnimation(Context context, View view, String dongzuo, View nextView, int inDuration, CustomEditText AfterEditTextClear) {
		this.view = view;
		this.context = context;
		this.dongzuo = dongzuo;
		this.inDuration = inDuration;
		this.nextView = nextView;
		this.AfterEditTextClear = AfterEditTextClear;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}


	/**
	 * This animation fades the view out by animating its alpha property to 0.
	 * On animation end, the view is restored to its original state and is set
	 * to <code>View.INVISIBLE</code>.
	 *  @param view
	 *            The view to be animated.
	 * @param dongzuo
	 * @param nextView
	 */
	public FadeOutAnimation(Context context, View view, String dongzuo, View nextView, int inDuration) {
		this.view = view;
		this.context = context;
		this.dongzuo = dongzuo;
		this.inDuration = inDuration;
		this.nextView = nextView;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}


	public FadeOutAnimation(View view) {
		this.view = view;
		interpolator = new AccelerateDecelerateInterpolator();
		duration = DURATION_LONG;
		listener = null;
	}

	@Override
	public void animate() {
		getAnimatorSet().start();
	}

	@Override
	public AnimatorSet getAnimatorSet() {
		final float originalAlpha = view.getAlpha();
		AnimatorSet fadeSet = new AnimatorSet();
		fadeSet.play(ObjectAnimator.ofFloat(view, View.ALPHA, 0f));
		fadeSet.setInterpolator(interpolator);
		fadeSet.setDuration(duration);
		fadeSet.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
				if(!TextUtils.isEmpty(dongzuo)&&nextView!=null&&inDuration>=0){
					if (AfterEditTextClear!=null){
						// 动画显示手机号布局
						FadeInAnimation fadeInAnimation = new FadeInAnimation(context,nextView,AfterEditTextClear);
						fadeInAnimation.setDuration(inDuration);
						fadeInAnimation.animate();
					}else{
						// 动画显示手机号布局
						FadeInAnimation fadeInAnimation = new FadeInAnimation(context,nextView);
						fadeInAnimation.setDuration(inDuration);
						fadeInAnimation.animate();
					}

				}
				view.setAlpha(originalAlpha);
				if (getListener() != null) {
					getListener().onAnimationEnd(FadeOutAnimation.this);
				}
			}
		});
		return fadeSet;
	}

	/**
	 * @return The interpolator of the entire animation.
	 */
	public TimeInterpolator getInterpolator() {
		return interpolator;
	}

	/**
	 * @param interpolator
	 *            The interpolator of the entire animation to set.
	 */
	public FadeOutAnimation setInterpolator(TimeInterpolator interpolator) {
		this.interpolator = interpolator;
		return this;
	}

	/**
	 * @return The duration of the entire animation.
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            The duration of the entire animation to set.
	 */
	public FadeOutAnimation setDuration(long duration) {
		this.duration = duration;
		return this;
	}

	@Override
	public Animation setListener(AnimationListener animationListener) {
		return null;
	}

	/**
	 * @return The listener for the end of the animation.
	 */
	public AnimationListener getListener() {
		return listener;
	}



}
