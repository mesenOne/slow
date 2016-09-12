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

package com.indulge.freedom.who.view.movingimageviewlib.generating;


import com.indulge.freedom.who.view.movingimageviewlib.generating.generators.AngledValuesGenerator;
import com.indulge.freedom.who.view.movingimageviewlib.generating.generators.BaseValuesGenerator;
import com.indulge.freedom.who.view.movingimageviewlib.generating.generators.ZoomedAngledValuesGenerator;
import com.indulge.freedom.who.view.movingimageviewlib.parameters.Parameters;

/**
 * Utility class used to return the right ValuesGenerator instance based on the VALUESGENERATORS enumeration value
 * <p/>
 * ValuesGeneratorChooser created by Diego Grancini on 08/12/2014.
 */
public class ValuesGeneratorChooser {

    /**
     * Return an instance of ValuesGenerator using the VALUESGENERATORS enumeration value
     *
     * @param valueGeneratorType enumeration value
     * @param parameters         initial values
     * @return the right instance of ValuesGenerator
     * @throws IllegalArgumentException if valueGeneratorType doesn't match any VALUESGENERATOR value
     */
    public static ValuesGenerator get(VALUESGENERATORS valueGeneratorType, Parameters parameters) throws IllegalArgumentException {
        switch (valueGeneratorType) {
            case BASE:
                return new BaseValuesGenerator(parameters);
            case ANGLED:
                return new AngledValuesGenerator(parameters);
            case ZOOMED:
                return new ZoomedAngledValuesGenerator(parameters);
            default:
                throw new IllegalArgumentException();
        }
    }
}
