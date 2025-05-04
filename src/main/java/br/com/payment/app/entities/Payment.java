package br.com.payment.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import static java.math.BigDecimal.ZERO;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethodId;

    @Column(name = "payment_value")
    private BigDecimal value = ZERO;

    @Column(name = "payment_identifier")
    private String paymentIdentifier;

    @Column(name = "order_id")
    private String orderId;

    private String status;

    @Column(name = "notification_date")
    private String notificationDate;

    @Override
    public String toString() {
        return "Id " + id;
    }
}
