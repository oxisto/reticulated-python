package io.github.oxisto.reticulated.ast.expression

/**
 * A primary
 *
 * Reference: https://docs.python.org/3/reference/expressions.html#primaries
 */
abstract class Primary : Expression() {

  fun asIdentifier(): Identifier {
    return this as Identifier
  }

}