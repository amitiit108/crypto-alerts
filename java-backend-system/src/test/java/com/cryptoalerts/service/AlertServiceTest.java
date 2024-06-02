package com.cryptoalerts.service;

import com.cryptoalerts.model.Alert;
import com.cryptoalerts.model.AlertStatus;
import com.cryptoalerts.repository.AlertRepository;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AlertServiceTest {

    private AlertRepository alertRepository;
    private AlertService alertService;

    @Before
    public void setUp() {
        alertRepository = mock(AlertRepository.class);
        alertService = new AlertService(alertRepository);
    }

    @Test
    public void testCreateAlert() throws SQLException {
        Alert alert = new Alert(0, null, null, null, 0, null, null);
        alert.setSymbol("BTCUSDT");
        alert.setBasis("price");
        alert.setValue(40000);
        alert.setDirection("UP");

        alertService.createAlert(alert);

        verify(alertRepository, times(1)).save(alert);
        assertEquals(AlertStatus.PENDING.name(), alert.getStatus());
    }

    @Test
    public void testGetAllAlerts() throws SQLException {
        Alert alert = new Alert(0, null, null, null, 0, null, null);
        alert.setSymbol("BTCUSDT");
        alert.setBasis("price");
        alert.setValue(40000);
        alert.setDirection("UP");
        alert.setStatus(AlertStatus.PENDING.name());

        when(alertRepository.findAll()).thenReturn(Arrays.asList(alert));

        List<Alert> alerts = alertService.getAllAlerts();
        assertEquals(1, alerts.size());
        assertEquals("BTCUSDT", alerts.get(0).getSymbol());
    }

    @Test
    public void testRemoveAlert() throws SQLException {
        alertService.removeAlert(1);
        verify(alertRepository, times(1)).delete(1);
    }

    @Test
    public void testUpdateAlertStatus() throws SQLException {
        alertService.updateAlertStatus(1, AlertStatus.COMPLETED);
        verify(alertRepository, times(1)).updateStatus(1, AlertStatus.COMPLETED.name());
    }
}
