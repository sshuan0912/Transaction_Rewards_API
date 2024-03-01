package transaction.rewardsAPI.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "user_id")
    @NotNull(message = "User Id cannot be null")
    private Long userId;
    @Column(name = "amount")
    @NotNull(message = "amount cannot be null")
    private Double amount;
    @Column(name = "transaction_date")
    @NotNull(message = "transaction date be null")
    private Date transactionDate;
}
