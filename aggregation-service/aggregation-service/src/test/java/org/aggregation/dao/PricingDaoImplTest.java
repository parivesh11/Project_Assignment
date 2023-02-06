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
public class PricingDaoImplTest {
    @InjectMocks
    PricingDaoImpl pricingDaoImpl;
    @Mock
    private WebBackend webBackend;

    @Test
    public void test_submit() {
        List<String> countryCodes = new ArrayList<>();
        countryCodes.add("NL");
        ExecutorService executor = Mockito.mock(ExecutorService.class);
        doReturn("14.2").when(webBackend).getPricing(Mockito.anyString());
        Map<String, String> submit = pricingDaoImpl.getPricing(countryCodes);
        assertEquals("14.2", submit.get("NL"));
    }
}
