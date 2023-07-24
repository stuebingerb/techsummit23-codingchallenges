import java.util.Properties

fun main() {
    Spec().main()
}
class Spec {
    fun main() {
        val properties = Properties()
        properties.load(javaClass.getResourceAsStream ("/spec.properties"))

        val challenge = Challenge()

        properties.entries.filter { it.key.toString().endsWith("input") }.forEach {
            val result = challenge.weightedSort(it.value.toString())
            val resultLines = result.lines().toList()

            val expected = properties.getProperty(it.key.toString().replace("input", "expected"))
            val expectedLines = expected.split(" ").filterNot { it.isEmpty() }
            if (resultLines != expectedLines) {
                throw Exception("Mismatch")
            }
        }

        println("📯 It's a match! 🐝📝")
    }
}
