package org.aggregation.dao;

import java.util.List;
import java.util.Map;

public interface PricingDao {

    Map<String, String> getPricing(List<String> countryCodes);
}
