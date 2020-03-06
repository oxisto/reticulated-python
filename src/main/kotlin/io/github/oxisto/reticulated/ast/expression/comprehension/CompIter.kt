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

package io.github.oxisto.reticulated.ast.expression.comprehension

import java.lang.IllegalArgumentException

class CompIter(val compFor: CompFor?, val compIf: CompIf?) : BaseComprehension() {
    init {
        if(compFor == null){
            if(compIf == null){
                throw IllegalArgumentException()
            }
        } else {
            if ( compIf != null ) {
                throw IllegalArgumentException()
            }
        }
    }

    constructor(compFor: CompFor) : this(compFor, null)

    constructor(compIf: CompIf) : this(null, compIf)

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