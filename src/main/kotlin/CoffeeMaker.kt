private const val ESPRESSO_SELECTED = "1"
private const val LATTE_SELECTED = "2"
private const val CAPPUCCINO_SELECTED = "3"

private const val ESPRESSO_WATER = 250
private const val ESPRESSO_COFFEE = 16
private const val ESPRESSO_COST = 4

private const val LATTE_WATER = 350
private const val LATTE_MILK = 75
private const val LATTE_COFFEE = 20
private const val LATTE_COST = 7

private const val CAPPUCCINO_WATER = 200
private const val CAPPUCCINO_MILK = 100
private const val CAPPUCCINO_COFFEE = 12
private const val CAPPUCCINO_COST = 6

class CoffeeMaker {

    private var water = 400
    private var milk = 540
    private var coffee = 120
    private var cups = 9
    private var money = 550

    fun start() {
        var instruction = ""
        while (instruction != "exit") {
            println("Write action (buy, fill, take, remaining, exit):")
            instruction = readln()
            when (instruction) {
                "buy" -> buyCoffee()
                "fill" -> fillMachine()
                "take" -> takeMoney()
                "remaining" -> printStats()
            }
        }
    }

    private fun printStats() {
        println("The coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$coffee g of coffee beans")
        println("$cups disposable cups")
        println("$$money of money")
    }

    private fun buyCoffee() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
        val selection = readln()

        if (selection == ESPRESSO_SELECTED) {
            if (checkStockLevels(ESPRESSO_WATER, requiredCoffee = ESPRESSO_COFFEE)) {
                println("I have enough resources! Making you a coffee!")
                water -= ESPRESSO_WATER
                coffee -= ESPRESSO_COFFEE
                money += ESPRESSO_COST
                cups--
            }
        } else if (selection == LATTE_SELECTED) {
            if (checkStockLevels(LATTE_WATER, LATTE_MILK, LATTE_COFFEE)) {
                println("I have enough resources! Making you a coffee!")
                water -= LATTE_WATER
                milk -= LATTE_MILK
                coffee -= LATTE_COFFEE
                money += LATTE_COST
                cups--
            }
        } else if (selection == CAPPUCCINO_SELECTED) {
            if (checkStockLevels(CAPPUCCINO_WATER, CAPPUCCINO_MILK, CAPPUCCINO_COFFEE)) {
                println("I have enough resources! Making you a coffee!")
                water -= CAPPUCCINO_WATER
                milk -= CAPPUCCINO_MILK
                coffee -= CAPPUCCINO_COFFEE
                money += CAPPUCCINO_COST
                cups--
            }
        }
    }

    private fun checkStockLevels(requiredWater: Int, requiredMilk: Int = 0, requiredCoffee: Int): Boolean {
        var enoughStock = true
        if (requiredWater > water) {
            println("Sorry, not enough water!")
            enoughStock = false
        } else if (requiredMilk > milk) {
            println("Sorry, not enough milk!")
            enoughStock = false
        } else if (requiredCoffee > coffee) {
            println("Sorry, not enough coffee!")
            enoughStock = false
        }
        return enoughStock
    }

    private fun fillMachine() {
        println("Write how many ml of water you want to add:")
        water += readln().toInt()
        println("Write how many ml of milk you want to add:")
        milk += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        coffee += readln().toInt()
        println("Write how many disposable cups you want to add:")
        cups += readln().toInt()
    }

    private fun takeMoney() {
        println("I gave you $$money")
        money = 0
    }
}