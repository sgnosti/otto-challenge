package de.otto.logistics.challenge.iprange;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder=IpRanges.IpRangesBuilder.class)
public class IpRanges {

    @JsonProperty("prefixes")
    @Builder.Default
    private List<Prefix> prefixes = new ArrayList<>();

    @JsonProperty("ipv6_prefixes")
    @Builder.Default
    private List<Prefix> ipv6Prefixes = new ArrayList<>();

    public List<Prefix> getAllPrefixes() {
        return Stream.of(prefixes, ipv6Prefixes).flatMap(Collection::stream).toList();
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonDeserialize(builder=IpRanges.Prefix.PrefixBuilder.class)
    public static class Prefix {

        @JsonAlias({"ip_prefix", "ipv6_prefix"})
        private String ipPrefix;

        @JsonProperty
        private String region;
    }
}
