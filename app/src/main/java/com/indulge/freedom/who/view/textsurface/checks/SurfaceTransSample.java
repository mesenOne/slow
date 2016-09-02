package com.indulge.freedom.who.view.textsurface.checks;



import com.indulge.freedom.who.view.textsurface.Text;
import com.indulge.freedom.who.view.textsurface.TextBuilder;
import com.indulge.freedom.who.view.textsurface.TextSurface;
import com.indulge.freedom.who.view.textsurface.animations.Alpha;
import com.indulge.freedom.who.view.textsurface.animations.AnimationsSet;
import com.indulge.freedom.who.view.textsurface.animations.Delay;
import com.indulge.freedom.who.view.textsurface.animations.ScaleSurface;
import com.indulge.freedom.who.view.textsurface.animations.TransSurface;
import com.indulge.freedom.who.view.textsurface.contants.Align;
import com.indulge.freedom.who.view.textsurface.contants.Fit;
import com.indulge.freedom.who.view.textsurface.contants.Pivot;
import com.indulge.freedom.who.view.textsurface.contants.TYPE;
/**
 * Created by Eugene Levenetc.
 */
public class SurfaceTransSample {
	public static void play(TextSurface textSurface) {
		Text textA = TextBuilder.create("TextA").setPosition(Align.SURFACE_CENTER).build();
		Text textB = TextBuilder.create("TextB").setPosition(Align.RIGHT_OF | Align.BOTTOM_OF, textA).build();
		Text textC = TextBuilder.create("TextC").setPosition(Align.LEFT_OF | Align.BOTTOM_OF, textB).build();
		Text textD = TextBuilder.create("TextD").setPosition(Align.RIGHT_OF | Align.BOTTOM_OF, textC).build();

		int duration = 500;

		textSurface.play(TYPE.SEQUENTIAL,
				Alpha.show(textA, duration),
				new AnimationsSet(TYPE.PARALLEL, Alpha.show(textB, duration), new TransSurface(duration, textB, Pivot.CENTER)),
				new AnimationsSet(TYPE.PARALLEL, Alpha.show(textC, duration), new TransSurface(duration, textC, Pivot.CENTER)),
				new AnimationsSet(TYPE.PARALLEL, Alpha.show(textD, duration), new TransSurface(duration, textD, Pivot.CENTER)),
				new TransSurface(duration, textC, Pivot.CENTER),
				new TransSurface(duration, textB, Pivot.CENTER),
				new TransSurface(duration, textA, Pivot.CENTER)
		);
	}
}
