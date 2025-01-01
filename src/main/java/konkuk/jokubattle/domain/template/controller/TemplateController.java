package konkuk.jokubattle.domain.template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController {

    @GetMapping("")
    public String hello() {
        return "hello";
    }
}
