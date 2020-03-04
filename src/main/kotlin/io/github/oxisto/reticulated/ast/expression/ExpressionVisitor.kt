package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.EmptyContextException
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class ExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Expression>() {

  override fun visitAtom_expr(ctx: Python3Parser.Atom_exprContext?): Expression {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // TODO: can be different things
    //return super.visitAtom_expr(ctx)

    return if (ctx.childCount == 1) {
      ctx.getChild(0).accept(this)
    } else {
      // first child is the primary
      val primary = ctx.getChild(0).accept(this) as Primary

      // check the trailer and decide what it is
      var trailer = ctx.getChild(Python3Parser.TrailerContext::class.java, 0)

      if (trailer.childCount == 3 && trailer.getChild(TerminalNode::class.java, 0).text == "(") {
        // it is a call

        // parse the trailer
        val argumentList =
          trailer.accept(
            ArgumentListVisitor(
              this.scope
            )
          )

        // create a call
        val call = Call(primary, argumentList)

        call
      } else if (trailer.childCount == 2 && trailer.getChild(TerminalNode::class.java, 0).text == ".") {
        // it is an attribute ref, parse the identifier
        val id = trailer.getChild(1).accept(IdentifierVisitor(this.scope))

        val attributeRef = AttributeRef(primary, id)

        attributeRef
      } else {
        throw Exception("could not parse")
      }
    }
  }

  override fun visitAtom(ctx: Python3Parser.AtomContext?): Atom {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val expression = ctx.getChild(0).accept(this)

    if (expression is Identifier) {
      // convert identifier to name
      return Name.fromIdentifier(expression)
    }

    if (expression is Atom) {
      return expression
    }

    throw Exception("Invalid type")
  }

  override fun visitTerminal(node: TerminalNode?): Expression {
    if (node == null) {
      throw EmptyContextException()
    }

    // check for some literals now
    var text = node.text

    if (text.startsWith("\"")) {
      return StringLiteral(text.replace("\"", ""))
    }

    // check if it is a number
    val i = text.toIntOrNull()
    if (i != null) {
      return Integer(i)
    }

    // lets just return an identifier for now
    val id = Identifier(node.text)

    return id
  }
}