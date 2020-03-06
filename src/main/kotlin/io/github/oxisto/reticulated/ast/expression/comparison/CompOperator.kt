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

package io.github.oxisto.reticulated.ast.expression.comparison

enum class CompOperator(val symbol:String) {
    SMALLER("<"), SMALLER_OR_EQUAL("<="), LARGER(">"), LARGER_OR_EQUAL(">="),
    EQUAL("=="), NOT_EQUAL("!="), IS("is"), IS_NOT("is not"), IN("in"),
    NOT_IN("not in");

    companion object {
        fun getCompOperatorBySymbol(symbolToFind: String): CompOperator? {
            val compOperators = CompOperator.values()
            var result: CompOperator? = null
            for (compOperator in compOperators) {
                if (compOperator.symbol == symbolToFind) {
                    result = compOperator
                }
            }
            return result
        }
    }

}