package com.indulge.freedom.who.view.textsurface.interfaces;

import android.support.annotation.NonNull;

import com.indulge.freedom.who.view.textsurface.Text;


/**
 * Created by Eugene Levenetc.
 */
public interface ITextSurfaceAnimation extends ISurfaceAnimation {

	void setInitValues(@NonNull Text text);

	Text getText();

}