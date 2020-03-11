package io.github.oxisto.reticulated

class TestUtils {
  companion object {
    fun beautifyResult(input: String): String{
      var result = String()
      var count = -1
      for ( line in input.split( System.lineSeparator() ) ) {
        val isClosingBracket = line.isNotEmpty() && line[0] == ')'
        if ( isClosingBracket ) {
          count --
        }
        var tmp = count
        while ( tmp > 0 ) {
          result += "\t"
          tmp--
        }
        result += line + System.lineSeparator()
        if ( !isClosingBracket ) {
          count++
        }
      }
      return result
    }
  }
}