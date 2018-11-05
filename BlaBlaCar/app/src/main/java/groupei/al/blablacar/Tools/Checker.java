package groupei.al.blablacar.Tools;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Checker {
    static private Checker instance;

    private Checker() {}

    static public Checker getInstance() {
        if (instance == null) {
            instance = new Checker();
        }
        return instance;
    }

    static public boolean isEmail(String email) {
        String[] tab_email = email.split("@");
        if ( tab_email.length == 2 ) {
            if (tab_email[0].length() > 0 & tab_email[1].length() > 2) {
                String domain = (String) tab_email[1];
                String[] tab_domain = domain.split("[.]");
                if (tab_domain.length == 2) {
                    if (tab_domain[0].length() > 0 & tab_domain[1].length() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static public boolean isDate(String date) {
        String[] tab_date = date.split("/");
        if (tab_date.length == 3) {
            if (tab_date[0].length() == 2 & tab_date[1].length() == 2 & tab_date[2].length() == 4) {
                return true; // A terminer, verifier si numerique
            }
        }
        return false;
    }

    static public boolean isTel(String tel) {
        if (tel.length() == 10) {
            return true; // A terminer, verifier si numerique
        }
        return false;
    }

    static private void fileAppend(String fileName, String str) {
        try {
            File file = new File(fileName);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fw);
            out.append(str);
            out.close();
            fw.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    static public boolean inscriptionIsValid(String email, String mdp, String mdp2, String naissance, String tel, String nom, String prenom) throws IOException {

        if (isEmail(email) && mdp.equals(mdp2) && isDate(naissance) && isTel(tel)) {
            File users = new File("./users.txt");
            users.createNewFile(); // if file already exists will do nothing
            users.setWritable(true, false);
            try (BufferedReader br = new BufferedReader(new FileReader(users))) {
                String line;
                String[] tab;
                String mailToCheck;
                while ((line = br.readLine()) != null) {
                    if (line != "") {
                        tab = line.split(";");
                        mailToCheck = tab[0].split(":")[1];
                        if (email.equals(mailToCheck)) {
                            return false;
                        }
                    }
                }
                br.close();
                int hashmdp = mdp.hashCode();
                String str = "email:"+email+";mdp:"+hashmdp+";nom:"+nom+";prenom:"+prenom+";naissance:"+naissance+";tel:"+tel+"\n";
                fileAppend(users.getAbsolutePath(), str);
                return true;
            }
        }

        return false;
    }

    static public boolean connexionIsValid(String email, String mdp) throws IOException {
        if (isEmail(email) & !mdp.isEmpty()) {
            File users = new File("./users.txt");
            users.createNewFile(); // if file already exists will do nothing
            try (BufferedReader br = new BufferedReader(new FileReader(users))) {
                String line;
                String[] tab;
                String mailToCheck;
                String mdpToCheck;
                String hashmdp = "" + mdp.hashCode();
                while ((line = br.readLine()) != null) {
                    tab = line.split(";");
                    mailToCheck = tab[0].split(":")[1];
                    if (email.equals(mailToCheck)) {
                        mdpToCheck = tab[1].split(":")[1];
                        if(hashmdp.equals(mdpToCheck)) {
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

}
