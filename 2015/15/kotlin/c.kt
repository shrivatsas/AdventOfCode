import java.io.File
import kotlin.math.max

data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int)


fun main() {
  val lines: List<String> = File("../15.input").useLines { it.toList() }

  // Sugar: capacity 3, durability 0, flavor 0, texture -3, calories 2
  val regex = Regex("""(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""")
  val ingredients = lines.map { line ->
    val (name, capacity, durability, flavor, texture, calories) = regex.matchEntire(line)!!.destructured
    Ingredient(name, capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt())
  }

  var max_score = recursiveCalculateScore(ingredients, 100)
  println(max_score)
}

fun calc(ingredient: Ingredient, amount: Int): Int {
  return max(0, amount * ingredient.capacity) * max(0, amount * ingredient.durability) * max(0, amount * ingredient.flavor) * max(0, amount * ingredient.texture)
}

fun recursiveCalculateScore(ingredients: List<Ingredient>, remaining: Int): Int {
  println("${ingredients.size}, ${remaining}")
  if (ingredients.size == 1) {
    val m = calc(ingredients[0], remaining)
    println(m)
    return m
  }

  readLine()
  var max_score = 0
  for (i in 0..remaining) {
    val score = calc(ingredients[0], i)
    val rem_score = recursiveCalculateScore(ingredients.drop(1), remaining - i)
    max_score = max(score + rem_score, max_score)
  }
  return max_score
}