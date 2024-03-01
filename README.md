This API calculates customers' reward points for a retailer's rewards program based on their historical transaction records.
Customer's historical transaction records are stored in MySQL database, and table fields include transaction_id, user_id, transaction_amount, transaction_date
The reward points conversion rule is as follows: A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction (e.g., a $120 purchase = 2x$20 + 1x$50 = 90 points).
This API also provides other basic CRUD operations.
