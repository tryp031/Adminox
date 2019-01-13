package co.dmazo.adminox.user.domain;

public class UserFilterDto {
    private String name;
    private String login;
    private Integer[] status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer[] getStatus() {
        return status;
    }

    public void setStatus(Integer[] status) {
        this.status = status;
    }
}
