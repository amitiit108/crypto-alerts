import React, { useState, useEffect } from 'react';
import { getAlerts, createAlert as addAlertApi, deleteAlert as deleteAlertApi } from './api';
import AlertForm from './components/AlertForm';
import AlertList from './components/AlertList';
import Notification from './components/Notification';
import './App.css'; // Import the CSS file

const App = () => {
  const [alerts, setAlerts] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchAlerts = async () => {
      try {
        const fetchedAlerts = await getAlerts();
        setAlerts(fetchedAlerts);
        setError('');
      } catch (err) {
        setError(err.message);
      }
    };

    fetchAlerts();
  }, []);

  const handleAddAlert = async (alert) => {
    try {
      // Add the new alert
      await addAlertApi(alert);
      // Refetch the alerts
      const updatedAlerts = await getAlerts();
      setAlerts(updatedAlerts);
      setError('');
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteAlert = async (id) => {
    try {
      // Delete the alert
      await deleteAlertApi(id);
      // Refetch the alerts
      const updatedAlerts = await getAlerts();
      setAlerts(updatedAlerts);
      setError('');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      <h1>Crypto Alerts</h1>
      {error && <p className="error">{error}</p>}
      <Notification />
      <AlertForm onAdd={handleAddAlert} />
      <AlertList alerts={alerts} onDelete={handleDeleteAlert} />
    </div>
  );
};

export default App;
