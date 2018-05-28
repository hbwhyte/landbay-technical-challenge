# Landbay Technical Challenge
Built by Heather Whyte

## Setup
All necessary dependencies and files should be included within the repository. The CSVs are 
located at
``` landbay-technical-challenge/landbay-prototype/src/main/resources/inputs/ ```
and can be swapped out there to test with different data sets. 

## Endpoints
The main endpoints can all be found at the MainApplication.java:
  - **Print Loans**: Takes in raw CSV input from disk, and ouputs loans in formatted JSON
  - **Print Investment Requests**: Takes in raw CSV input from disk, and ouputs investment requests 
in formatted JSON
  - **Print Matched Loans**: Based on the give loans and requests, and the stated parameters, it 
outputs the fully funded matched loans, along with the investors and amount invested.

## Built With
 - Java 8
 - Maven
 - OpenCSV
 - Gson
 - Junit/Mockito

