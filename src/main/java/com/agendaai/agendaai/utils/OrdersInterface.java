package com.agendaai.agendaai.utils;

/**
 * OrdersInterface
 *
 * This interface contains the possible status of an order
 * to avoid using magic strings in the code.
 *
 */
public interface OrdersInterface {
    String PENDING = "pending";
    
    String CANCELED = "canceled";

    String APPROVED = "approved";

    String FAILED = "failed";
}
