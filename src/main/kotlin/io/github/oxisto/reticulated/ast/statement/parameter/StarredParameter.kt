package io.github.oxisto.reticulated.ast.statement.parameter
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.solver.Identifiable

class StarredParameter (val parameter: BaseParameter) : BaseParameter(), Identifiable {

  override val id: Identifier
    get() = parameter as Identifier

  override fun toString(): String {
    return "Parameter(" + System.lineSeparator() +
        "\t\"*\" parameter=$parameter" +
        System.lineSeparator() + ")"
  }
}