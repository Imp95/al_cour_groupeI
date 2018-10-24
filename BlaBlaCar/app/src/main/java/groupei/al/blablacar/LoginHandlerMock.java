package groupei.al.blablacar;

public class LoginHandlerMock implements LoginHandler {

    private int passwordHash = "password".hashCode();


    @Override
    public LoginToken login(String mail, String password) {
        if(mail=="user@exemple.com" && password.hashCode()==passwordHash)
            return new LoginToken(mail);
        return null;
    }
}
