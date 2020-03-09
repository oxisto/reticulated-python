package io.github.oxisto.reticulated.ast

import java.lang.Exception

class CouldNotParseException(override val message: String? = "") : Exception(message) {
}