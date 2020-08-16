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

/**
 * This class represents a stringliteral
 * The EBNF representation is:
 *      stringliteral   ::=  [stringprefix] ( shortstring | longstring )
 *      stringprefix    ::=  "r" | "u" | "R" | "U" | "f" | "F" | "fr" |
 *              "Fr" | "fR" | "FR" | "rf" | "rF" | "Rf" | "RF"
 *      shortstring     ::=  "'" shortstringitem* "'" | '"' shortstringitem* '"'
 *      longstring      ::=  "'''" longstringitem* "'''" | '"""' longstringitem* '"""'
 *      shortstringitem ::=  shortstringchar | stringescapeseq
 *      longstringitem  ::=  longstringchar | stringescapeseq
 *      shortstringchar ::=  <any source character except "\" or newline or the quote>
 *      longstringchar  ::=  <any source character except "\">
 *      stringescapeseq ::=  "\" <any source character>
 *  [see: {@linktourl https://docs.python.org/3/reference/lexical_analysis.html#literals}]
 */
class StringLiteral(val value: String) : Literal<String>() {
  override fun toString(): String {
    return "StringLiteral(" + System.lineSeparator() +
      "\tvalue=$value" + System.lineSeparator() +
      ")"
  }
}
