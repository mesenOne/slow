package com.indulge.freedom.who.view.textsurface.interfaces;

import com.indulge.freedom.who.view.textsurface.contants.TYPE;

import java.util.LinkedList;


/**
 * Created by Eugene Levenetc.
 */
public interface ISet extends ISurfaceAnimation {
	TYPE getType();

	LinkedList<ISurfaceAnimation> getAnimations();
}
