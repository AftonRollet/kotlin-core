package cinema

class CinemaManager {

    private var currentIncome = 0

    private val room = Room()

    fun start() {
        room.setup()

        var menuSelection = printAndGetUserOption()

        while (menuSelection != 0) {
            when (menuSelection) {
                1 -> room.seatPlan.show()
                2 -> startPurchaseTicketProcess()
                3 -> getStats()
            }
            menuSelection = printAndGetUserOption()
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
        println("Number of purchased tickets: ${room.seatPlan.getNumberOfBookedSeats()}")
        println("Percentage: ${String.format("%.2f", "${room.getPercentOfTicketsSold()}")}%")
        println("Current Income: $$currentIncome")
        println("Total income: $${room.totalPotentialIncome}")
    }

    private fun startPurchaseTicketProcess() {
        var selectedSeatNumber = -1
        var selectedRowNumber = -1
        try {
            println("Enter a row number:")
            selectedRowNumber = readln().toInt()
            println("Enter a seat number in that row:")
            selectedSeatNumber = readln().toInt()
        } catch (e: Exception) {
            println("Wrong Input!")
            println()
            startPurchaseTicketProcess()
        }

        when(val result = room.bookSeat(selectedRowNumber = selectedRowNumber, selectedSeatNumber = selectedSeatNumber)) {
            Room.BookSeatState.InvalidSeat -> {
                println("Wrong Input!")
                println()
                startPurchaseTicketProcess()
            }
            Room.BookSeatState.SeatAlreadyBooked -> {
                println("That ticket has already been purchased!")
                println()
                startPurchaseTicketProcess()
            }
            is Room.BookSeatState.Success -> {
                println("Ticket price: $${result.ticketPrice}")
                currentIncome += result.ticketPrice
            }
        }
    }
}
