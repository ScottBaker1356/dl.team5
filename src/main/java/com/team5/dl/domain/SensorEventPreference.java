
package com.team5.dl.domain;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "channelType",
    "enabled",
    "contacts"
})
public class SensorEventPreference {

    @JsonProperty("channelType")
    private String channelType;
    @JsonProperty("enabled")
    private Boolean enabled;
    @JsonProperty("contacts")
    private List<String> contacts = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("channelType")
    public String getChannelType() {
        return channelType;
    }

    @JsonProperty("channelType")
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("contacts")
    public List<String> getContacts() {
        return contacts;
    }

    @JsonProperty("contacts")
    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
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
        return new ToStringBuilder(this).append("channelType", channelType).append("enabled", enabled).append("contacts", contacts).append("additionalProperties", additionalProperties).toString();
    }

}
