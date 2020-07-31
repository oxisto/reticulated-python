/*
 * Copyright (c) 2020, Christian Banse and Andreas Hager. All rights reserved.
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

package io.github.oxisto.reticulated.ast.expression.primary.atom.literal

import io.github.oxisto.reticulated.ast.CouldNotParseException

/**
 * This class represents a imagnumber
 * The EBNF representation is:
 *      imagnumber ::=  (floatnumber | digitpart) ("j" | "J")
 *  [see: {@linktourl https://docs.python.org/3/reference/lexical_analysis.html#literals}]
 */
class ImagNumber(val floatNumber:FloatNumber?, val integer:Integer?) : Literal<ImagNumber>() {
    init {
        if(floatNumber == null){
            if(integer == null) {
                throw CouldNotParseException()
            }
        }else {
            if(integer != null) {
                throw CouldNotParseException()
            }
        }
    }

    override fun toString():String {
        val result = floatNumber ?: integer
        return "ImagNumber(" + System.lineSeparator() +
                "\tvalue=$result \"j\"" + System.lineSeparator() +
                ")"
    }

}