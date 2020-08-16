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


package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.CouldNotParseException

/**
 * This class represents a dict_display.
 * It´s EBNF representations is:
 *        dict_display ::= "{" ( starred_list | comprehension ) "}"
 *
 * [see: {@linktourl https://docs.python.org/3/reference/expressions.html#grammar-token-dict-display}]
 */
class DictDisplay(val keyDatumList: KeyDatumList?, var dictComprehension: DictComprehension?) : Enclosure() {

  init {
    if (keyDatumList != null && dictComprehension != null)
      throw CouldNotParseException(
        "A DictDisplay should not contain both, a keyDatumList=$keyDatumList and a dictComprehension=$dictComprehension."
      )
  }

  override fun toString(): String {
    var result = "DictDisplay(" + System.lineSeparator() + "\t \"{\" "
    if (keyDatumList != null)
      result += "keyDatumList=$keyDatumList"
    if (dictComprehension != null)
      result += "dictComprehension=$dictComprehension"
    return "$result \"}\"" + System.lineSeparator() + ")"
  }
}
