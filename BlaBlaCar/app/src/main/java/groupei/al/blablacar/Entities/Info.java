package groupei.al.blablacar.Entities;

public class Info {

    static private Info instance;

    static public Info getInstance() {
        if (instance == null) {
            instance = new Info();
        }
        return instance;
    }

    Utilisateur user;

    private Info(){

    }

    public static Utilisateur getUser(){
        return getInstance().user;
    }

    public static void setUser(Utilisateur user){
        getInstance().user = user;
    }




}
