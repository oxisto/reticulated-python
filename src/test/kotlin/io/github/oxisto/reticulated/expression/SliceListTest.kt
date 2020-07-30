package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import io.github.oxisto.reticulated.ast.expression.primary.AttributeRef
import io.github.oxisto.reticulated.ast.expression.primary.atom.Identifier
import io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure.ListDisplay
import io.github.oxisto.reticulated.ast.expression.primary.atom.literal.Integer
import io.github.oxisto.reticulated.ast.expression.primary.call.Call
import io.github.oxisto.reticulated.ast.expression.primary.slice.ProperSlice
import io.github.oxisto.reticulated.ast.expression.primary.slice.SliceList
import io.github.oxisto.reticulated.ast.expression.primary.slice.Slicing
import io.github.oxisto.reticulated.ast.expression.primary.slice.Stride
import io.github.oxisto.reticulated.ast.expression.starred.StarredList
import io.github.oxisto.reticulated.ast.simple.AssignmentExpression
import io.github.oxisto.reticulated.ast.simple.ImportStatement
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class SliceListTest {
  @Test
  fun sliceListTest() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/slice/slice_list.py")!!
            .file
    )
    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    // print(
        // beautifyResult(
            // input.toString()
        // )
    // )
    val import = input.statements[0] as ImportStatement
    val module = import.module
    assertEquals(module.name, "numpy")

    val assignment = input.statements[1] as AssignmentExpression
    val target = assignment.target as Identifier
    assertEquals(target.name, "a")
    val expr1 = assignment.expression as Call
    val prim1 = expr1.primary as AttributeRef
    assertEquals(prim1.primary.asIdentifier().name, "numpy")
    assertEquals(prim1.identifier.name, "array")
    val listDisplay1 = expr1.callTrailer as ListDisplay
    assertNull(listDisplay1.comprehension)
    val starredList1 = listDisplay1.starredList as Call
    val prim2 = starredList1.primary as AttributeRef
    assertEquals(prim2.primary.asIdentifier().name, "numpy")
    assertEquals(prim2.identifier.name, "array")
    val listDisplay2 = starredList1.callTrailer as ListDisplay
    assertNull(listDisplay2.comprehension)
    val starredList2 = listDisplay2.starredList as StarredList
    val firstStarredItem = starredList2[0] as Integer
    assertEquals(firstStarredItem.value, 1)
    val secondStarredItem = starredList2[1] as Integer
    assertEquals(secondStarredItem.value, 2)

    val call = input.statements[2] as Call
    assertEquals(call.primary.asIdentifier().name, "print")
    val slicing = call.callTrailer as Slicing
    assertEquals(slicing.primary.asIdentifier().name, "a")
    val sliceList = slicing.sliceList as SliceList
    val firstProperSlice = sliceList[0] as ProperSlice
    assertNull(firstProperSlice.lowerBound)
    assertNull(firstProperSlice.upperBound)
    assertNull(firstProperSlice.stride)
    val secondProperSlice = sliceList[1] as ProperSlice
    assertNull(secondProperSlice.lowerBound)
    assertNull(secondProperSlice.upperBound)
    val stride = secondProperSlice.stride as Stride
    assertNull(stride.expression)
  }

  @Test
  fun sliceListToStringTest () {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/slice/slice_list.py")!!
            .file
    )
    val input = PythonParser()
        .parse(file.path)
        .root
    assertNotNull(input)
    assertEquals(input.toString(), """FileInput(statements=[ImportStatement(
	module=Identifier(
	name='numpy'
)
), AssignmentExpression(
	target=Identifier(
	name='a'
) expression=Call(
	primary=AttributeRef(
	primary=Identifier(
	name='numpy'
)"."identifier=Identifier(
	name='array'
)
) callTrailer=ListDisplay(
	 "[" starredList=Call(
	primary=AttributeRef(
	primary=Identifier(
	name='numpy'
)"."identifier=Identifier(
	name='array'
)
) callTrailer=ListDisplay(
	 "[" starredList=StarredList(
	starredItems=[Integer(
	value=1
), Integer(
	value=2
)]
) "]"
)
) "]"
)
)
), Call(
	primary=Identifier(
	name='print'
) callTrailer=Slicing(
	 primary=Identifier(
	name='a'
) "[" slice_list=SliceList(
	items=[ProperSlice(
	 ":" 
), ProperSlice(
	 ":"  Stride(
	":"
)
)]
)  "]"
)
)])""".replace("\n", System.lineSeparator()))
  }
}