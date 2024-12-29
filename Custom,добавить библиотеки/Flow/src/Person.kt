    data class Person(val name: String, val age: Int, val card: String? = null, val password: String? = null) {
      override fun toString(): String =
        if (card != null && password != null) "Person(name=$name, cart=$card, password=$password)"
          else "Person(name=$name, age=$age)"

}

