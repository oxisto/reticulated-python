package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.SetDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.StringLiteral
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SetTest {

  @Test
  fun starredSetTest () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/starred_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    // print(input.toString())
    val set = (
        (
            (
                (
                    (
                        (
                            input.statements[0] as StatementList
                            )[0] as ExpressionStatement
                        ).starredExpression
                        .expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as SetDisplay
    assertNotNull(set)
    assertNull(set.comprehension)
    val firstElement = (
        (
            (
                (
                    (
                        set.starredList as StarredList
                        )[0].expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Integer
    assertEquals(firstElement.value, 1)
    val valueOfSecondElement = (
        (
            (
                (
                    (
                        set.starredList as StarredList
                        )[1].expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as StringLiteral
    assertEquals(valueOfSecondElement.value, "a")
  }

  @Test
  fun comprehensionSetTest () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    print(input.toString())
    val set = (
        (
            (
                (
                    (
                        (
                            input.statements[0] as StatementList
                            )[0] as ExpressionStatement
                        ).starredExpression
                        .expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as SetDisplay
    assertNotNull(set)
    assertNull(set.starredList)
    val comprehension = set.comprehension
    assertNotNull(comprehension)
    val compIdentifier = (
        (
            (
                (
                    comprehension.expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Identifier
    assertEquals(compIdentifier.name, "x")
    val compFor = comprehension.compFor
    assertFalse(compFor.isAsync)
    val targetIdentifier = compFor.targetList[0] as Identifier
    assertEquals(targetIdentifier.name, "x")
    val call = (
        (
            compFor.orTest
                .andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Call
    assertNotNull(call)
    val callName = call.primary as Identifier
    assertEquals(callName.name, "range")
    val callArgument = (
        (
            (
                (
                    (
                        call.callTrailer as ArgumentList
                        )[0].expression as ConditionalExpression
                    ).orTest as OrTest
                ).andTest
                .notTest
                .comparison as Comparison
            ).orExpr
            .xorExpr
            .andExpr
            .shiftExpr
            .baseOperator as PowerExpr
        ).primary as Integer
    assertEquals(callArgument.value, 10)
  }


  @Test
  fun starredSetToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/starred_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    val inputString = input.toString()
    // print(beautifyResult(inputString))
    assertEquals(inputString, """FileInput(statements=[StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=SetDisplay(
	"{"starredList=StarredList(
	starredItems=[StarredItem(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Integer(
	value=1
)
)
)
)
)
)
)
)
)
)
)
), StarredItem(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=StringLiteral(
	value=a
)
)
)
)
)
)
)
)
)
)
)
)]
) "}"
)
)
)
)
)
)
)
)
)
)
)
)
)]
)])""".replace("\n", System.lineSeparator()))
  }

  @Test
  fun comprehensionSetToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_set.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)

    val inputString = input.toString()
    // print(beautifyResult(inputString))
    assertEquals(inputString, """FileInput(statements=[StatementList(
	statements=[ExpressionStatement(
	StarredExpression=StarredExpression(
	Expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=SetDisplay(
	"{"comprehension=Comprehension(
	expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Identifier(
	name='x'
)
)
)
)
)
)
)
)
)
)
) compFor=CompFor(
	compFor=for targetList=TargetList(
	targets=[Identifier(
	name='x'
)]
) in orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Call(
	primary=Identifier(
	name='range'
) callTrailer=ArgumentList(
	argument=[Argument(
	expression=ConditionalExpression(
	orTest=OrTest(
	andTest=AndTest(
	notTest=NotTest(
	comparison=Comparison(
	orExpr=OrExpr(
	xorExpr=XorExpr(
	andExpr=AndExpr(
	shiftExpr=ShiftExpr(
	additiveExpr=PowerExpr(
	primary=Integer(
	value=10
)
)
)
)
)
)
)
)
)
)
)
)]
)
)
)
)
)
)
)
)
)
)
)
)
) "}"
)
)
)
)
)
)
)
)
)
)
)
)
)]
)])""".replace("\n", System.lineSeparator()))
  }
}