package undertea.demo;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

/**
 * Created by me on 10/19/16.
 */
public class Client {
    public static void main(String[] args) {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement div = document.createElement("div");
        div.appendChild(document.createTextNode("TeaVM generated element"));
        document.getBody().appendChild(div);
    }
}
