package org.aggregation.dao;

import org.aggregation.webbackend.WebBackend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class TrackingDaoImplTest {

    @InjectMocks
    TrackingDaoImpl trackingDaoImpl;

    @Mock
    private WebBackend webBackend;

    @Test
    public void test_submit() {
        List<String> orders = new ArrayList<>();
        orders.add("123456789");
        ExecutorService executor = Mockito.mock(ExecutorService.class);
        doReturn("DELIVERED").when(webBackend).getTrackStatus(Mockito.anyString());
        Map<String, String> submit = trackingDaoImpl.getTracking(orders);
        assertEquals("DELIVERED", submit.get("123456789"));

    }
}
