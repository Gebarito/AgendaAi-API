package com.agendaai.agendaai.utils;

/**
 * OrderInterface
 *
 * This interface contains the possible status of an order
 * to avoid using magic strings in the code.
 *
 */
public interface OrderInterface {
    String PENDING = "pending";
    
    String CANCELED = "canceled";

    String APPROVED = "approved";

    String FAILED = "failed";
}
