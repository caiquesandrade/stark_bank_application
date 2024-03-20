## READE ME - CLI BANK APPLICATION ##
### === STARK BANK CLI APPLICATION === ##

### #1 REQUIREMENTS:
- JDK 17 installed
- IDE (IntelliJ, Eclipse, VSCode, etc)

### #2 HOW IT WORKS:
We have 6 main functionalities for on this project.

They are:

1 - Create a new account

2 - Make a deposit

3 - Check the amount info

4 - Do withdraw

5 - Transfer the money

6 - Print a statement

7 - Exit the program

To start the program we need run the main class called "Program".
When it starts, will be printed on the terminal the options to be selected.

- Create a new account -> This option is responsible to create a runtime new Account object it's composed by account name with max of 60 digits, account number with 6 digits, bank code with 4 digits, check digits with 2 digits, initial amount value on the account and account transfer limit that set the max amount allowed between 20:00PM till 6:00AM.
- Make a deposit -> With a created user, you can deposit the desired amount.
- Check the amount info -> This option return for the user the total amount on the account.
- Do withdraw -> If you want to get some money from the account, this is the right option for you.
- Transfer the money -> This option requires at least two account created because once select this option, the application will ask you inform the ORIGIN account (account that will get the money) and the DESTINATION account (account that will receive the money) and then you type the desired amount.
    - NOTE: Transfer money first will check if you are on the security period, making sure that you are doing the transaction after the 8PM and before 6AM, if is not into this period, the MAX limit is configured on the user account, if not passed the DEFAULT value is $1000.
- Print a statement -> Do you want check all transactions' history? So use this option to take a look on the statement history since account creation.
- Exit the program -> Time to say goodbye and close the application losing all runtime object created.
    - NOTE: When the application is closed, an CSV file is created with whole history. This file title is based on "accounNumber_accountName.csv".

### #3 DIAGRAM:
![mermaid-diagram-2024-03-19-201646](https://github.com/caiquesandrade/stark_bank_application/assets/48366009/8e049a98-8a1b-44f7-81e3-8090995d3149)

### #4 BACKLOG:
- Create a new class to handle the statements files.
- Add method to check if object Account exists before work with that.
- Use the Account data to create an IBAN code that allows international transference's (buy dolar and euro).
- Create credit and debit card area.
- Separate users rules and levels as Administrators, Managers, Customer, etc.


