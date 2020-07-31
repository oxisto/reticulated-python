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

package io.github.oxisto.reticulated.ast.expression.comprehension

import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.simple.target.Target

/**
 * This class represents a comp_for.
 * It´s EBNF definition is:
 *      comp_for :: ["async"] "for" target_list "in" or_test [comp_iter]
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#displays-for-lists-sets-and-dictionaries}]
 */
class CompFor(val isAsync: Boolean, val targetList: Target, val orTest: Expression, val compIter: CompIter?) : CompIter() {

    override fun toString(): String {
        var result = "CompFor(" + System.lineSeparator() + "\t"
        if ( isAsync )
            result += "\"async\" "
        result += "\"for\" targetList=$targetList  \"in\" orTest=$orTest"
        if ( compIter != null )
            result += " compIter=$compIter"
        return result + System.lineSeparator() + ")"
    }

}