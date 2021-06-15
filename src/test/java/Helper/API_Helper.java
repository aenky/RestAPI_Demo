package Helper;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pojo.Comment;

import static com.jayway.restassured.RestAssured.given;


public class API_Helper {
    public static Response response=null;
    public static Comment comment = new Comment();

    /**
     * Set Base URL
     * @param url
     * @author Ankit Mistry
     */
    public void setURL(String url){
        RestAssured.baseURI = url;
        System.out.println("Set Base URL: "+url);
    }

    /**
     * Send API Request with given endpoint and set response on pojo class
     * @param endpoint
     * @author Ankit Mistry
     */
    public Response getRequestForGetAPI(String endpoint) {
         response = given().contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .extract().response();

        System.out.println("Response of API :"+response.getBody().asString());

        String body = response.getBody().asString();
        JSONArray responseArray= new JSONArray(body);
        comment.setBody(responseArray);
        return response;

    }

    /**
     * Verify Response code with give code
     * @param responseCode
     * @author Ankit Mistry
     */
    public void verifyResponseCode(int responseCode){

        Assert.assertEquals(responseCode,response.statusCode());
        System.out.println("Verify Response code: "+responseCode);
    }

    /**
     * Verify response has greater Than zero comments
     *
     * @author Ankit Mistry
     */
    public void verifyResponseHasGreaterThanZeroComments(){
        JSONArray listOfComments=parseResponseAsJSONArray(response);
        Assert.assertTrue(!listOfComments.isEmpty());
        Assert.assertTrue(listOfComments.length()>0);
        System.out.println("Verify response has more than zero comments");
    }

    /**
     * Verify given email is exist in response
     * @param email
     * @author Ankit Mistry
     */
    public void verifyResponseHasExistGivenEmail(String email){
        JSONArray listOfComments=parseResponseAsJSONArray(response);
        boolean isEmail=false;
        for(Object comment:listOfComments){
            String userEmail=((JSONObject)comment).getString("email");
            if(email.equals(userEmail)){
                isEmail=true;
                break;
            }
        }
        Assert.assertTrue(isEmail);
        System.out.println("Verify '"+email+"' email is exist in response comments list");
    }

    /**
     * Convert response into JSONArray
     * @param response
     * @author Ankit Mistry
     */
    public static JSONArray parseResponseAsJSONArray(Response response)
    {
        String responseAsString = response.asString();
        JSONArray jarray = null;
        try
        {
            jarray = new JSONArray(responseAsString);
            return jarray;
        }
        catch (JSONException e) {
            System.out.println("Exception => " + e.getMessage());
        }
        return null;
    }

    /**
     * Remove all comments from response which has postId different than one and print that list
     * @author Ankit Mistry
     */
    public void removeAllCommentsThatHasPostIdDifferentThanOne(){
        JSONArray commentWhoHasPostIdOne=new JSONArray();
        JSONArray commentList= comment.getBody();
        for(Object comments: commentList){
            if(((JSONObject)comments).getInt("postId") ==1)
            {
                commentWhoHasPostIdOne.put(comments);
            }
        }
        System.out.println("============================ List Of Comments That Has PostId One ==========================================");
        System.out.println(commentWhoHasPostIdOne);
        System.out.println("============================================================================================================");

    }

    /**
     * Remove all comments from response which has non word in body and print that list
     * @author Ankit Mistry
     */
    public void removeAllCommentsThatHasNotNonWordInBody(){
        JSONArray commentWhoHasNonWordInBody=new JSONArray();
        JSONArray commentList= comment.getBody();
        for(Object comments: commentList){
            if(((JSONObject)comments).getString("body").toLowerCase().contains("non"))
            {
                commentWhoHasNonWordInBody.put(comments);
            }
        }
        System.out.println("============================ List Of Comments That Has Non word in Body ====================================");
        System.out.println(commentWhoHasNonWordInBody);
        System.out.println("============================================================================================================");

    }


}
