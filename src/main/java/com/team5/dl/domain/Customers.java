
package com.team5.dl.domain;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customers"
})
public class Customers {

    @JsonProperty("customers")
    private List<Customer> customers = new ArrayList<Customer>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("customers")
    public List<Customer> getCustomers() {
        return customers;
    }

    @JsonProperty("customers")
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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
        return new ToStringBuilder(this).append("customers", customers).append("additionalProperties", additionalProperties).toString();
    }

}
