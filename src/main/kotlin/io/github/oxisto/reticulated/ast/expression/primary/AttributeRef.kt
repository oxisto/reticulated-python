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

package io.github.oxisto.reticulated.ast.expression.primary

import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier

/**
 * This class represents an attributeref.
 * It´s EBNF representation is:
 *        attributeref ::= primary "." identifier
 */
class AttributeRef(val primary: Primary, val identifier: Identifier) : Primary() {


  override fun toString(): String {
    return "AttributeRef(" + System.lineSeparator() +
            "\tprimary=$primary\".\"identifier=$identifier" + System.lineSeparator() +
            ")"
  }

}