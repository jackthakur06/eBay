eBay & CoinGecko Automation AssessmentThis repository contains automated test scripts for both UI and API validation as part of a technical assessment.

## Project Structure 
UI Automation: 
Ebay.java - A Selenium-based script that automates the product search and "Add to Cart" flow on eBay.com.API Automation: 
CoinGeckoApiTest.java - A RestAssured-based script that validates cryptocurrency market data from the CoinGecko API.

## Features### 
1. eBay UI Test (Ebay.java)
2. Stealth Configuration: Uses specific ChromeOptions to bypass bot detection mechanisms.
3. Dynamic Search: Automates searching for "book", selecting the first item, and handling multi-tab navigation.
4. Verification: Confirms the item is successfully added to the cart by validating the final URL and cart bucket visibility.
  
6.  CoinGecko API Test (CoinGeckoApiTest.java)
7.  Status Validation: Ensures the API returns a 200 OK status code.
8.  Currency Verification: Confirms that market data exists for USD, GBP, and EUR.
9.  Metric Validation: Verifies that market_cap, total_volume, and price_change_percentage_24h are present and valid.
10.
11.  ## Prerequisites
12.  To run these tests locally, you will need:
13.  Java SDK (Version 11 or higher)
14.  Maven (for dependency management)
15.  Google Chrome (for UI tests)
16.
17.  ## How to RunClone the repository:Bash
18.git clone https://github.com/jackthakur06/eBay.git
Install Dependencies:Ensure your pom.xml includes Selenium, RestAssured, and TestNG.
Execute Tests:Run Ebay.main() to execute the UI flow.
Run CoinGeckoApiTest.main() to execute the API validations.
