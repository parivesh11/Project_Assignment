package org.aggregation.webbackend;

import java.util.List;

public interface WebBackend {

    List<String> getShipmentStatus(String orderNumber);

    String getTrackStatus(String orderNumber);

    String getPricing(String countryCode);
}
