package Controller;

import Service.LoginService;
import Model.UserModel;

public class LoginController {
    LoginService loginService = new LoginService();

    public UserModel authenticate(String accNum, String pin) {
        return loginService.login(accNum, pin);
    }
}
