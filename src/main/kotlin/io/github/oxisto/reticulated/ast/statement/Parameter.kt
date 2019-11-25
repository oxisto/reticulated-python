package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.expression.Identifier
import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.solver.Identifiable

/**
 * A parameter, i.e. in a function definition.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-parameter
 */
open class Parameter(final override val id: Identifier, val expression: Expression? = null) : Node(), Identifiable {

  init {
    id.parent = this
    expression?.parent = this
  }

}