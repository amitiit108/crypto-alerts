Crypto Alerts API

Here's a basic README template for setting up the code and testing the API:
Crypto Alerts API

The Crypto Alerts API allows users to create and manage alerts for cryptocurrency prices. This README provides instructions for setting up the project and testing the API endpoints.

Setup
Prerequisites

    Java Development Kit (JDK) 8 or higher
    Maven
    PostgreSQL
Steps

1. Clone the repository:
    git clone https://github.com/amitiit108/crypto-alerts
2. Navigate to the project directory:
cd crypto-alerts
3. Build the project using Maven:
mvn clean package

4. Set up the PostgreSQL database:
 a. Create a database named crypto_alerts.
 b. Configure the database connection details in the resources/application.properties file.
 c. Create the necessary table by running the following SQL query:
   
   psql -U your_username crypto_alerts < path/to/your/schema.sql



5. Run the application:
a. mvn exec:java -Dexec.mainClass=com.cryptoalerts.Main

API Endpoints

Create Alert

URL: /alerts
Method: POST
Request Body:
{
  "symbol": "BTC",
  "basis": "price",
  "value": 60000,
  "direction": "UP",
  "status": "pending"
}

Response:
{
  "message": "Alert created successfully"
}

Get All Alerts
URL: /alerts
Method: GET
Response:
[
  {
    "id": 1,
    "symbol": "BTC",
    "basis": "price",
    "maLength": null,
    "value": 60000,
    "direction": "UP",
    "status": "pending"
  },
  {
    "id": 2,
    "symbol": "ETH",
    "basis": "price",
    "maLength": null,
    "value": 3000,
    "direction": "UP",
    "status": "pending"
  }
]

Remove Alert
URL: /alerts/{id}
Method: DELETE
Response:
{
  "message": "Alert removed successfully"
}

Testing
You can test the API endpoints using tools like cURL or Postman.

1. Start the application.
2. Use cURL or Postman to send requests to the endpoints mentioned above.

Example cURL command to create an alert:
curl --location --request POST 'http://localhost:8888/alerts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "symbol": "BTC",
    "basis": "price",
    "value": 60000,
    "direction": "UP",
    "status": "pending"
}'


