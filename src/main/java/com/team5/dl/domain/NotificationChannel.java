
package com.team5.dl.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "channelType",
    "enabled",
    "contacts"
})
public class NotificationChannel {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("channelType")
    private String channelType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("enabled")
    private Boolean enabled;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("contacts")
    private List<String> contacts = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("channelType")
    public String getChannelType() {
        return channelType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("channelType")
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("enabled")
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("contacts")
    public List<String> getContacts() {
        return contacts;
    }

    /**
     * 
     * (Required)
     * 
     */
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
