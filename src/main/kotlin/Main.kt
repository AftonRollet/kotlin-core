fun main(args: Array<String>) {

    var selectedProject = getProjectInput()
    val coffeeMaker = CoffeeMaker()
    val cinemaRoom = Cinema()
    while (selectedProject != 0) {
        when (selectedProject) {
            1 -> coffeeMaker.start()
            2 -> cinemaRoom.start()
        }
        selectedProject = getProjectInput()
    }
}

fun getProjectInput(): Int {
    println("Which project would you like to start?")
    println("1. Coffee Maker")
    println("2. Cinema Room")
    println("0. Exit")
    return readln().toInt()
}

