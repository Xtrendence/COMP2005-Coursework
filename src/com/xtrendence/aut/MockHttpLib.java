package com.xtrendence.aut;

public class MockHttpLib implements IHttpLib {
    public MockHttpLib() {

    }

    public Response call(String url) throws Exception {
        return new Response(200, "");
    }
}
