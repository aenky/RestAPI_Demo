package Test;


import Helper.API_Helper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommentTest {

    public static API_Helper helper=new API_Helper();

    @BeforeAll
    public static void setup() {

        helper.setURL("https://jsonplaceholder.typicode.com");
    }

    @Test
    public void commentTest() {
        helper.getRequestForGetAPI("/comments");
        helper.verifyResponseCode(200);
        helper.verifyResponseHasGreaterThanZeroComments();
        helper.verifyResponseHasExistGivenEmail("Jayne_Kuhic@sydney.com");
        helper.removeAllCommentsThatHasPostIdDifferentThanOne();
        helper.removeAllCommentsThatHasNotNonWordInBody();
    }

}
