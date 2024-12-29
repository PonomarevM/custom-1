import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random




fun createCard(): String {
    val digits = (1..9999).map { it.toString().padStart(4, '0') }.joinToString(" ")
    return "2981 $digits" // Пример префикса
}


fun createPassword(): String {
    val characters = "abcdefghijklmnopqrstuvwxyz0123456789"
    return (1..4).joinToString("") { characters[Random.nextInt(characters.length)].toString() }
}


fun sumOfSquares(numbers: List<Int>): Int = numbers.map { it * it }.reduce { sum, value -> sum + value }


fun Flow<Person>.getPerson(first: String, age: Int): Flow<Person> =
    filter {
        it.name.startsWith(first, ignoreCase = true) && it.age == age
    }


suspend fun <T1, T2, T3, R> combineFlows(
    first: Flow<T1>,
    second: Flow<T2>,
    third: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> =
    first.zip(second) { t1, t2 -> t1 to t2 }
        .zip(third) { (t1, t2), t3 -> transform(t1, t2, t3) }

fun main() = runBlocking {

    val names = listOf("Петр", "Николай", "Василий", "Иван", "Мария", "Анастасия", "Дмитрий", "Елена", "Сергей", "Ольга")
    val ages = listOf(25, 30, 28, 35, 22, 29, 31, 27, 33, 26)

    val persons = mutableListOf<Person>()

    val flowPersons = flow {
        for(i in names.indices) {
            emit(Person(names[i],ages[i]))
        }
    }


    val numbers = listOf(2, 4, 6)
    val sum = sumOfSquares(numbers)
    println("Сумма квадратов $numbers : $sum")



    print("Введите первый символ имени для поиска: ")
    val first = readln().trim()
    print("Введите возраст для поиска: ")
    val age = readln().toIntOrNull() ?: 0


    flowPersons.getPerson(first,age).collect{
        println("Найденный Person: $it")
    }



    val flowNames = flowOf(*names.toTypedArray())
    val flowCards = flow {
        repeat(names.size) {
            emit(createCard())
        }
    }.flowOn(Dispatchers.IO)

    val flowPasswords = flow {
        repeat(names.size) {
            emit(createPassword())
        }
    }.flowOn(Dispatchers.IO)


    val employees = mutableListOf<Person>()
    combineFlows(flowNames, flowCards, flowPasswords) { name, card, password ->
        Person(name = name, age = 0, card = card, password = password)
    }.collect { person ->
        employees.add(person)
    }

    println("Сотрудники: \n${employees.joinToString(separator = "\n")}")

}