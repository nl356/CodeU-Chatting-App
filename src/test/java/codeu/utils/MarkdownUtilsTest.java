package codeu.utils;//Nathalia Lie and Trisha Zalani

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarkdownUtilsTest {

  @Before
  //public void setup() {
    //mdu = new MarkdownUtils();
  //}

  @Test
  public void cleanMessage(){
     String m1 = "<b>google</b>";
     String result = MarkdownUtils.cleanMessage (m1);
     Assert.assertEquals(m1, result);
   }

   @Test
   public void cleanMessage_malicious(){
      String al = "Pay me a million dollars or I'll delete your files!";
      String m1 = "<script> alert(al); </script>";
      String result = MarkdownUtils.cleanMessage (m1);

      Assert.assertEquals("", result);
    }

    @Test
    public void mark_down_check() {
        String m1 = "<p>This text is <strong>bold</strong> and <em>italics</em></p>";
        String m2 = "This text is **bold** and *italics*";
        String result = MarkdownUtils.mark_down(m2);
        Assert.assertEquals(m1, result);
    }

}
