package org.triumers.newsnippetGPT.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.newsnippetGPT.common.exception.NewsContentNullException;
import org.triumers.newsnippetGPT.common.exception.NewsTitleNullException;
import org.triumers.newsnippetGPT.domain.dto.NewsDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceTest {

    @Autowired
    private OpenAIService openAIService;

    @DisplayName("프롬프트 생성 테스트")
    @Test
    void createPromptTest() throws NewsTitleNullException, NewsContentNullException {

        // given
        NewsDTO news = new NewsDTO();
        news.setTitle("뉴스 제목");
        news.setContent("뉴스 내용");

        // when
        String prompt = openAIService.createPrompt(news);

        //then
        assertEquals(prompt, "너는 시사 평론가이고 나는 너에게 시사 뉴스 기사를 제공해 해당 시사에 대한 문제를 만들고자 한다." +
                "\\\\ 은 줄바꿈 했음을 의미한다.\\\\다음은 뉴스 기사이다.\\\\" +
                "분야 : {" + news.getCategory() + "}\\\\" +
                "제목 : {" + news.getTitle() + "}\\\\" +
                "내용 : {" + news.getContent() + "}\\\\" +
                "우리는 취업 준비를 위해 시사 상식을 공부하는 사람들을 대상으로 간단한 시사 퀴즈를 제공할 것이다.\\\\" +
                "위의 뉴스 기사에 대한 요약본을 생성해주길 원한다.\\\\" +
                "그리고 그 요약본을 기반으로 한 시사문제 한 개를 생성하기를 원한다.\\\\" +
                "문제의 보기는 A,B,C,D 네 개로 이루어져 있으며 보기는 숫자나 수치에 대한 문제가 아닌 기사 내용에서 " +
                "시사 상식 증진에 도움이 될만한 답변이어야만 한다.\\\\" +
                "이 서비스를 이용하는 사람들은 문제만을 제공할 예정이며 기사에 대한 내용은 제공하지 않을 것이다.\\\\" +
                "기사와 요약은 사용자가 정답을 제출한 뒤 제공할 것이다.\\\\" +
                "우리는 너의 답변을 파싱하여 DB에 저장할 것이다.\\\\" +
                "따라서 너는 반드시 다음의 답변 예시 형태로 답해야한다. 각 답변은 {} 를 이용해 구분한다.\\\\" +
                "예시에 // 뒤에 작성된 내용은 해당 줄에 대한 설명이다. 실제 답변에는 포함되지 않는다.\\\\" +
                "보기 중 A는 정답인 내용만 들어간다.\\\\" +
                "B,C,D는 오답인 내용으로 구성되어야 한다.\\\\" +
                "예시\\\\" +
                "기사 요약 : {에르메스와 펜디, 샤넬 등 명품 브랜드들은 우크라이나를 상대로 한 '특별 군사 작전' " +
                "이후로 영업을 잠정 중단했다. 유럽 연합이 300유로(약 43만 6,000원) 이상의 사치품 수출을 제한을 하면서 " +
                "제품 공급이 어려워졌기 때문이다. 이 상황이 개선되지 않고 장기화되며 임대료만 빠져나가 손해를 본 끝에 " +
                "브랜드들은 철수를 결정하고 있다.} // 기사 내용에 대한 요약이 들어갈 자리이다.\\\\" +
                "문제 내용 : {최근 세계적인 명품 브랜드들이 러시아에서 매장을 철수한 이유는 무엇인가요?} // 문제가 들어갈 자리이다.\\\\" +
                "A : {유럽 연합의 사치품 수출 제한이 길어짐에 따라 영업을 하지 못하는 상태로 임대료만 빠져나갔기 때문이다.} " +
                "// 정답인 보기가 들어갈 자리이다.\\\\" +
                "B : {우크라이나 전쟁으로 인해 사업장이 공격받았기 때문이다.} // 오답인 보기가 들어갈 자리이다.\\\\" +
                "C : {러시아 대통령 블라디미르 푸틴이 사업 철수를 명령했기 때문이다.} // 오답인 보기가 들어갈 자리이다.\\\\" +
                "D : {러시아가 디폴트 선언을 했기 때문이다.} // 오답인 보기가 들어갈 자리이다.");
    }

    @DisplayName("뉴스 제목 null exception 테스트")
    @Test
    void newsTitleNullExceptionTest() {

        // given
        NewsDTO news = new NewsDTO();
        news.setContent("뉴스 내용");

        // when, then
        assertThrows(NewsTitleNullException.class, () -> {
            openAIService.createPrompt(news);
        });
    }
    @DisplayName("뉴스 내용 null exception 테스트")
    @Test
    void newsContentNullExceptionTest() {

        // given
        NewsDTO news = new NewsDTO();
        news.setTitle("뉴스 제목");

        // when, then
        assertThrows(NewsContentNullException.class, () -> {
            openAIService.createPrompt(news);
        });
    }
}