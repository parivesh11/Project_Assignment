package org.aggregation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
@NoArgsConstructor
public class AggregationResponse {
    private Map<String, List<String>> shipments;
    private Map<String, String> track;
    private Map<String, String> prices;
	
    
    
}
