package cinema

const val frontRowTicketPrice = 10
const val backRowTicketPrice = 8
var numberOfRows = 0
var numberOfSeatsEachRow = 0
val seats = mutableListOf<MutableList<String>>()
var income = 0

fun printSeats() {
    println("\nCinema:")
    println("  ${(1..seats.first().size).joinToString(" ")}")
    seats.forEachIndexed { index, seat ->
        println("${index + 1} ${seat.joinToString(" ")}")
    }
}

fun printMenu() {
    println("\n1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun buyTicket() {
    while (true) {
        println("\nEnter a row number:")
        val rowNumber = readln().toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readln().toInt()

        try {
            if (isSeatsAvailable(rowNumber, seatNumber)) {
                seats[rowNumber - 1][seatNumber - 1] = "B"

                val ticketPrice = if (getTotalSeats() < 60) {
                    frontRowTicketPrice
                } else {
                    val frontRows = numberOfRows / 2
                    if (rowNumber in 1..frontRows) frontRowTicketPrice else backRowTicketPrice
                }

                income += ticketPrice

                println("Ticket price: \$$ticketPrice")
                break
            } else {
                println("That ticket has already been purchased!")
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        }
    }
}

fun getTotalSeats(): Int = numberOfRows * numberOfSeatsEachRow

fun populateSeats() {
    for (i in 0 until numberOfRows) {
        val rows = mutableListOf<String>()
        for (j in 0 until numberOfSeatsEachRow) {
            rows.add("S")
        }
        seats.add(rows)
    }
}

fun isSeatsAvailable(rowNumber: Int, seatNumber: Int): Boolean = seats[rowNumber - 1][seatNumber - 1] == "S"

fun statistics() {
    val totalIncome = if (getTotalSeats() < 60) {
        getTotalSeats() * frontRowTicketPrice
    }  else {
        val frontRows: Int = numberOfRows / 2
        val backRows =  numberOfRows - frontRows
        (frontRows * numberOfSeatsEachRow * 10) + (backRows  * numberOfSeatsEachRow * 8)
    }

    val takenSeats = seats.sumOf { row ->
        row.count { seat -> seat == "B" }
    }

    val percentage = takenSeats * 100.0 / getTotalSeats()
    val formatPercentage = "%.2f".format(percentage)

    println("\nNumber of purchased tickets: $takenSeats")
    println("Percentage: $formatPercentage%")
    println("Current income: \$$income")
    println("Total income: \$$totalIncome")
}

fun main() {
    println("Enter the number of rows:")
    numberOfRows = readln().toInt()
    println("Enter the number of seats in each row:")
    numberOfSeatsEachRow = readln().toInt()

    populateSeats()

    var showMenu = true
    while(showMenu) {
        printMenu()

        val selection = readln().trim().toInt()
        when (selection) {
            1 -> printSeats()
            2 -> buyTicket()
            3 -> statistics()
            else -> showMenu = false
        }
    }
}