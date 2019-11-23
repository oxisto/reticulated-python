package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode
import java.lang.Exception

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

      val stmt = tree.accept(this) as Statement
      statements.add(stmt)
    }

    val fileInput = FileInput(statements)

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

    // create a new scope for this function
    val functionScope = Scope(this.scope, ScopeType.FUNCTION);

    // third is the parameter list
    val parameterList = ctx.getChild(2).accept(Visitor(functionScope)) as ParameterList;

    // forth is ':'

    // fifth is the suite
    val suite = ctx.getChild(4).accept(Visitor(functionScope)) as Suite;

    // create a new function definition
    val def = FunctionDefinition(id, parameterList, Suite())

    return def;
  }

  override fun visitTerminal(node: TerminalNode?): Node {
    if (node == null) {
      throw EmptyContextException();
    }

    // TODO: literals and stuff
    //return super.visitTerm(ctx)

    val id = Identifier(node.text)

    // TODO: check, where a good place to add variables to a scope is
    this.scope.addVariable(id)

    return id
  }

  override fun visitParameters(ctx: Python3Parser.ParametersContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    if (ctx.childCount == 2) {
      return ParameterList();
    }

    // second parameter is the list of (typed) arguments
    var list = ctx.getChild(1).accept(ListVisitor(this.scope))

    return ParameterList(list);
  }

  override fun visitSuite(ctx: Python3Parser.SuiteContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val list = ArrayList<Statement>()

    for(tree in ctx.children)
    {
      // skip commas, etc.
      if(tree is TerminalNode)
      {
        continue;
      }

      var stmt = tree.accept(this) as Statement
      list.add(stmt)
    }

    return Suite(list)
  }


  override fun visitSimple_stmt(ctx: Python3Parser.Simple_stmtContext?): Node {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // not sure how to handle this
    return ctx.getChild(0).accept(this) as Statement
  }

  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext?): Node {
    // TODO: can be different things
    //return super.visitAtom_expr(ctx)
    val expression = AtomExpression(Object(), listOf())

    return expression
  }
}
