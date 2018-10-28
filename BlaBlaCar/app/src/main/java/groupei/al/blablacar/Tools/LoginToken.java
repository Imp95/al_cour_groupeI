package groupei.al.blablacar.Tools;

import java.util.Calendar;
import java.util.Date;

public class LoginToken {

    private String email;
    private final Date expire;

    public LoginToken(String mail){
        email = mail;
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        expire = c.getTime();
    }

    public LoginToken(String mail, long timeout){
        email = mail;
        expire = new Date(timeout);
    }

    public String getEmail(){
        return email;
    }
    public long getExpire(){
        return expire.getTime();
    }
}
