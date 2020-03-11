package io.github.oxisto.reticulated.expression

import io.github.oxisto.reticulated.PythonParser
import org.junit.Test
import java.io.File
import kotlin.test.assertNotNull

class ArithmeticTest {
  @Test
  fun testAdditiveExpressions() {
    val file = File(
        javaClass
            .classLoader
            .getResource("expressions/arithmetic/additive.py")!!
            .file
    )

    val input = PythonParser()
        .parse(file.path)
        .root

    assertNotNull(input)
  }
}