package com.correctin.demo.service.impl;

import com.correctin.demo.dto.CreateLanguageRequest;
import com.correctin.demo.entity.Language;
import com.correctin.demo.entity.Post;
import com.correctin.demo.repository.LanguageRepository;
import com.correctin.demo.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Language save(CreateLanguageRequest createLanguageRequest) {
        Language language = modelMapper.map(createLanguageRequest, Language.class);
        return this.languageRepository.save(language);
    }
}
