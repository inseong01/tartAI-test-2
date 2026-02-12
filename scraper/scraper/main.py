from .url_utils import fetch_url, get_articles
from .loop_articles import loop_processing_articles


def scrape_prap_articles():
    url = "https://prap.kr/?cat=5"

    response = fetch_url(url)
    articles = get_articles(response.text)
    data = loop_processing_articles(articles)

    return data


scrape_prap_articles()
