# ReadMe

## Bugs

### Input data validation for refund and purchase
- Validate the user inputs (Purchase and refund)
- Check for invalid user input.
- Limit user input to Alpha Numeric by using "Number Decimal" input type.
- Show appropriate User message for error, success and invalid transaction.
- Clear input field once the user has a successful transaction.
- Hide the keyboard after user input is completed.

### Fixed refund logic
- Fix the refund logic which had a excess refund bug
- Fetch the purchase using transactionId provided by the argument.
- Check if the requested refund amount is not greater then the purchase amount.
- If check fails return invalid.
- If the check passes then aggregate all the previous refund amount by iterating and calculating the sum.
- After the sum of all the refund is calculated add it with the requested refund amount and determine if it does not exceed the purchase value.
- If it exceeds the purchase value then return invalid
- if the total is lesser then or equal to the return value then add the new refund object to the refund list and respond with the valid retrun value.

## Architecture Improvements

- Implemented MVVM Architecture for better readable code.
- Clean Architecture was followed for better segregation of code.

### Data Layer
- Contains Repository Implementation.
- Contains the service that provides the transaction data
- Has data classes to Purchase Refund and Transaction

### Domain Layer
- Contains the Repository Interface that hold the contract for repository implementation in Data layer
-  Usecase for Purchase Refund and Transaction which holds all the usecases that is performed by the user

### UI (Presentation)
- Segregated adapter class for recycle view
- Sealed state classes to represent UI for refund and purchase screen
- View model class that interacts with usecase for user action performed on UI.

## DI (dependency injection)
- Implemented DI using HILT Desktop library
- All providers are implemented as part of `PaymentModule` class
- Classes in domain data and UI are created using DI.

## Unit Testing
- Have implemented unit test for `TransactionService` with 85% coverage
- ViewModel class also has unit test cases associated with it `MainActivityViewModelTest` which has more then 85% test coverage

## Improvisation
- Add Unit test for UI classes
- We can improve the UI performance with the help of the new Jetpack compose library.
- Write unit test for Navigation 