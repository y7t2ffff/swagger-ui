/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nani.api;

import com.nani.common.Xlsx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.InputStream;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.poi.ss.usermodel.Row;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 書籍資料
 *
 * @author nani12a
 */
@Api(tags = {"Book"}, value = "書籍資料")
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
   *
   * @return
   */
  @GET
  @ApiOperation(
          value = "書籍列表",
          notes = "書籍列表資料")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
  })
  public String Books() {
    JSONObject jMain = new JSONObject();
    jMain.put("Status", "Success");
    jMain.put("Message", "Success");
    jMain.put("Data", jBook);

    return jMain.toString();
  }

  @POST
  @ApiOperation(
          value = "批次新增書籍",
          notes = "匯入XLSX，批次新增書籍")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
  })
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public String Books(
          @ApiParam(value = "XLSX檔案", required = true) @FormDataParam("file") InputStream is,
          @ApiParam(value = "檔案說明", required = false, hidden = true) @FormDataParam(value = "file") FormDataContentDisposition fileDisposition
  ) {
    JSONObject jMain = new JSONObject();
    String Status = "";
    String Message = "";
    try {
      JSONArray jData = Xlsx.getXlsxToJsonarray(is, Row.RETURN_BLANK_AS_NULL);
      for (int i = 0; i < jData.length(); i++) {
        JSONObject jtmp = new JSONObject();
        jtmp.put("ID", ++IDIndex);
        jtmp.put("BookName", jData.getJSONObject(i).getString("BookName"));
        jBook.put(jtmp);
      }
      String fileName = new String(fileDisposition.getFileName().getBytes("ISO8859-1"), "UTF-8");
      Status = "Success";
      Message = fileName + "上傳完成";
    } catch (Exception ex) {
      Status = "Error";
      Message = ex.getMessage();
      ex.printStackTrace();
    } finally {
      jMain.put("Status", Status);
      jMain.put("Message", Message);
    }

    return jMain.toString();
  }

  @POST
  @Path("Multi")
  @ApiOperation(
          value = "批次新增書籍(多檔上傳)",
          notes = "多檔上傳，匯入XLSX，批次新增書籍")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
  })
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public String Books2(
          @ApiParam(value = "XLSX檔案", required = true) FormDataMultiPart formData
  ) {
    JSONObject jMain = new JSONObject();
    String Status = "";
    String Message = "";
    try {
      StringBuilder sb = new StringBuilder();

      List<FormDataBodyPart> parts = formData.getFields("file");
      if (parts != null) {
        for (FormDataBodyPart part : parts) {
          System.out.println("++");
          //取得檔案說明
          FormDataContentDisposition disp = part.getFormDataContentDisposition();
          //取得檔案串流
          InputStream inputstream = part.getValueAs(InputStream.class);
          //檔名及編碼設定
          String fileName = new String(disp.getFileName().getBytes("ISO8859-1"), "UTF-8");
          sb.append(fileName);
          sb.append("  ");
          
          JSONArray jData = Xlsx.getXlsxToJsonarray(inputstream, Row.RETURN_BLANK_AS_NULL);
          for (int i = 0; i < jData.length(); i++) {
            JSONObject jtmp = new JSONObject();
            jtmp.put("ID", ++IDIndex);
            jtmp.put("BookName", jData.getJSONObject(i).getString("BookName"));
            jBook.put(jtmp);
          }

        }
      } else {
        throw new Exception("未上傳檔案");
      }

      Status = "Success";
      Message = sb.toString()+ "上傳完成";
    } catch (Exception ex) {
      Status = "Error";
      Message = ex.getMessage();
      ex.printStackTrace();
    } finally {
      jMain.put("Status", Status);
      jMain.put("Message", Message);
    }

    return jMain.toString();
  }

  @GET
  @Path("/{ID}")
  @ApiOperation(
          value = "取得書籍",
          notes = "依照編號取得書籍資訊")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
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
  @ApiOperation(
          value = "更新書籍",
          notes = "依照編號,更新書籍名稱")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
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
  @ApiOperation(
          value = "刪除書籍",
          notes = "依照編號,刪除特定書籍")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
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
  @Path("/{ID}")
  @ApiOperation(
          value = "新增書籍",
          notes = "新增書籍，並回傳相關資料")
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "完成")
  })
  public String Books(
          @ApiParam(value = "編號(新增預設為0)", required = true, defaultValue = "0") @PathParam("ID") String ID,
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
