package cinema

class Room() {

    sealed class BookSeatState {
        object InvalidSeat : BookSeatState()
        object SeatAlreadyBooked : BookSeatState()
        object Success : BookSeatState()
    }

    var rows = 0
        private set
    var rowsHalved = 0
        private set
    var seatsPerRow = 0
        private set

    var seatPlan = SeatPlan()
        private set

    val capacity
        get() = rows * seatsPerRow

    fun setup() {
        println("Enter the number of rows:")
        rows = readln().toInt()
        println("Enter the number of seats in each row:")
        seatsPerRow = readln().toInt()

        rowsHalved = rows / 2
        seatPlan = SeatPlan()
    }

    fun bookSeat(selectedRowNumber: Int, selectedSeatNumber: Int): BookSeatState {
        return if (seatPlan.isSeatAlreadyTaken(selectedRowNumber, selectedSeatNumber)) {
            BookSeatState.SeatAlreadyBooked
        } else if (isSeatNumberInvalid(selectedRowNumber, selectedSeatNumber)) {
            BookSeatState.InvalidSeat
        } else {
            seatPlan.bookSeat(selectedRowNumber, selectedSeatNumber)
            BookSeatState.Success
        }
    }

    private fun isSeatNumberInvalid(selectedRowNumber: Int, selectedSeatNumber: Int): Boolean {
        return selectedRowNumber == 0 || selectedRowNumber > rows || selectedSeatNumber > seatsPerRow
    }


    fun getPercentOfBookedSeats(): Double {
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
                for (y in 1..seatsPerRow) {
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


