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

package io.github.oxisto.reticulated.ast.expression.literal

/**
 * This class represents a bytesliteral
 * The EBNF representation is:
 *      bytesliteral   ::=  bytesprefix ( shortbytes | longbytes )
 *      bytesprefix    ::=  "b" | "B" | "br" | "Br" | "bR" | "BR" | "rb" | "rB" | "Rb" | "RB"
 *      shortbytes     ::=  "'" shortbytesitem* "'" | '"' shortbytesitem* '"'
 *      longbytes      ::=  "'''" longbytesitem* "'''" | '"""' longbytesitem* '"""'
 *      shortbytesitem ::=  shortbyteschar | bytesescapeseq
 *      longbytesitem  ::=  longbyteschar | bytesescapeseq
 *      shortbyteschar ::=  <any ASCII character except "\" or newline or the quote>
 *      longbyteschar  ::=  <any ASCII character except "\">
 *      bytesescapeseq ::=  "\" <any ASCII character>
 *  [see: {@linktourl https://docs.python.org/3/reference/lexical_analysis.html#literals}]
 */
class BytesLiteral(val value: Byte) : Literal<Byte>() {
    override fun toString(): String {
        return "BytesLiteral(" + System.lineSeparator() +
                "\tvalue=$value" + System.lineSeparator() +
                ")"
    }

}