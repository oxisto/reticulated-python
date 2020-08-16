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

package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite

/**
 * A definition is a utility node to distinguish definition-style compound statements from others, such as 'if' and 'for'.
 */
abstract class Definition(suite: Suite) : CompoundStatement(suite) {

  abstract fun isClassDefinition(): Boolean
  abstract fun isFunctionDefinition(): Boolean

  fun asFunctionDefinition(): FunctionDefinition {
    return this as FunctionDefinition
  }

  fun asClassDefinition(): ClassDefinition {
    return this as ClassDefinition
  }

}
