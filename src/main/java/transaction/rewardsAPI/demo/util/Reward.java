package transaction.rewardsAPI.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
    private long userId;
    private long transactionId;
    private double rewardPoint;
    private Date rewardDate;
}
