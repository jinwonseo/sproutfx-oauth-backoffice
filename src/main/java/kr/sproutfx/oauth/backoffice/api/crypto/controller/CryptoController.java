package kr.sproutfx.oauth.backoffice.api.crypto.controller;

import kr.sproutfx.oauth.backoffice.common.base.BaseController;
import kr.sproutfx.oauth.backoffice.common.utilities.CryptoUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/crypto")
public class CryptoController extends BaseController {
    @GetMapping(value = "/encrypt")
    public StructuredBody encrypt(@RequestParam(value = "plain-text") String plainText) {
        return StructuredBody.content(CryptoUtils.encrypt(plainText));
    }

    @GetMapping(value = "/decrypt")
    public StructuredBody decrypt(@RequestParam(value = "encrypted-text") String encryptedText) {
        return StructuredBody.content(CryptoUtils.decrypt(encryptedText));
    }
}
