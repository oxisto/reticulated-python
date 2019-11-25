package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.statement.Statement

class Suite(val statements: List<Statement> = ArrayList()) : Node(), Container<Statement> {

  override val children: List<Statement>
    get() = statements

}