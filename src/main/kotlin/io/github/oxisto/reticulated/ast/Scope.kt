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

package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.statement.parameter.BaseParameter
import io.github.oxisto.reticulated.ast.statement.parameter.ParameterList
import io.github.oxisto.reticulated.solver.Identifiable
import io.github.oxisto.reticulated.solver.ResolvedVariable

enum class ScopeType {
  GLOBAL,
  FUNCTION,
  BLOCK
}

class Scope(val parent: Scope? = null, val type: ScopeType = ScopeType.GLOBAL) {

  val variables = HashMap<Identifier, ResolvedVariable>()

  fun addVariable(variable: ResolvedVariable) {
    val id = variable.definition.id

    this.variables[id] = variable

    println("Added variable %s to %s scope".format(variable, this.type))
  }

  fun resolveByIdentifier(id: Identifier): ResolvedVariable? {
    // look in the scope itself
    val variable = this.variables[id]

    // forward to parent scope, if there is any
    return if (variable == null && this.parent != null)
      this.parent.resolveByIdentifier(id)
    else variable
  }

  fun handleParameterList(parameterList: BaseParameter) {
    if (parameterList is ParameterList) {
      parameterList.parameters.forEach { parameter ->
        val variable = ResolvedVariable(parameter as Identifiable)
        this.addVariable(variable)
      }
    } else {
      this.addVariable(
          ResolvedVariable(parameterList as Identifiable)
      )
    }
  }

}