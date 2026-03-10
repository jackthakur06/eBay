import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import static io.restassured.RestAssured.given;

/**
 * API Automation Test for CoinGecko [cite: 86, 93]
 * Validates Bitcoin market data metrics via REST Assured.
 */
public class CoinGeckoApiTest {
    public static void main(String[] args) {
        // 1. Send the GET request [cite: 95, 97, 99]
        Response response = given()
            .baseUri("https://api.coingecko.com/api/v3")
            .when()
            .get("/coins/bitcoin")
            .then()
            .extract().response();

        // Verify status code is 200 [cite: 102, 103]
        Assert.assertEquals(response.getStatusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        System.out.println("Response received. Starting validations...");

        // 2.a Verify Currencies exist in market_data [cite: 106, 108, 110]
        validateCurrencyExists(jsonPath, "usd");
        validateCurrencyExists(jsonPath, "gbp");
        validateCurrencyExists(jsonPath, "eur");
        System.out.println("Validation 2a: USD, GBP, and EUR price indexes confirmed.");

        // 2.b Verify market cap and total volume [cite: 112, 113, 115]
        validateMarketMetrics(jsonPath, "usd");
        validateMarketMetrics(jsonPath, "gbp");
        validateMarketMetrics(jsonPath, "eur");
        System.out.println("Validation 2b: Market Cap and Total Volume verified for all currencies.");

        // 2.c Verify price change percentage [cite: 117, 118, 120]
        Double priceChange24h = jsonPath.getDouble("market_data.price_change_percentage_24h");
        Assert.assertNotNull(priceChange24h, "Price change percentage 24h is missing!");
        System.out.println("Validation 2c: Price change percentage (24h) is: " + priceChange24h + "%");

        System.out.println("\nAll API Validations Passed Successfully!");
    }

    private static void validateCurrencyExists(JsonPath jp, String currency) {
        Assert.assertTrue(jp.getMap("market_data.current_price").containsKey(currency), 
            "Currency index " + currency + " not found!"); [cite: 124]
    }

    private static void validateMarketMetrics(JsonPath jp, String currency) {
        Assert.assertNotNull(jp.get("market_data.market_cap." + currency), "Market cap missing for " + currency); [cite: 128]
        Assert.assertNotNull(jp.get("market_data.total_volume." + currency), "Total volume missing for " + currency); [cite: 130]
    }
}
