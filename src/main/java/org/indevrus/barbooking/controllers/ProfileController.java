package org.indevrus.barbooking.controllers;

import org.indevrus.barbooking.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private ProfileService profileService;

    @GetMapping("/")
    public String find() {
        return "profile/find";
    }

    @GetMapping("/show")
    public String profile(@RequestParam("phone") String phone, @RequestParam("name") String name, Model model) {
        var bookings = profileService.findAll(phone, name);

        model.addAttribute("dateFormatter", new SimpleDateFormat("dd.MM.yyyy"));
        model.addAttribute("phoneNumber", phone);
        model.addAttribute("name", name);
        model.addAttribute("bookings", bookings);

        return "profile/show";
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
}
