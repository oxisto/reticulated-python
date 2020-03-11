package io.github.oxisto.reticulated.expression;

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.boolean_ops.OrTest
import io.github.oxisto.reticulated.ast.expression.call.Call
import io.github.oxisto.reticulated.ast.expression.comprehension.Comprehension
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertTrue

class AsyncComprehensionTest {
    @Test
    fun testAsyncArgumentComprehension() {
        val file = File(
                javaClass
                        .classLoader
                        .getResource("expressions/comprehension/async_comprehension_argument.py")!!
            .file
    )

        val input = PythonParser()
                .parse(file.path)
                .root

        val isAsync = (
            (
                (
                    (
                        (
                            (
                                input.statements[0] as StatementList
                                )[0] as ExpressionStatement
                            ).expression as OrTest
                        ).andTest
                        .notTest
                        .comparison!!
                        .orExpr
                        .xorExpr
                        .andExpr
                        .shiftExpr
                        .baseOperator as PowerExpr
                    ).primary as Call)
                .callTrailer as Comprehension
            ).compFor
            .isAsync
        assertTrue(isAsync)
    }
}
