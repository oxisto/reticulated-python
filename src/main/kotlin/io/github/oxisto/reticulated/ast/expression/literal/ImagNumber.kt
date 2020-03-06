/*
 * Copyright (c) 2019, Fraunhofer AISEC. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.github.oxisto.reticulated.ast.expression.literal

import io.github.oxisto.reticulated.ast.Node
import java.lang.IllegalArgumentException

class ImagNumber(val floatNumber:FloatNumber?, val integer:Integer?) : Literal<ImagNumber>() {
    init {
        if(floatNumber == null){
            if(integer == null) {
                throw IllegalArgumentException()
            }
        }else {
            if(integer != null) {
                throw IllegalArgumentException()
            }
        }
    }

    override fun toString():String {
        val result:String
        if(floatNumber == null){
            result = "value=$integer j"
        }else{
            result = "value=$floatNumber j"
        }
        return "ImagNumber(" + System.lineSeparator() +
                "\t$result" + System.lineSeparator() +
                ")"
    }

}