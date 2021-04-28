package com.xtrendence.aut;

public class FunctionalTesting {
    boolean mockTest;
    Restaurant[] restaurants;

    public FunctionalTesting(boolean mockTest) {
        this.mockTest = mockTest;
    }

    public void loadRestuarants() throws Exception {
        int code;
        String json;

        Testing.outputFunctional("Fetching JSON...");

        if(mockTest) {
            MockHttpLib mockHttpLib = new MockHttpLib();
            Response response = mockHttpLib.call("");
            code = response.getCode();
            json = response.getData();
        } else {
            HttpLib httpLib = new HttpLib();
            Response response = httpLib.call("http://intelligent-social-robots-ws.com/restaurant-data.json");
            code = response.getCode();
            json = response.getData();
        }

        if(json != null && !json.equals("")) {
            Testing.outputFunctional("Fetched JSON");
            Testing.outputFunctional(json.substring(0, 127) + "...\n(Only showing first 128 characters)");
            Testing.outputFunctional("Parsing JSON...");
            this.restaurants = new RestaurantAdapter(json).adapt();
        } else {
            Testing.outputFunctional("Couldn't fetch JSON.");
        }
    }
}
