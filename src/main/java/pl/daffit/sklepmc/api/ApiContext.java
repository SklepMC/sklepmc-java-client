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
package pl.daffit.sklepmc.api;

import okhttp3.OkHttpClient;

public class ApiContext {

    private static final String DEFAULT_MAIN_URL = "https://www.sklepmc.pl/api";

    private final String shopId;
    private final String secret;

    private String mainUrl;
    private OkHttpClient client;

    public ApiContext(String shopId, String secret) {
        this.shopId = shopId;
        this.secret = secret;
        this.client = new OkHttpClient();
        this.mainUrl = DEFAULT_MAIN_URL;
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
}
