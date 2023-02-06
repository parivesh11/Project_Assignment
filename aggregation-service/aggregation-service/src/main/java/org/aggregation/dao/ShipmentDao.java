package org.aggregation.dao;

import java.util.List;
import java.util.Map;

public interface ShipmentDao {
    Map<String, List<String>> getShipments(List<String> orders);
}
