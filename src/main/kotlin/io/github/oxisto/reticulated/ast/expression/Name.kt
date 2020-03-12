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

package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.solver.Identifiable
import io.github.oxisto.reticulated.solver.ResolvedType
import io.github.oxisto.reticulated.solver.ResolvedVariable

/**
 * According to the reference, a name is an identifier occurring as an atom.
 *
 * References:
 * - https://docs.python.org/3/reference/expressions.html#atom-identifiers
 * - https://docs.python.org/3/reference/executionmodel.html
 */
class Name(name: String) : Identifier(name), Identifiable {

  companion object {
    fun fromIdentifier(id: Identifier): Name {
      return Name(id.name)
    }
  }

  /**
   * Establishes a binding of this name to the scope. (Actually names are bound to 'blocks' and a 'scope' is only referring to the visibility of a name.
   */
  fun bind(scope: Scope) {
    println("Binding %s to scope %s".format(this.name, scope))

    // not sure what to do here, just add the name for now
    val variable = ResolvedVariable(this, ResolvedType())
    scope.addVariable(variable)
  }

  override val id: Identifier
    get() {
      return this
    }

  /**
   * Tries to resolve the name to an object (or rather a variable holding the object) in the specified scope.
   */
  fun resolve(scope: Scope): ResolvedVariable? {
    return scope.resolveByIdentifier(this)
  }

  override fun toString(): String {
    return "Name(" + System.lineSeparator() +
            "\tname=$name" + System.lineSeparator() +
            ")"
  }

}