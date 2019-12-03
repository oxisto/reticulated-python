package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.statement.ParameterList
import io.github.oxisto.reticulated.ast.statement.ParameterListVisitor
import io.github.oxisto.reticulated.ast.statement.Statement
import io.github.oxisto.reticulated.ast.statement.StatementVisitor
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class Visitor(val scope: Scope) : Python3BaseVisitor<Node>() {

  override fun visitFile_input(ctx: Python3Parser.File_inputContext?): FileInput {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val statements = ArrayList<Statement>()

    // loop through children
    for (tree in ctx.children) {
      if (tree is TerminalNode) {
        continue
      }

      val stmt = tree.accept(StatementVisitor(this.scope)) as Statement
      statements.add(stmt)
    }

    return FileInput(statements)
  }

  override fun visitParameters(ctx: Python3Parser.ParametersContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    if (ctx.childCount == 2) {
      return ParameterList()
    }

    // second parameter is the list of (typed) arguments
    val list = ctx.getChild(1).accept(
      ParameterListVisitor(
        this.scope
      )
    )

    return ParameterList(list)
  }

  override fun visitSuite(ctx: Python3Parser.SuiteContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Statement>()

    for (tree in ctx.children) {
      // skip commas, etc.
      if (tree is TerminalNode) {
        continue
      }

      val stmt = tree.accept(StatementVisitor(this.scope)) as Statement
      list.add(stmt)
    }

    return Suite(list)
  }

}
