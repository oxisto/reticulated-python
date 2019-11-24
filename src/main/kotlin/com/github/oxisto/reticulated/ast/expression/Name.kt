package com.github.oxisto.reticulated.ast.expression

import com.github.oxisto.reticulated.ast.Scope
import com.github.oxisto.reticulated.solver.Identifiable
import com.github.oxisto.reticulated.solver.ResolvedVariable

/**
 * According to the reference, a name is an identifier occurring as an atom.
 *
 * Reference: https://docs.python.org/3/reference/expressions.html#atom-identifiers
 */
class Name(name: String) : Identifier(name), Identifiable {

  companion object {
    fun fromIdentifier(id: Identifier): Name {
      return Name(id.name)
    }
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

}