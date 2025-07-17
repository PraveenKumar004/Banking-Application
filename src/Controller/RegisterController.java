package Controller;

import Service.RegisterService;

public class RegisterController {
    RegisterService registerService = new RegisterService();

    public boolean register(String name, String email, String phone, String dob, String pin) {
        return registerService.registerNewUser(name, email, phone, dob, pin);
    }
}
