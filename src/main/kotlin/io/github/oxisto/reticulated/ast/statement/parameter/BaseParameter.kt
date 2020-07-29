package io.github.oxisto.reticulated.ast.statement.parameter

import io.github.oxisto.reticulated.ast.statement.Statement

abstract class BaseParameter: Statement() {
  abstract override fun toString(): String
}