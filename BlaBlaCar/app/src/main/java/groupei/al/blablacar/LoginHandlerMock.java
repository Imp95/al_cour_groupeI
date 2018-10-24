package groupei.al.blablacar;

import android.util.Log;

public class LoginHandlerMock implements LoginHandler {

    private int passwordHash = "password".hashCode();


    @Override
    public LoginToken login(String mail, String password) {
        if(mail.equals("user@exemple.com") && password.hashCode()==passwordHash)
            return new LoginToken(mail);
        else if(mail.equals("user@exemple.com")){
            Log.w("conection","incorrect password");
        }
        else {
            Log.w("conection","incorrect login");
        }
        return null;
    }
}
