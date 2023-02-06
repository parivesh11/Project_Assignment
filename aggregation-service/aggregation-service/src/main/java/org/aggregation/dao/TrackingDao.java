package org.aggregation.dao;

import java.util.List;
import java.util.Map;

public interface TrackingDao {
    Map<String, String> getTracking(final List<String> orders);
}
