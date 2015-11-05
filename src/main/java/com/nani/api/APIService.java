/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nani.api;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("API")
public class APIService extends ResourceConfig {

//驗證碼前綴
  static public String md5Pre = "fgoeDDpwseSa";

  public APIService() {
    packages("com.nani.api");
    //須註冊這個才可以上傳檔案
    register(MultiPartFeature.class);
//    register(CharsetResponseFilter.class);
    //swagger
    Set<Class<?>> resources = new HashSet<>();
    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    registerClasses(resources);
  }
}
