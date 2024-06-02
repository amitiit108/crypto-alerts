# React App README

## Description

This is a React application for managing price alert and notification back-end application.

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/amitiit108/crypto-alerts

2. Install dependencies:

cd crypto-alerts-frontend
npm install

3. Create a .env file in the root directory and define your environment variables:

API_BASE_URL=http://localhost:8888

SOCKET_URL=ws://127.0.0.1:8080

4. Start the development server:

npm start

5. Open your browser and visit http://localhost:3000 to view the app.

Usage

    Use the AlertForm component to create new alerts.
    Use the AlertList component to view and delete existing alerts.
    Use the Notification component to receive WebSocket notifications.

Dependencies

    React
    axios
    react-use-websocket


Environment Variables

    API_BASE_URL: Base URL of the API server.
    SOCKET_URL : Socket Base URL to recieve realtime alerts


