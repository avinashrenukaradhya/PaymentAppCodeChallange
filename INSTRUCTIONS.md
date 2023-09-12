# Android refund coding challenge

## Guidelines
Attached is a proof-of-concept Payment App that needs a lot of improvement. Additionally, there's a request to fix a bug ticket that allows users to refund more than the purchase amount. We expect you to spend 2 to 4 hours on this challenge. We don't expect you to fix all the problems that you find in the application but rather, prioritize the most important tasks first given the limited amount of time. In order to remove any bias, we ask you not to add your name into your submission so it can be reviewed anonymously. Finally, once ready to submit, zip the entire project making sure the .git folder is included

## Requirements
There are 2 types of transactions that we support. A Purchase and a Refund.

### Refund rules
- Refers to a Purchase transaction always
- User can only make a Refund transaction if the total accumulated refund amount does not exceed the Purchase transaction amount
- User can make multiple refunds on a Purchase transaction
- A refund is not refundable

An example test case:
1. PurchaseTx id:1 amount: 100.00
2. RefundTx id:2 referenceId:1 amount:50.00 (ok)
3. RefundTx id:3 referenceId:1 amount:49.00 (ok)
3. RefundTx id:4 referenceId:1 amount:1.01 (error)

## What we're looking for
- Refund bug to be fixed
- Improvements to the application
- Good code as per your own definition
- A README that rationalizes how you prioritised the tasks and what other improvements you would like to make given more time

## What we're not looking for
- Fixing all the problems you find in the application
- High fidelity UI (UI is not the focus)
- An over-engineered solution
- Persistence in the application data
- A multi-threaded solution for now