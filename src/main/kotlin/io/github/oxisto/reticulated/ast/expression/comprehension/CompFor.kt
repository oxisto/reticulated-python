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

import io.github.oxisto.reticulated.ast.expression.boolean_expr.BaseBooleanExpr
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.simple.target.TargetList


class CompFor(val isAsync: Boolean, val targetList: TargetList, val orTest: OrTest, val compIter: BaseComprehension?) : BaseComprehension() {

    override fun toString(): String {
        var result = String()
        if ( isAsync ) {
            result += "async "
        }
        result += "for targetList=$targetList in orTest=$orTest"
        if ( compIter != null ) {
            result += " compIter=$compIter"
        }
        return "CompFor(" + System.lineSeparator() +
                "\tcompFor=$result" + System.lineSeparator() +
                ")"
    }

}