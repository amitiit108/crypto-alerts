import React, { useState } from 'react';
import { createAlert } from '../api';
import './AlertForm.css'; // Import CSS file

const AlertForm = ({ onAdd }) => {
  const [symbol, setSymbol] = useState('');
  const [basis, setBasis] = useState('price');
  const [value, setValue] = useState('');
  const [direction, setDirection] = useState('UP');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const newAlert = { symbol, basis, value: parseFloat(value), direction };
      const addedAlert = await createAlert(newAlert);
      onAdd(addedAlert);
      setSymbol('');
      setValue('');
      setError('');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="alert-form">
      {error && <p className="error">{error}</p>}
      <input
        type="text"
        value={symbol}
        onChange={(e) => setSymbol(e.target.value)}
        placeholder="Symbol"
        required
      />
      <select value={basis} onChange={(e) => setBasis(e.target.value)}>
        <option value="price">Price</option>
        <option value="moving_average">Moving Average</option>
      </select>
      <input
        type="number"
        value={value}
        onChange={(e) => setValue(e.target.value)}
        placeholder="Value"
        required
      />
      <select value={direction} onChange={(e) => setDirection(e.target.value)}>
        <option value="UP">UP</option>
        <option value="DOWN">DOWN</option>
      </select>
      <button type="submit">Add Alert</button>
    </form>
  );
};

export default AlertForm;
