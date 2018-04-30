# Java-Json: Transactions Software


## Run software
To run the application you need to follow next steps:
1. Download app.zip
2. Unzip app.zip (You will get now dist folder)
3. Open your command line and go to dist directory
4. run the next command ``java -jar "TransactionsSoftware.jar" commands``

### Commands
Always you run the app you need to write what command you want to execute, these are the commands:

``user_id add transaction_json``

This is the main command because we have to add data to run the next commands. ``transaction_json`` needs to be formated in this way:

``{ "amount": 1.23, "description": "Joes Tacos", "date":"2018-12-30", "user_id": 345 }``


``user_id sum``

This sums all the transactions amount of one user.


``user_id list``

It shows all the transactions of an user.


``user_id transaction_id``

This command returns one transaction.


## Code
To run code you need to meet two requirements:
1. Add json-simple library to your project.
2. Create an empty file with the name 0File.json in your project folder.

Note: All these files are included in the src folder.
