package transaction.rewardsAPI.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import transaction.rewardsAPI.demo.entity.Transaction;
import transaction.rewardsAPI.demo.exception.TransactionException;
import transaction.rewardsAPI.demo.exception.TransactionNotFoundException;
import transaction.rewardsAPI.demo.service.TransactionService;
import transaction.rewardsAPI.demo.util.ErrorResponse;


import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RewardsController {

    TransactionService transactionService;

    @Autowired
    public RewardsController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    //reward points within 3 months
    @GetMapping("/rewardsin3months")
    public ResponseEntity<Map<Long, Map<YearMonth, Double>> > getReward(){
        Map<Long, Map<YearMonth, Double>> rewards = transactionService.findRewardsByUserandMonth();
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }

    //get all transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransaction(){
        List<Transaction> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    //get one transaction by transactionId
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable long id) throws TransactionException{
        Transaction transaction = transactionService.findById(id);
        if(transaction == null){
            throw new TransactionNotFoundException("TRANSACTION_NOT_FOUND");
        }
       return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    //post a new transaction
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@Validated @RequestBody Transaction transaction){
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.OK);
    }

    //update a transaction
    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable long id, @RequestBody Transaction transaction){
        if(transactionService.findById(id) == null){
            throw new TransactionNotFoundException("TRANSACTION_NOT_FOUND");
        }
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    //delete a transaction
    @DeleteMapping("/transactions/{id}")
    public String deleteTransaction(@PathVariable long id){
        if(transactionService.findById(id) == null){
            throw new TransactionNotFoundException("TRANSACTION_NOT_FOUND");
        }
        transactionService.deleteTransaction(id);
        return "This Transaction has been Deleted!";
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException(Exception ex) {
        //logger.error("Cannot find user");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
