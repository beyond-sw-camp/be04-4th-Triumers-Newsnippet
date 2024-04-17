package org.triumers.newsnippetGPT.domain.service;

import org.springframework.stereotype.Service;
import org.triumers.newsnippetGPT.common.exception.NewsContentNullException;
import org.triumers.newsnippetGPT.common.exception.NewsTitleNullException;
import org.triumers.newsnippetGPT.domain.dto.NewsDTO;

@Service
public class PromptValidationService {

    public void isNewsNull(NewsDTO news) throws NewsTitleNullException, NewsContentNullException {
        if (news.getTitle() == null) {
            throw new NewsTitleNullException();
        }
        if (news.getContent() == null) {
            throw new NewsContentNullException();
        }
    }
}
