package groupei.al.blablacar;

import android.support.annotation.CheckResult;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import groupei.al.blablacar.Tools.Checker;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class CheckerTest {
    @Before
    public void init()  throws Exception {
        Checker.getInstance();
    }

    @Test
    public void test_isEmail() throws Exception {
        String email_juste = "user@exemple.fr";
        String email_fausse1 = "user-exemple.fr";
        String email_fausse2 = "user-exemple.fr";
        assertTrue(Checker.isEmail(email_juste));
        assertFalse(Checker.isEmail(email_fausse1));
        assertFalse(Checker.isEmail(email_fausse2));
    }

    @Test
    public void test_inscriptionIsValid() throws Exception {
        String email = "user@exemple.fr";
        String mdp = "test";
        String naissance = "01/01/1995";
        String tel = "0611121314";
        String nom = "Doe";
        String prenom = "John";
        boolean init = Checker.inscriptionIsValid(email, mdp, mdp, naissance, tel, nom, prenom);
        assertFalse((Checker.inscriptionIsValid(email, mdp, mdp, naissance, tel, nom, prenom)));
    }

    @Test
    public void test_connexionIsValid() throws Exception {
        String email = "user@exemple.fr";
        String mdp = "test";
        String faux_email = "abcz@test.com";
        String faux_mdp = "faux";
        assertTrue((Checker.connexionIsValid(email, mdp)));
        assertFalse((Checker.connexionIsValid(faux_email, mdp)));
        assertFalse((Checker.connexionIsValid(email, faux_mdp)));
    }
}
