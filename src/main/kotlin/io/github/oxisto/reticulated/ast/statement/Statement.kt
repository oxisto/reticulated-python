package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Node

abstract class Statement() : Node() {

  fun asCompoundStatement(): CompoundStatement {
    return this as CompoundStatement
  }

  fun asStatementList(): StatementList {
    return this as StatementList
  }

  abstract fun isCompoundStatement(): Boolean
  abstract fun isStatementList(): Boolean

}