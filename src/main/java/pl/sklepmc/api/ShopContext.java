/*
 * SklepMC Java API
 * Copyright (C) 2019 SklepMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package pl.sklepmc.api;

import okhttp3.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ShopContext {

    private static final String DEFAULT_SHOP_URL = "https://www.sklepmc.pl/shop/{SHOP_ID}";
    private static final String DEFAULT_MAIN_URL = "https://www.sklepmc.pl/api";

    private final String shopId;
    private final String secret;

    private String mainUrl;
    private String shopUrl;
    private OkHttpClient client;

    public ShopContext(String shopId, String secret) {
        this.shopId = shopId;
        this.secret = secret;
        this.client = new OkHttpClient();
        this.mainUrl = DEFAULT_MAIN_URL;
        this.shopUrl = DEFAULT_SHOP_URL;
    }

    protected String getSecret() {
        return this.secret;
    }

    protected OkHttpClient getClient() {
        return this.client;
    }

    public String getMainUrl() {
        return this.mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getShopId() {
        return this.shopId;
    }

    public String getShopUrl() {
        return this.shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String generatePaymentUrl(int serviceId, String paymentMethod, String nick, String charset) {

        try {
            nick = URLEncoder.encode(nick, charset);
        } catch (UnsupportedEncodingException exception) {
            throw new IllegalArgumentException("Failed to encode nick with " + charset + " encoding!", exception);
        }

        String url = this.getShopUrl() + "/buy/{SERVICE_ID}/{PAYMENT_METHOD}?nick={NICK}";
        url = ApiResource.replace(url, "{SHOP_ID}", this.getShopId());
        url = ApiResource.replace(url, "{SERVICE_ID}", String.valueOf(serviceId));
        url = ApiResource.replace(url, "{PAYMENT_METHOD}", paymentMethod);
        url = ApiResource.replace(url, "{NICK}", nick);

        return url;
    }

    public String generatePaymentUrl(int serviceId, String paymentMethod, String nick, String giftTo, String charset) {

        try {
            nick = URLEncoder.encode(nick, charset);
        } catch (UnsupportedEncodingException exception) {
            throw new IllegalArgumentException("Failed to encode nick with " + charset + " encoding!", exception);
        }

        String url = this.getShopUrl() + "/buy/{SERVICE_ID}/{PAYMENT_METHOD}?nick={NICK}&gift_to={GIFT_TO}";
        url = ApiResource.replace(url, "{SHOP_ID}", this.getShopId());
        url = ApiResource.replace(url, "{SERVICE_ID}", String.valueOf(serviceId));
        url = ApiResource.replace(url, "{PAYMENT_METHOD}", paymentMethod);
        url = ApiResource.replace(url, "{NICK}", nick);
        url = ApiResource.replace(url, "{GIFT_TO}", giftTo);

        return url;
    }
}
