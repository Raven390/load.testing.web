package ru.develonica.load.testing.web.test.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Duration;

public class LoadTestingStartParams {
    private Duration duration;
    private Integer parallelRequests;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String jmxHost;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer jmxPort;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getParallelRequests() {
        return parallelRequests;
    }

    public void setParallelRequests(Integer parallelRequests) {
        this.parallelRequests = parallelRequests;
    }

    public String getJmxHost() {
        return jmxHost;
    }

    public void setJmxHost(String jmxHost) {
        this.jmxHost = jmxHost;
    }

    public Integer getJmxPort() {
        return jmxPort;
    }

    public void setJmxPort(Integer jmxPort) {
        this.jmxPort = jmxPort;
    }
}
