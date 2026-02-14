from .url_utils import fetch_url, get_articles
from .loop_articles import loop_processing_articles

# from .mock.loader import load_mock_data

import json


def scrape_prap_articles():
    url = "https://prap.kr/?cat=5"

    response = fetch_url(url)
    articles = get_articles(response.text)
    data = loop_processing_articles(articles)

    # 테스트 용도
    # data = load_mock_data()

    print(json.dumps(data, ensure_ascii=False), flush=True)


scrape_prap_articles()
