
package com.team5.dl.domain;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "eventPreferences",
    "customerId"
})
public class Customer {

    @JsonProperty("eventPreferences")
    private List<SensorEventPreference> eventPreferences = new ArrayList<SensorEventPreference>();
    @JsonProperty("customerId")
    private String customerId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("eventPreferences")
    public List<SensorEventPreference> getSensorEventPreferences() {
        return eventPreferences;
    }

    @JsonProperty("eventPreferences")
    public void setSensorEventPreferences(List<SensorEventPreference> eventPreference) {
        this.eventPreferences = eventPreference;
    }

    @JsonProperty("customerId")
    public String getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("eventPreferences", eventPreferences).append("customerId", customerId).append("additionalProperties", additionalProperties).toString();
    }

}
