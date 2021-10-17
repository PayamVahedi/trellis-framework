package com.hamrasta.trellis.util.net;

import com.hamrasta.trellis.core.payload.ResponseMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IpUtil {
    private static final String IP_API_URL = "http://ip-api.com/json/";
    private static final String IP_API_PARAMETER = "?fields=country,isp";
    private static final String UNKNOWN_COUNTRY = "UNKNOWN";
    private static final Map<String, IpApiResponse> ipCache = new HashMap<>();

    public static boolean match(String ip, Set<String> allowCountries, Set<String> allowIps) {
        if (!matchAllowIp(allowIps, ip)) {
            IpApiResponse country = (ipCache.containsKey(ip) && !ipCache.get(ip).getCountry().equalsIgnoreCase(UNKNOWN_COUNTRY)) ? ipCache.get(ip) : getIpInfo(ip);
            if (ObjectUtils.isEmpty(country))
                return false;
            ipCache.put(ip, country);
            return matchCountry(allowCountries, country.getCountry(), country.getIsp());
        }
        return true;
    }

    private static boolean matchAllowIp(Set<String> allowIps, String ip) {
        return ObjectUtils.isNotEmpty(allowIps) && allowIps.stream().anyMatch(ip::startsWith);
    }

    private static boolean matchCountry(Set<String> allowCountries, String country, String isp) {
        return ObjectUtils.isNotEmpty(allowCountries) && allowCountries.stream().anyMatch(x -> StringUtils.containsIgnoreCase(x, country) || StringUtils.containsIgnoreCase(x, isp));
    }

    private static IpApiResponse getIpInfo(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IpApiResponse> response = restTemplate.getForEntity(IP_API_URL + ip + IP_API_PARAMETER, IpApiResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            if (response.getBody() == null || response.getBody().getCountry() == null)
                return new IpApiResponse(UNKNOWN_COUNTRY, UNKNOWN_COUNTRY);
            return response.getBody();
        }
        return new IpApiResponse(UNKNOWN_COUNTRY, UNKNOWN_COUNTRY);
    }

    private static class IpApiResponse extends ResponseMessage {

        private String country;

        private String isp;

        public String getIsp() {
            return isp;
        }

        public void setIsp(String isp) {
            this.isp = isp;
        }

        public String getCountry() {
            return country;
        }

        public IpApiResponse() {
        }

        public IpApiResponse(String country, String isp) {
            this.country = country;
            this.isp = isp;
        }
    }
}
