package com.indulge.freedom.who.view.textsurface.animations;


import com.indulge.freedom.who.view.textsurface.contants.TYPE;
import com.indulge.freedom.who.view.textsurface.interfaces.ISurfaceAnimation;

/**
 * Created by Eugene Levenetc.
 */
public class Parallel extends AnimationsSet {
	public Parallel(ISurfaceAnimation... animations) {
		super(TYPE.PARALLEL, animations);
	}
}
