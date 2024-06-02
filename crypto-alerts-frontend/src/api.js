import axios from 'axios';

const API_BASE_URL = process.env.API_BASE_URL || 'http://localhost:8888';

export const getAlerts = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/alerts`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch alerts. Please try again later.');
  }
};

export const createAlert = async (alert) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/alerts`, alert);
    return response.data;
  } catch (error) {
    throw new Error('Failed to create alert. Please try again later.');
  }
};

export const deleteAlert = async (id) => {
  try {
    await axios.delete(`${API_BASE_URL}/alerts/${id}`);
  } catch (error) {
    throw new Error('Failed to delete alert. Please try again later.');
  }
};
