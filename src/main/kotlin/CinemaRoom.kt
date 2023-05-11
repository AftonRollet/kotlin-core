class Cinema {
    private var rowNumber = 0
    private var seatsPerRow = 0

    private var seatsAxis = mutableListOf<String>()
    private var rowsAxis = mutableListOf<MutableList<String>>()

    private val capacity
        get() = rowNumber * seatsPerRow
    private var rowsHalved = 0
    private var totalIncome = 0
    private var currentIncome = 0

    private var selectedRowNumber = 0
    private var selectedSeatNumber = 0

    private var numOfPurchasedTickets = 0
    private val percentOfTicketsSold: Double
        get() = (numOfPurchasedTickets.toDouble() / capacity.toDouble()) * 100.0

    private var rows = 0

    fun start() {
        setup()

        var menuSelection = printAndGetUserOption()

        while (menuSelection != 0) {
            when (menuSelection) {
                1 -> showSeatPlan()
                2 -> buyTicket()
                3 -> getStats()
            }
            menuSelection = printAndGetUserOption()
        }
    }

    fun setup() {
        println("Enter the number of rows:")
        rows = readln().toInt()
        println("Enter the number of seats in each row:")
        seatsPerRow = readln().toInt()

        val divisor = rows % 2
        if (divisor == 0) {
            rowsHalved += rows / 2
        } else {
            rowsHalved += (rows / 2) - (divisor / 2)
        }

        val seatPlanAxis = compileSeatPlan()
        seatsAxis = seatPlanAxis.first
        rowsAxis = seatPlanAxis.second
        calculateTotalIncome()
    }

    private fun compileSeatPlan(): Pair<MutableList<String>, MutableList<MutableList<String>>> {
        for (x in 1..seatsPerRow) {
            seatsAxis.add(x.toString())
        }
        for (x in 1..rows) {
            val currentRow = mutableListOf<String>()
            currentRow.add(x.toString())
            for (x in 1..seatsPerRow) {
                currentRow.add("S")
            }
            rowsAxis.add(currentRow)
        }
        return Pair(seatsAxis, rowsAxis)
    }

    private fun calculateTotalIncome() {
        if (capacity > 60) {
            val incomeFrontSection = (seatsPerRow * 10) * rowsHalved
            val incomeBackSection = (seatsPerRow * 8) * (rows - rowsHalved)
            totalIncome += (incomeFrontSection + incomeBackSection)
        } else {
            totalIncome += (seatsPerRow * 10) * rows
        }
    }

    private fun printAndGetUserOption(): Int {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        return readln().toInt()
    }

    private fun getStats() {
        println("Number of purchased tickets: $numOfPurchasedTickets")
        println("Percentage: ${String.format("%.2f", percentOfTicketsSold)}%")
        println("Current Income: $$currentIncome")
        println("Total income: $$totalIncome")
    }

    private fun showSeatPlan() {
        println("Cinema:")
        print("  ")
        println(seatsAxis.joinToString(separator = " "))
        for (x in rowsAxis) {
            println(x.joinToString(separator = " "))
        }
    }

    private fun calculateTicketPrice(): Int {
        val highPrice = 10
        val lowPrice = 8
        if (capacity <= 60) {
            return highPrice
        } else {
            if (selectedRowNumber > rowsHalved) {
                return lowPrice
            } else {
                return highPrice
            }
        }
    }

    private fun buyTicket() {
        try {
            println("Enter a row number:")
            selectedRowNumber = readln().toInt()
            println("Enter a seat number in that row:")
            selectedSeatNumber = readln().toInt()
        } catch (e: Exception) {
            println("Wrong Input!")
            println()
            buyTicket()
        }

        if (selectedRowNumber == 0 || selectedRowNumber > rowNumber) {
            println("Wrong Input!")
            println()
            buyTicket()
        } else if (selectedSeatNumber == 0 || selectedSeatNumber > seatsPerRow) {
            println("Wrong Input!")
            println()
            buyTicket()
        } else {
            var ticketPosition = rowsAxis[selectedRowNumber - 1][selectedSeatNumber]
            if (ticketPosition == "B") {
                println("That ticket has already been purchased!")
                println()
                buyTicket()
            } else {
                val ticketPrice = calculateTicketPrice()
                println("Ticket price: $$ticketPrice")
                numOfPurchasedTickets += 1
                currentIncome += ticketPrice

                rowsAxis[selectedRowNumber - 1][selectedSeatNumber] = "B"
            }
        }
    }

    private inner class CinemaRoomSetup() {

    }

}


