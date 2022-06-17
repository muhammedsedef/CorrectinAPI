package com.correctin.demo.service;

import com.correctin.demo.dto.CreateLanguageRequest;
import com.correctin.demo.entity.Language;

public interface LanguageService {

    Language save(CreateLanguageRequest createLanguageRequest);
}
