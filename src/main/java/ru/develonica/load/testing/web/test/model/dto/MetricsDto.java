package ru.develonica.load.testing.web.test.model.dto;

import ru.develonica.load.testing.common.model.generated.Metrics;
import ru.develonica.load.testing.common.model.generated.TimeBasedMetric;
import ru.develonica.load.testing.common.model.generated.TimeBasedMetricMap;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetricsDto {

    private final Map<String, List<Map<OffsetDateTime, Long>>> memoryMetricsMap;
    private final Map<Integer, Long> responseStatusMetricMap;

    public MetricsDto(Metrics metrics) {
        this.responseStatusMetricMap = metrics.getCodesReceivedMap();
        this.memoryMetricsMap = parse(metrics);
    }

    private static Map<String, List<Map<OffsetDateTime, Long>>> parse(Metrics metrics) {
        return metrics.getMemoryMetricsMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getMetricsMapList().stream()
                                .map(timeBasedMetricMap -> timeBasedMetricMap.getDataMap().entrySet().stream()
                                        .collect(Collectors.toMap(
                                                e -> OffsetDateTime.parse(e.getKey()),
                                                Map.Entry::getValue,
                                                (v1, v2) -> v1,
                                                LinkedHashMap::new
                                        )))
                                .collect(Collectors.toList()),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, List<Map<OffsetDateTime, Long>>> getMemoryMetricsMap() {
        return memoryMetricsMap;
    }

    public Map<Integer, Long> getResponseStatusMetricMap() {
        return responseStatusMetricMap;
    }
}
