package transaction.rewardsAPI.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import transaction.rewardsAPI.demo.entity.Transaction;
import transaction.rewardsAPI.demo.repository.TransactionRepository;
import transaction.rewardsAPI.demo.util.Reward;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    TransactionRepository transactionRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

//    return all transaction records within 3 months
    public List<Transaction> findTransactionsLast3Months(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthAgo = calendar.getTime();
        return transactionRepository.findByTransactionDateAfter(threeMonthAgo);
    }

//    get rewards within 3 months
    public List<Reward> findRewardsLast3Months(){
        List<Reward> rewards = new ArrayList<>();
        List<Transaction> transactions = findTransactionsLast3Months();
        for(Transaction transaction: transactions){
            double rewardPoint = convertRewardPoint(transaction.getAmount());
            rewards.add(
                    new Reward(transaction.getUserId(), transaction.getTransactionId(),
                            rewardPoint, transaction.getTransactionDate()));
        }
        return rewards;
    }

//    get rewards groupby userid and month
    public Map<Long, Map<YearMonth, Double>> findRewardsByUserandMonth(){
        List<Reward> rewards = findRewardsLast3Months();
        Map<Long, Map<YearMonth, Double>> res = rewards.stream()
                .collect(Collectors.groupingBy(Reward::getUserId,
                        Collectors.groupingBy(Reward -> getYearMonthFromDate(Reward.getRewardDate()),
                                Collectors.summingDouble(Reward::getRewardPoint)
                        )));
        return res;
    }

    public List<Transaction> findAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction findById(long id){
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(long id){
        transactionRepository.deleteById(id);
    }

    public Transaction updateTransaction(long id, Transaction transaction){
        Transaction currentTransaction = transactionRepository.findById(id).orElse(null);
        currentTransaction.setUserId(transaction.getUserId());
        currentTransaction.setAmount(transaction.getAmount());
        currentTransaction.setTransactionDate(transaction.getTransactionDate());
        transactionRepository.save(currentTransaction);
        return currentTransaction;
    }

    private YearMonth getYearMonthFromDate(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return YearMonth.from(localDate);
    }

    private double convertRewardPoint(double transactionAmount){
        double res = 0;

        double beyondHundred = (transactionAmount - 100)>0? transactionAmount-100:0;
        if(beyondHundred > 0){
            transactionAmount -= beyondHundred;
            res += beyondHundred*2;
        }
        double beyondFifty = (transactionAmount - 50)>0? transactionAmount-50:0;
        if(beyondFifty > 0){
            transactionAmount -= beyondFifty;
            res += beyondFifty;
        }

        return res;
    }
}
