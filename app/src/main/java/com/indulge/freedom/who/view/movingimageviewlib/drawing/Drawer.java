/*
 * Copyright 2014-2015 Diego Grancini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.indulge.freedom.who.view.movingimageviewlib.drawing;

import android.graphics.Canvas;

import com.indulge.freedom.who.view.movingimageviewlib.parameters.Parameters;


/**
 * A Drawer specifies how to use the generated values to draw the image inside the ImageView
 * <p/>
 * Drawer created by Diego Grancini on 21/12/2014.
 */
public interface Drawer {
    /**
     * This method applies a transformation to the Canvas to change the view
     *
     * @param canvas     the canvas on which the transformations will be drawn
     * @param parameters the generated values to be applied in the transformation
     */
    public void onDraw(Canvas canvas, Parameters parameters);
}
