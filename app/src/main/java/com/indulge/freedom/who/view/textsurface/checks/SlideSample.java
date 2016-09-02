package com.indulge.freedom.who.view.textsurface.checks;

import com.indulge.freedom.who.view.textsurface.Text;
import com.indulge.freedom.who.view.textsurface.TextBuilder;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.animations.AnimationsSet;
import com.indulge.freedom.who.view.textsurface.animations.Slide;
import com.indulge.freedom.who.view.textsurface.animations.TransSurface;
import com.indulge.freedom.who.view.textsurface.contants.Align;
import com.indulge.freedom.who.view.textsurface.contants.Pivot;
import com.indulge.freedom.who.view.textsurface.contants.Side;
import com.indulge.freedom.who.view.textsurface.contants.TYPE;

/**
 * Created by Eugene Levenetc.
 */
public class SlideSample {
	public static void play(TextSurface textSurface) {

		Text textA = TextBuilder.create(" How are you?").build();
		Text textB = TextBuilder.create("I'm fine! ").setPosition(Align.LEFT_OF, textA).build();
		Text textC = TextBuilder.create("Are you sure?").setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textB).build();
		Text textD = TextBuilder.create("Totally!").setPadding(10, 10, 10, 10).setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textC).build();
		int duration = 1250;

		textSurface.play(
				TYPE.SEQUENTIAL,
				new AnimationsSet(TYPE.PARALLEL,
						new AnimationsSet(TYPE.SEQUENTIAL,
								new AnimationsSet(TYPE.PARALLEL, Slide.showFrom(Side.LEFT, textA, duration), Slide.showFrom(Side.RIGHT, textB, duration)),
								Slide.showFrom(Side.TOP, textC, duration),
								Slide.showFrom(Side.BOTTOM, textD, duration)
						),
						new TransSurface(duration * 3, textD, Pivot.CENTER)
				),
				new AnimationsSet(TYPE.PARALLEL,
						new AnimationsSet(TYPE.SEQUENTIAL,
								new AnimationsSet(TYPE.PARALLEL, Slide.hideFrom(Side.LEFT, textD, duration), Slide.hideFrom(Side.RIGHT, textC, duration)),
								Slide.hideFrom(Side.TOP, textB, duration),
								Slide.hideFrom(Side.BOTTOM, textA, duration)
						),
						new TransSurface(duration * 3, textA, Pivot.CENTER)
				)

		);
	}
}
