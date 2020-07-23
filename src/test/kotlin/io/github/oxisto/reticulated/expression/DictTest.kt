package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.ConditionalExpression
import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList
import io.github.oxisto.reticulated.ast.expression.booleanops.OrTest
import io.github.oxisto.reticulated.ast.expression.comparison.Comparison
import io.github.oxisto.reticulated.ast.expression.operator.PowerExpr
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.DictComprehension
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.DictDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.KeyDatumList
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.StringLiteral
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.statement.StatementList
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class DictTest {

  @Test
  fun emptyDictTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/empty_dict.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    // print(input.toString())
    val dict = (
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
        ).primary as DictDisplay
    assertNotNull(dict)
    assertNull(dict.dictComprehension)
    assertNull(dict.keyDatumList)
  }

  @Test
  fun keyDatumDictTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/key_datum_dict.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    print(input.toString())
    val dict = (
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
        ).primary as DictDisplay
    assertNotNull(dict)
    assertNull(dict.dictComprehension)
    val firstElement = (
        dict.keyDatumList as KeyDatumList
        ).keyDatums[0]
    val firstKey = (
        (
            (
                (
                    firstElement.key as ConditionalExpression
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
    assertEquals(firstKey.value, "a")
    val firstValue = (
        (
            (
                (
                    firstElement.datum as ConditionalExpression
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
    assertEquals(firstValue.value, 1)

    val secondElement = (
        dict.keyDatumList as KeyDatumList
        ).keyDatums[1]
    val secondKey = (
        (
            (
                (
                    secondElement.key as ConditionalExpression
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
    assertEquals(secondKey.value, "b")
    val secondValue = (
        (
            (
                (
                    secondElement.datum as ConditionalExpression
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
    assertEquals(secondValue.value, 2)
  }

  @Test
  fun comprehensionDictTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_dict.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    print(input.toString())
    val dict = (
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
        ).primary as DictDisplay
    assertNotNull(dict)
    assertNull(dict.keyDatumList)
    val comprehension = dict.dictComprehension as DictComprehension
    assertNotNull(comprehension)
    val compIdentifier = (
        (
            (
                (
                    comprehension.key as ConditionalExpression
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
    val compValue = (
        (
            (
                (
                    comprehension.datum as ConditionalExpression
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
    assertEquals(compValue.value, 1)
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
  fun emptyDictToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/empty_dict.py")!!
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
	primary=DictDisplay(
	 "{" "}"
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
  fun KeyDatumDictToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/key_datum_dict.py")!!
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
	primary=DictDisplay(
	 "{"keyDatumList=KeyDatumList(
	keyDatumList=[KeyDatum(
	key=ConditionalExpression(
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
) ":" datum=ConditionalExpression(
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
), KeyDatum(
	key=ConditionalExpression(
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
	value=b
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
) ":" datum=ConditionalExpression(
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
	value=2
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
  fun comprehensionDictToStringTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/enclosure/display/comprehension_dict.py")!!
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
	primary=DictDisplay(
	 "{"dictComprehension=DictComprehension(
	key=ConditionalExpression(
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
) ":" datum=ConditionalExpression(
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