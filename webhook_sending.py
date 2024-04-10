import requests
from trafilatura import feeds, fetch_url, extract
import json
import feedparser
from bs4 import BeautifulSoup
import html
import re
from datetime import datetime

# 웹훅 서버의 URL
webhook_url = 'https://webhook.site/44802742-aee0-471b-bef7-44a901f35a2b'

# 각 카테고리별 RSS 피드 URL
categories = {
    "국제": "https://newsis.com/RSS/international.xml",
    "금융": "https://www.newsis.com/RSS/bank.xml",
    "사회": "https://www.newsis.com/RSS/society.xml",
    "정치": "https://www.newsis.com/RSS/politics.xml",
    "경제": "https://www.newsis.com/RSS/economy.xml",
    "산업": "https://www.newsis.com/RSS/industry.xml",
    "IT·바이오": "https://www.newsis.com/RSS/health.xml"
}

for category, feed_url in categories.items():
    feed = feedparser.parse(feed_url)
    for entry in feed.entries:
        html_content = fetch_url(entry.link)
        text_content = extract(html_content, output_format="json")

        news_date = datetime.strptime(entry.published, '%a, %d %b %Y %H:%M:%S %z').strftime('%Y-%m-%d')
        news_link = entry.link
        title = html.unescape(entry.title).replace('\n', ' ')
        content = html.unescape(entry.summary).replace('\n', ' ')
        content = re.sub(r'◎공감언론 뉴시스 [\w@.]+', '', content)

        soup = BeautifulSoup(content, 'html.parser')
        cleaned_content = soup.get_text()

        news_item = {
            "newsDate": news_date,
            "newsLink": news_link,
            "title": title,
            "content": cleaned_content,
            "category": category
        }

        response = requests.post(webhook_url, json=news_item)
        print("서버 응답:", response.text)
        print(json.dumps(news_item, ensure_ascii=False, indent=4))