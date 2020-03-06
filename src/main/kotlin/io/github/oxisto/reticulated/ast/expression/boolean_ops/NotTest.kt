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

package io.github.oxisto.reticulated.ast.expression.boolean_ops

import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import java.lang.IllegalArgumentException

class NotTest(val comparison: Comparison?, val notTest:NotTest? ): BaseBooleanOp() {

    init {
        if(comparison == null){
            if(notTest == null){
                throw IllegalArgumentException()
            }
        }else {
            if(notTest != null){
                throw IllegalArgumentException()
            }
        }
    }

    override fun toString(): String {
        var result = "NotTest(" + System.lineSeparator() + "\t"
        result += if(comparison == null){
            "not notTest=$notTest"
        }else {
            "comparison=$comparison"
        }
        result += System.lineSeparator() + ")"
        return result
    }

}