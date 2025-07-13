package passwords;

public class Password {

    private String login;
    private String value;

    public Password () {
        this.login = "";
        this.value = "";
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String newLogin) {
        login = newLogin;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String newValue) {
        value = newValue;
    }

    public void showPassword() {
        System.out.println("🙍‍♂️ Login: " + login);
        System.out.println("🔑 Password: " + value);
    }

}
