import java.io.File

fun main() {
  val medicine: String = File("../19.medicine").readText()
  val replacements: Map<String, CharSequence> = File("../19.input")
                                      .readLines()
                                      .map { it.split(" => ") }
                                      .map { it[0] to it[1] }
                                      .toMap()
  println(medicine)

  val m = mutableMapOf<String, Int>()
  var i = 0
  var j = 1
  while (i < medicine.length) {
    while (j < medicine.length) {
      val ch = medicine.substring(i..j)
      println("$ch .. $i .. $j")
      if (ch in replacements) {
        m[medicine.replaceRange(i, j, replacements[ch])] = 1
        i++
      } else if (!replacements.keys.any { it.startsWith(ch) }) {
        i++
      }
      j++
    }
  }
    
  for (c in medicine) {
    if (Character.toString(c) in replacements) {
      println(c)
    }
  }
}