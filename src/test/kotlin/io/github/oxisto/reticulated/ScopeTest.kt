package io.github.oxisto.reticulated

import org.junit.Test
import java.io.File
import kotlin.test.assertNotNull

class ScopeTest {
  @Test
  fun testImport() {
    val file = File(javaClass.classLoader.getResource("import.py").file)

    val result = PythonParser().parse(file.path)
    assertNotNull(result)

    result.scope.variables
  }
}