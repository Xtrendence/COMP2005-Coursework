package com.xtrendence.aut;

public interface IHttpLib {
    /* Ensures that all classes that implement the interface have a call method.
    *  @param url The URL to make an API call to.
    *  @return Response the response code and data.
    */
    public Response call(String url) throws Exception;
}