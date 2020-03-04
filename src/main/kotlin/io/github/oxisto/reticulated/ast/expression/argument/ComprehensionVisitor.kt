package io.github.oxisto.reticulated.ast.expression.argument

import io.github.oxisto.reticulated.ast.Node
import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser
import jdk.jshell.spi.ExecutionControl

/**
 * This class offers visitors for comp_for, conp_iter and comp_if
 * The EBNF representation is:
 *      comprehension ::= expression comp_for
 *      comp_for ::= ["async"] "for" target_list "in" or_test [comp_iter]
 *      comp_iter ::= comp_for | comp_if
 *      comp_if ::= "if" expression_nocond [comp_iter]
 *  [see: {@linktourl https://docs.python.org/3/reference/expressions.html#displays-for-lists-sets-and-dictionaries}]
 */
class ComprehensionVisitor(val scope: Scope) : Python3BaseVisitor<Node>() {
    // TODO: implement visitors

    override fun visitComp_for(ctx: Python3Parser.Comp_forContext?): Node? {
        throw ExecutionControl.NotImplementedException("")
    }

    override fun visitComp_iter(ctx: Python3Parser.Comp_iterContext?): Node? {
        throw ExecutionControl.NotImplementedException("")
    }

    override fun visitComp_if(ctx: Python3Parser.Comp_ifContext?): Node? {
        throw ExecutionControl.NotImplementedException("")
    }
}