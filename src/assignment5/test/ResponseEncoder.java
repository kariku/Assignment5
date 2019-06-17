package assignment5.test;

import assignment5.rest.PriceInfo;

/**
 * Encode a PriceInfo into a response as expected by RestPriceService.
 */
class ResponseEncoder {
    String encodeResponse(PriceInfo priceInfo) {
        StringBuilder response = new StringBuilder();

        response.append(priceInfo.getPrice());

        return response.toString();
    }
}
