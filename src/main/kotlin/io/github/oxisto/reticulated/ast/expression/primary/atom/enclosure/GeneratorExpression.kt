package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.expression.comprehension.CompFor

class GeneratorExpression(val expression: Expression, val compFor: CompFor) : Enclosure() {
  override fun toString(): String {
    return "GeneratorExpression(" + System.lineSeparator() +
        "\t \"(\" expression=$expression compFor=$compFor \")\"" + System.lineSeparator() +
        ")"
  }

}