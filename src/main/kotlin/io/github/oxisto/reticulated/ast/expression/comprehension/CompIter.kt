/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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

package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.CouldNotParseException

/**
 * This class represents a comp_iter.
 * It´s EBNF definition is:
 *      comp_iter ::= comp_for | comp_if
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#displays-for-lists-sets-and-dictionaries}]
 */
class CompIter(val compFor: CompFor?, val compIf: CompIf?) : BaseComprehension() {
    init {
        if(compFor == null){
            if(compIf == null){
                throw CouldNotParseException(
                        "In a CompIter should not be both, the compFor and the compIf, null."
                )
            }
        } else {
            if ( compIf != null ) {
                throw CouldNotParseException(
                        "In a CompIter should me ether compFor or compIf null."
                )
            }
        }
    }

    override fun toString(): String {
        val result:String
        if(compFor == null){
            result = "compIf=$compIf"
        }else{
            result = "compFor=$compFor"
        }
        return "CompIter(" + System.lineSeparator() +
                "\t$result" + System.lineSeparator() +
                ")"
    }
}