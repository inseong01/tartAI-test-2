import requests
from bs4 import BeautifulSoup

from .exceptions.scraper_error import ScraperError


def fetch_url(url: str, timeout: int = 10):
    try:
        response = requests.get(url, timeout=timeout)
        response.raise_for_status()
        return response

    except requests.exceptions.Timeout as e:
        raise ScraperError(
            code="TIMEOUT",
            message="HTTP 요청이 타임아웃되었습니다.",
            detail={"url": url, "exception": type(e).__name__, "error": str(e)},
        )
    except requests.exceptions.ConnectionError as e:
        raise ScraperError(
            code="CONNECTION_ERROR",
            message="HTTP 연결에 실패했습니다.",
            detail={"url": url, "exception": type(e).__name__, "error": str(e)},
        )
    except requests.exceptions.HTTPError as e:
        status_code = e.response.status_code if e.response else None
        raise ScraperError(
            code="HTTP_ERROR",
            message=f"HTTP 상태 코드 오류: {status_code}",
            detail={
                "url": url,
                "status_code": status_code,
                "exception": type(e).__name__,
            },
        )
    except requests.exceptions.RequestException as e:
        raise ScraperError(
            code="REQUEST_FAILED",
            message=f"HTTP 요청 실패: {str(e)}",
            detail={"url": url, "exception": type(e).__name__, "error": str(e)},
        )


def get_articles(text: str):
    soup = BeautifulSoup(text, "html.parser")
    articles = soup.find_all("article", class_="uicore-post")
    if not articles:
        raise ScraperError(
            code="NO_ARTICLES_FOUND",
            message="HTML 문서에서 'article.uicore-post' 요소를 찾을 수 없습니다.",
            detail={"html_length": len(text)},
        )
    return articles
