package cinema

class CinemaManager {

    private var currentIncome = 0

    private val room = Room()

    private val totalPotentialIncome: Int
        get() {
            return if (room.capacity > 60) {
                val incomeFrontSection = (room.seatsPerRow * 10) * room.rowsHalved
                val incomeBackSection = (room.seatsPerRow * 8) * (room.rows - room.rowsHalved)
                (incomeFrontSection + incomeBackSection)
            } else {
                (room.seatsPerRow * 10) * room.rows
            }
        }

    fun start() {
        room.setup()
        currentIncome = 0

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
        println("Percentage: ${String.format("%.2f", room.getPercentOfBookedSeats())}%")
        println("Current Income: $$currentIncome")
        println("Total Income: $${totalPotentialIncome}")
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

        when(room.bookSeat(selectedRowNumber = selectedRowNumber, selectedSeatNumber = selectedSeatNumber)) {
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
                val ticketPrice = calculateTicketPrice(selectedRowNumber)
                println("Ticket price: $${ticketPrice}")
                currentIncome += ticketPrice
            }
        }
    }
    private fun calculateTicketPrice(selectedRowNumber: Int): Int {
        val highPrice = 10
        val lowPrice = 8
        return if (room.capacity <= 60) {
            highPrice
        } else {
            if (selectedRowNumber > room.rowsHalved) {
                lowPrice
            } else {
                highPrice
            }
        }
    }
}
