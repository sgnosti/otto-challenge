package de.otto.logistics.challenge.iprange;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpRanges {

    @JsonProperty("prefixes")
    private List<Prefix> prefixes = new ArrayList<>();

    @JsonProperty("ipv6_prefixes")
    private List<Prefix> ipv6Prefixes = new ArrayList<>();

    public List<Prefix> getAllPrefixes() {
        return Stream.of(prefixes, ipv6Prefixes).flatMap(Collection::stream).toList();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Prefix {

        @JsonAlias({"ip_prefix", "ipv6_prefix"})
        private String ipPrefix;

        @JsonProperty
        private String region;
    }
}
