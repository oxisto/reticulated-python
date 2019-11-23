package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.ast.Node
import com.github.oxisto.reticulated.ast.expression.Expression

/**
 * A parameter, i.e. in a function definition.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-parameter
 */
open class Parameter(val id: Identifier, val expression: Expression? = null) : Node() {

  init {
    id.parent = this
    expression?.parent = this
  }

}