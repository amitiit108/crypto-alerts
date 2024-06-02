import React, { useState } from "react";
import useWebSocket from "react-use-websocket";
import "./Notification.css"; // Import CSS file

const WS_URL = process.env.SOCKET_URL || 'ws://127.0.0.1:8080';

const Notification = () => {
  const [error, setError] = useState("");

  const { lastMessage, readyState } = useWebSocket(WS_URL, {
    onOpen: () => {
      console.log("WebSocket connection established.");
    },
    onClose: () => {
      console.log("WebSocket connection closed.");
    },
    onError: (event) => {
      console.error("WebSocket error:", event);
      setError("WebSocket connection error.");
    },
    retryOnError: true,
    shouldReconnect: () => true,
  });

  return (
    <>
      {error && <div className="error">{error}</div>}
      {lastMessage && <div className="notification">{lastMessage.data}</div>}
    </>
  );
};

export default Notification;
