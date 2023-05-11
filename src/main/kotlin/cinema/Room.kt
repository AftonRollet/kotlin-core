package cinema

class Room() {

    sealed class BookSeatState {
        object InvalidSeat : BookSeatState()
        object SeatAlreadyBooked : BookSeatState()
        data class Success(val ticketPrice: Int) : BookSeatState()
    }

    val totalPotentialIncome: Int
        get() {
            return if (capacity > 60) {
                val incomeFrontSection = (seatsPerRow * 10) * rowsHalved
                val incomeBackSection = (seatsPerRow * 8) * (rows - rowsHalved)
                (incomeFrontSection + incomeBackSection)
            } else {
                (seatsPerRow * 10) * rows
            }
        }

    private var rows = 0
    private var rowsHalved = 0
    private var seatsPerRow = 0

    val seatPlan by lazy { SeatPlan() }

    private val capacity
        get() = rows * seatsPerRow

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
    }

    fun bookSeat(selectedRowNumber: Int, selectedSeatNumber: Int): BookSeatState {
        return if (seatPlan.isSeatAlreadyTaken(selectedRowNumber, selectedSeatNumber)) {
            BookSeatState.SeatAlreadyBooked
        } else if (isSeatNumberValid(selectedRowNumber, selectedSeatNumber)) {
            BookSeatState.InvalidSeat
        } else {
            seatPlan.bookSeat(selectedRowNumber - 1, selectedSeatNumber)
            val ticketPrice = calculateTicketPrice(selectedRowNumber)
            BookSeatState.Success(ticketPrice)
        }
    }

    private fun isSeatNumberValid(selectedRowNumber: Int, selectedSeatNumber: Int): Boolean {
        return selectedRowNumber == 0 || selectedRowNumber > rows || selectedSeatNumber > seatsPerRow
    }

    private fun calculateTicketPrice(selectedRowNumber: Int): Int {
        val highPrice = 10
        val lowPrice = 8
        return if (capacity <= 60) {
            highPrice
        } else {
            if (selectedRowNumber > rowsHalved) {
                lowPrice
            } else {
                highPrice
            }
        }
    }

    fun getPercentOfTicketsSold(): Double {
        val ticketsSold = seatPlan.getNumberOfBookedSeats()
        return (ticketsSold.toDouble() / capacity.toDouble()) * 100.0
    }

    inner class SeatPlan() {

        private val rowsAxis = mutableListOf<MutableList<String>>()
        private val seatsAxis = mutableListOf<String>()

        init {
            compileSeatPlan()
        }

        private fun compileSeatPlan() {
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
        }

        fun show() {
            println("Cinema:")
            print("  ")
            println(seatsAxis.joinToString(separator = " "))
            for (x in rowsAxis) {
                println(x.joinToString(separator = " "))
            }
        }

        fun bookSeat(selectedRowNumber: Int, selectedSeatNumber: Int) {
            seatPlan.rowsAxis[selectedRowNumber - 1][selectedSeatNumber] = "B"
        }

        fun isSeatAlreadyTaken(selectedRowNumber: Int, selectedSeatNumber: Int): Boolean {
            return rowsAxis[selectedRowNumber - 1][selectedSeatNumber] == "B"
        }

        fun getNumberOfBookedSeats(): Int {
            var count = 0
            seatPlan.rowsAxis.forEach { list ->
                count += list.count { it == "B" }
            }
            return count
        }
    }
}


