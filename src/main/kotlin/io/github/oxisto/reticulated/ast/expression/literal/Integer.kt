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

/**
 * This class represents a integer
 * The EBNF representation is:
 *      integer      ::=  decinteger | bininteger | octinteger | hexinteger
 *      decinteger   ::=  nonzerodigit (["_"] digit)* | "0"+ (["_"] "0")*
 *      bininteger   ::=  "0" ("b" | "B") (["_"] bindigit)+
 *      octinteger   ::=  "0" ("o" | "O") (["_"] octdigit)+
 *      hexinteger   ::=  "0" ("x" | "X") (["_"] hexdigit)+
 *      nonzerodigit ::=  "1"..."9"
 *      digit        ::=  "0"..."9"
 *      bindigit     ::=  "0" | "1"
 *      octdigit     ::=  "0"..."7"
 *      hexdigit     ::=  digit | "a"..."f" | "A"..."F"
 *  [see: {@linktourl https://docs.python.org/3/reference/lexical_analysis.html#literals}]
 */
class Integer(val value: Int) : Literal<Int>() {
    override fun toString(): String {
        return "Integer(" + System.lineSeparator() +
                "\tvalue=$value" + System.lineSeparator() +
                ")"
    }
}