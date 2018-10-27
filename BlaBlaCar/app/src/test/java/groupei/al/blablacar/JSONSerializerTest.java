package groupei.al.blablacar;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class JSONSerializerTest {
    @Before
    public void init()  throws Exception {

    }

    @Test
    public void test_getConnexionJSON() throws Exception {
        JSONSerializer.getInstance();
        JSONObject json = JSONSerializer.getConnexionJSON("user@exemple.com", "password");
        assertEquals(json.get("Action"), "connexion");
        JSONObject json_body = (JSONObject) json.get("Body");
        assertEquals(json_body.get("email"), "user@exemple.com");
    }
}
