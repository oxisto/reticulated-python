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

package io.github.oxisto.reticulated.ast.expression.operator

import java.lang.IllegalArgumentException

class ShiftExpr(
        val shiftExpr: ShiftExpr?,
        val binaryOperator: BinaryOperator?,
        val baseOperator: BaseOperator
): BaseOperator() {

    init {
        if(binaryOperator == null){
            if(shiftExpr != null) {
                throw IllegalArgumentException()
            }
        }else {
            if(shiftExpr == null || (
                            binaryOperator != BinaryOperator.SHIFT_LEFT &&
                                    binaryOperator != BinaryOperator.SHIFT_LEFT
                        )
            ){
                throw IllegalArgumentException()
            }
        }
    }

    override fun toString(): String {
        var result = "ShiftExpr(" + System.lineSeparator() + "\t"
        if (shiftExpr != null) {
            result += "shiftExpr=$shiftExpr binaryOperator=${binaryOperator!!.representation} "
        }
        result += "additiveExpr=$baseOperator" + System.lineSeparator() +
                ")"
        return result
    }
}