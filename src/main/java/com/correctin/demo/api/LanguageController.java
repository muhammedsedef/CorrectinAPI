package com.correctin.demo.api;

import com.correctin.demo.constant.ApiEndpoints;
import com.correctin.demo.dto.CreateLanguageRequest;
import com.correctin.demo.dto.CreatePostRequest;
import com.correctin.demo.entity.Language;
import com.correctin.demo.entity.Post;
import com.correctin.demo.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiEndpoints.LANGUAGE_API_BASE_URL)
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping()
    public ResponseEntity<Language> save(@Valid @RequestBody CreateLanguageRequest createLanguageRequest) {
        return ResponseEntity.ok(this.languageService.save(createLanguageRequest));
    }

}
