package com.indulge.freedom.who.view.textsurface.animations;


import com.indulge.freedom.who.view.textsurface.contants.TYPE;
import com.indulge.freedom.who.view.textsurface.interfaces.ISurfaceAnimation;

/**
 * Created by Eugene Levenetc.
 */
public class Sequential extends AnimationsSet {
	public Sequential(ISurfaceAnimation... animations) {
		super(TYPE.SEQUENTIAL, animations);
	}
}
