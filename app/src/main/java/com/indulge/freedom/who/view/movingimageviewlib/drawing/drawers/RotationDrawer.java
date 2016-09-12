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

package com.indulge.freedom.who.view.movingimageviewlib.drawing.drawers;

import android.graphics.Canvas;

import com.indulge.freedom.who.view.movingimageviewlib.drawing.Drawer;
import com.indulge.freedom.who.view.movingimageviewlib.parameters.Parameters;


/**
 * The drawer that rotate the canvas using angle value
 * <p/>
 * RotationDrawer created by Diego Grancini on 08/12/2014.
 */
public class RotationDrawer implements Drawer {

    @Override
    public void onDraw(Canvas canvas, Parameters parameters) {
        int width = parameters.getWidth();
        int height = parameters.getHeight();
        float angle = parameters.getAngle();
        float x = parameters.getX();
        float y = parameters.getY();
        int pivotX = width / 2;
        int pivotY = height / 2;
        canvas.rotate(angle, pivotX + x, pivotY + y);
    }
}
