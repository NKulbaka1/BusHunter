package ru.kulbaka.bushunter.exception;

public class TicketAlreadyPurchasedException extends RuntimeException {

    public TicketAlreadyPurchasedException(Long ticketId) {
        super("Билет с айди " + ticketId + " уже выкуплен");
    }
}
