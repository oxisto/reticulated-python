/*
 * Copyright (c) 2020, Fraunhofer AISEC. All rights reserved.
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

import io.github.oxisto.reticulated.ast.CouldNotParseException

/**
 * All binary operators of Python.
 */
enum class BinaryOperator(val symbol: String) {
  SHIFT_RIGHT(">>"), SHIFT_LEFT("<<"), ADDITION("+"),
  SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/"),
  FLOOR_DIVISION("//"), MATRIX_MULTIPLICATION("@"),
  MODULO("%"), POWER("**");

  companion object {
    fun getBinaryOperator(symbolToFind: String): BinaryOperator {
      var result: BinaryOperator? = null
      for (binaryOperator in values()) {
        if (binaryOperator.symbol == symbolToFind) {
          result = binaryOperator
          break
        }
      }
      return result ?: throw CouldNotParseException(
        "The BinaryOperator=$symbolToFind should be a valid operator"
      )
    }
  }
}
