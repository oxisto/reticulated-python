package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode
import java.lang.Exception

class Visitor : Python3BaseVisitor<Node>() {

  override fun visitFile_input(ctx: Python3Parser.File_inputContext?): FileInput {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val fileInput = FileInput()
    val list = fileInput.statements

    // loop through children
    for (tree in ctx.children) {
      if (tree is TerminalNode) {
        continue
      }

      list.add(tree.accept(this) as Statement)
    }

    return fileInput
  }

  override fun visitStmt(ctx: Python3Parser.StmtContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val node = super.visitStmt(ctx)

    if (node !is Statement) {
      throw Exception()
    } else {
      return node
    }
  }

  override fun visitFuncdef(ctx: Python3Parser.FuncdefContext?): Node {
    // TODO: decorators

    // assume that the first child is 'def'

    // TODO: check, if result is really an identifier
    // second is the name
    val id = ctx?.getChild(1)?.accept(this) as Identifier

    // create a new function definition
    val def = FunctionDefinition(id)

    return def;
  }

  override fun visitTerminal(node: TerminalNode?): Node {
    if (node == null) {
      throw EmptyContextException();
    }

    // TODO: literals and stuff
    //return super.visitTerm(ctx)

    return Identifier(node.text)
  }

}
