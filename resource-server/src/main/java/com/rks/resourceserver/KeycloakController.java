package com.rks.resourceserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class KeycloakController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String parseCallbackURI(@RequestParam String session_state,
                                   @RequestParam String code) {
        return code;
    }
}
