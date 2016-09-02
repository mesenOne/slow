package com.indulge.freedom.who.view.textsurface.checks;

import android.graphics.Color;

import com.indulge.freedom.who.view.textsurface.Text;
import com.indulge.freedom.who.view.textsurface.TextBuilder;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.animations.Alpha;
import com.indulge.freedom.who.view.textsurface.animations.ChangeColor;
import com.indulge.freedom.who.view.textsurface.contants.Align;
import com.indulge.freedom.who.view.textsurface.contants.TYPE;


/**
 * Created by Eugene Levenetc.
 */
public class ColorSample {
	public static void play(TextSurface textSurface) {

		Text textA = TextBuilder
				.create("Now why you loer en kyk gelyk?")
				.setPosition(Align.SURFACE_CENTER)
				.setSize(100)
				.setAlpha(0)
				.build();

		textSurface.play(TYPE.SEQUENTIAL,
				Alpha.show(textA, 1000),
				ChangeColor.to(textA, 1000, Color.RED),
				ChangeColor.fromTo(textA, 1000, Color.BLUE, Color.CYAN),
				Alpha.hide(textA, 1000)
		);
	}
}
