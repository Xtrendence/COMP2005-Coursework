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

        this.restaurants = new RestaurantAdapter(json).adapt();
    }
}
