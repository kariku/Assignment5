package assignment5.test;

import assignment5.rest.PriceInfo;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Extract useful parameter values from a URI.
 * Namely: currencyFrom and currencyTo
 */
class URLDecoder {

    private static final String URL_PARAM_CURR_FROM = "fsym";
    private static final String URL_PARAM_CURR_TO = "tsyms";


    PriceInfo decodeURI(URI uri) {
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        Map<String, String> keyValuePairs = new HashMap<>();
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            keyValuePairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }

        String currencyFrom = keyValuePairs.get(URL_PARAM_CURR_FROM);
        Objects.requireNonNull(currencyFrom, "missing " + URL_PARAM_CURR_FROM + " parameter");
        String currencyTo = keyValuePairs.get(URL_PARAM_CURR_TO);
        Objects.requireNonNull(currencyTo, "missing " + URL_PARAM_CURR_TO + " parameter");

        return new PriceInfo(currencyFrom, currencyTo, 0);
    }

}
