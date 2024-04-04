package org.triumers.newsnippetGPT.Application.service;

import org.triumers.newsnippetGPT.common.exception.NewsContentNullException;
import org.triumers.newsnippetGPT.common.exception.NewsTitleNullException;
import org.triumers.newsnippetGPT.domain.dto.NewsDTO;

public interface PromptService {
    public String createPrompt(NewsDTO news) throws NewsTitleNullException, NewsContentNullException;
}
