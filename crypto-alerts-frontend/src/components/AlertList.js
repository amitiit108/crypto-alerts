import React, { useState, useEffect } from 'react';
import { deleteAlert } from '../api';
import './AlertList.css'; // Import CSS file

const AlertList = ({ alerts, onDelete }) => {
  const [error, setError] = useState('');
  const [reload, setReload] = useState(false);

  useEffect(() => {
    if (reload) {
      // Reload alerts here, you can fetch them again from the API or update the existing list
      setReload(false); // Reset reload state after reloading alerts
    }
  }, [reload]);

  const handleDelete = async (id) => {
    try {
      await deleteAlert(id);
      onDelete(id);
      setError('');
      setReload(true); // Trigger reload after deleting an alert
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <>
      {error && <p className="error">{error}</p>}
      <ul className="alert-list">
        {alerts.map((alert) => (
          <li key={alert.id}>
            <span>{alert.symbol} - {alert.basis} - {alert.value} - {alert.direction} - {alert.status}</span>
            <button onClick={() => handleDelete(alert.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </>
  );
};

export default AlertList;
