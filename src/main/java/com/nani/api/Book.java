/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nani.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 書籍資料
 * @author nani12a
 */
@Api(value = "書籍資料")
@Path("Book")
@Produces(MediaType.APPLICATION_JSON)
public class Book {

  //基本資料
  static JSONArray jBook = new JSONArray();
  static int IDIndex = 0;

  static {
    String[] books = {"木偶奇遇記", "小紅帽", "伊索寓言", "小美人魚"};
    JSONObject jtmp;
    for (int i = 0; i < books.length; i++) {
      jtmp = new JSONObject();
      jtmp.put("ID", i + 1);
      jtmp.put("BookName", books[i]);
      jBook.put(jtmp);
    }
    IDIndex = books.length;
  }
/**
 * 回傳輸及列表
 * @return 
 */
  @GET
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "回傳書籍列表")
  })
  public String Books() {
    JSONObject jMain = new JSONObject();
    jMain.put("Status", "Success");
    jMain.put("Message", "Success");
    jMain.put("Data", jBook);

    return jMain.toString();
  }

  @GET
  @Path("/{ID}")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "回傳書籍")
  })
  public String Books(
          @ApiParam(value = "書籍編號", required = true) @PathParam("ID") int ID
  ) {
    JSONObject jMain = new JSONObject();
    String Status = "";
    String Message = "";

    int index = -1;
    for (int i = 0; i < jBook.length(); i++) {
      if (jBook.getJSONObject(i).getInt("ID") == ID) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      Status = "Success";
      jMain.put("Data", jBook.getJSONObject(index));

    } else {
      Status = "Error";
      Message = "查無書籍";
    }

    jMain.put("Status", Status);
    jMain.put("Message", Message);

    return jMain.toString();
  }

  @PUT
  @Path("/{ID}")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "更新書籍")
  })
  public String Books(
          @ApiParam(value = "書籍編號", required = true) @PathParam("ID") int ID,
          @ApiParam(value = "書籍名稱", required = true) @FormParam("BookName") String BookName
  ) {
    JSONObject jMain = new JSONObject();
    String Status = "";
    String Message = "";

    int index = -1;
    for (int i = 0; i < jBook.length(); i++) {
      if (jBook.getJSONObject(i).getInt("ID") == ID) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      Status = "Success";
      jBook.getJSONObject(index).remove("BookName");
      jBook.getJSONObject(index).put("BookName", BookName);
      jMain.put("Data", jBook.getJSONObject(index));

    } else {
      Status = "Error";
      Message = "查無書籍";
    }

    jMain.put("Status", Status);
    jMain.put("Message", Message);

    return jMain.toString();
  }
  
  @DELETE
  @Path("/{ID}")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "刪除書籍")
  })
  public String DeleteBook(
          @ApiParam(value = "書籍編號", required = true) @PathParam("ID") int ID
  ) {
    JSONObject jMain = new JSONObject();
    String Status = "";
    String Message = "";

    int index = -1;
    for (int i = 0; i < jBook.length(); i++) {
      if (jBook.getJSONObject(i).getInt("ID") == ID) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      Status = "Success";
      jBook.remove(index);
    } else {
      Status = "Error";
      Message = "查無書籍";
    }

    jMain.put("Status", Status);
    jMain.put("Message", Message);

    return jMain.toString();
  }

  @POST
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "新增書籍")
  })
  public String Books(
          @ApiParam(value = "書籍名稱", required = true) @FormParam("BookName") String BookName
  ) {
    JSONObject jMain = new JSONObject();
    String Status = "";
    String Message = "";

    JSONObject jtmp = new JSONObject();
    jtmp.put("ID", ++IDIndex);
    jtmp.put("BookName", BookName);
    jBook.put(jtmp);

    Status = "Success";

    jMain.put("Data", jtmp);

    jMain.put("Status", Status);
    jMain.put("Message", Message);

    return jMain.toString();
  }

}
