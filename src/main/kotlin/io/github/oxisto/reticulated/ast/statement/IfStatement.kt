package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite
import io.github.oxisto.reticulated.ast.expression.Expression

class IfStatement(
  val condition: Expression,
  body: Suite,
  val elifCondition: Expression?,
  val elifBody: Suite?,
  val elseCondition: Expression?,
  val elseBody: Suite?,
) : CompoundStatement(body) {

  override fun toString(): String {
    TODO("Not yet implemented")
  }

}