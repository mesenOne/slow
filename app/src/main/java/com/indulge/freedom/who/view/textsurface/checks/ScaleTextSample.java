package com.indulge.freedom.who.view.textsurface.checks;


import com.indulge.freedom.who.view.textsurface.Text;
import com.indulge.freedom.who.view.textsurface.TextBuilder;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.animations.Just;
import com.indulge.freedom.who.view.textsurface.animations.Scale;
import com.indulge.freedom.who.view.textsurface.animations.Sequential;
import com.indulge.freedom.who.view.textsurface.contants.Align;
import com.indulge.freedom.who.view.textsurface.contants.Pivot;
import com.indulge.freedom.who.view.textsurface.contants.TYPE;

/**
 * Created by Eugene Levenetc.
 */
public class ScaleTextSample {
	public static void run(TextSurface textSurface) {
//		Text textA = TextBuilder.create("oat cake")
////				.setScale(2.0f, Pivot.RIGHT)
//				.build();
//
//		textSurface.play(TYPE.SEQUENTIAL,
//				//Just.show(textA)
//				new Scale(textA, 1000, 0.1f, 1.5f, Pivot.RIGHT)
//		);

		Text textA = TextBuilder.create("textA")
//				.setPosition(Align.SURFACE_CENTER)
				.build();

		Text textB = TextBuilder.create("textB")
				.setPosition(Align.LEFT_OF, textA)
				.build();

		Text textC = TextBuilder.create("textC")
				.setPosition(Align.RIGHT_OF, textA)
				.build();

		Text textD = TextBuilder.create("textD")
				.setPosition(Align.LEFT_OF, textB)
				.build();

		Text textE = TextBuilder.create("textE")
				.setPosition(Align.RIGHT_OF, textC)
				.build();

		textSurface.play(TYPE.PARALLEL,
				Just.show(textA, textB),
				new Sequential(new Scale(textA, 1000, 1, 2, Pivot.CENTER), new Scale(textA, 1000, 2, 1, Pivot.CENTER))
//				new Parallel(new Scale(textA, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textA, 500, 1, 1.5f, Pivot.LEFT)),
//				new Sequential(Delay.duration(250), new Parallel(new Scale(textB, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textB, 500, 1, 1.5f, Pivot.LEFT))),
//				new Sequential(Delay.duration(500), new Parallel(new Scale(textC, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textC, 500, 1, 1.5f, Pivot.LEFT))),
//				new Sequential(Delay.duration(750), new Parallel(new Scale(textD, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textD, 500, 1, 1.5f, Pivot.LEFT)))
		);
	}
}
