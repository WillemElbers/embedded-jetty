package nl.we.embedded.jetty.test.resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wilelb
 */
@XmlRootElement
public class TestModel {
    private boolean status;
    private String message;     

    public TestModel(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
