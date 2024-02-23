package com.api.request.controller;


import com.api.request.controller.dto.Text;
import com.api.request.service.OpenApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OpenApiController {
    private final OpenApiService openApiService;

    public OpenApiController(OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

    @GetMapping("/translate")
    public String translate(Model model) {
        model.addAttribute("text", new Text());

        return "translate";
    }

    @PostMapping("/translate")
    public void translatePost(Model model, @ModelAttribute Text text) {
        String translatedText = openApiService.translate(text);
        model.addAttribute("transText", translatedText);
    }
}
