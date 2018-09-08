package codeu.utils;//By: Nathalia Lie and Trisha Zalani
//
// This utility applies styles to texts.
// Implements parsing and sanitizing.
//
// Ex: Users could enter [**bold**] text which is parsed into [<b>bold</b>] text
// to render in the page

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


/** Servlet class responsible for the login page. */
public class MarkdownUtils {

  /**
   * Parses through input message.
   * Recognizes markdown symbols and returns message with HTML tags
   */
  // IMPLEMENT HERE
  public static String mark_down (String message) {
    Node doc = parse(message);
    HtmlRenderer renderer = HtmlRenderer.builder().build();
    String rendered = renderer.render(doc);
    String cleaned = cleanMessage(rendered);
    return cleaned;
  }

  //maybe required later.
  private static TextContentRenderer defaultRenderer() {
      return TextContentRenderer.builder().build();
  }

  private static Node parse (String mess){
    return Parser.builder().build().parse(mess);
  }

  /**
   * Takes care of sanitizing input.
   * Here, whitelist allows full range of text nodes and safe links.
   * Requires: [message] is input untrusted HTML
   * Returns: safe HTML according to white-list of permitted tags and attributes.
   */
  public static String cleanMessage (String message){
    return Jsoup.clean(message, Whitelist.basic());
  }
}
