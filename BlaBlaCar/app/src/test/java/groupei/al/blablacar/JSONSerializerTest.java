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
        JSONSerializer.getInstance();
    }

    @Test
    public void test_getConnexionJSON() throws Exception {
        JSONObject json = JSONSerializer.getConnexionJSON("user@exemple.com", "password");
        assertNotEquals(json.get("Action"), "inscription");
        assertEquals(json.get("Action"), "connexion");
        JSONObject json_body = (JSONObject) json.get("Body");
        assertNotEquals(json_body.get("email"), "user@unit_test.fr");
        assertEquals(json_body.get("email"), "user@exemple.com");
    }

    @Test
    public void test_getInscriptionJSON() throws Exception {
        JSONObject json = JSONSerializer.getInscriptionJSON("user@exemple.com", "password", "Doe", "John", "01/01/2000");
        assertEquals(json.get("Action"), "inscription");
        assertNotEquals(json.get("Action"), "connexion");
        JSONObject json_body = (JSONObject) json.get("Body");
        assertNotEquals(json_body.get("email"), "user@unit_test.fr");
        assertEquals(json_body.get("email"), "user@exemple.com");
        assertEquals(json_body.get("nom"), "Doe");
        assertEquals(json_body.get("prenom"), "John");
        assertEquals(json_body.get("date_de_naissance"), "01/01/2000");
        assertNotEquals(json_body.get("nom"), "Joe");
        assertNotEquals(json_body.get("prenom"), "Johnny");
        assertNotEquals(json_body.get("date_de_naissance"), "02/01/2000");
    }
}
