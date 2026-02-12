from bs4.element import Tag
from urllib.parse import urlparse, parse_qs
import re


def get_post_url_tag(article: Tag):
    wrapper = article.find("div", class_="uicore-post-wrapper")

    if not wrapper:
        return

    a = wrapper.find("a")
    if not a:
        return

    url = a.get("href")
    if not url:
        return

    return url


# 아이디
def parsing_id(article: Tag) -> str:
    url = get_post_url_tag(article)
    if not url:
        return ""

    parsed = urlparse(url)
    query_dict = parse_qs(parsed.query)

    post_id = query_dict.get("p", [None])[0]
    return post_id


# 게시물 URL
def parsing_post_url(article: Tag) -> str:
    url = get_post_url_tag(article)
    if not url:
        return ""

    return url


# 제목
def parsing_title(article: Tag) -> str:
    h4 = article.find("h4", class_="uicore-post-title")
    if not h4:
        return ""

    span = h4.find("span")
    if not span:
        return ""

    title = span.get_text(strip=True)
    if not title:
        return ""

    return title


# 섬네일
def parsing_thumbnail(article: Tag) -> str:
    div = article.find("div", class_="uicore-cover-img")
    if not div:
        return ""

    style = div.get("style")
    if not style:
        return ""

    match = re.search(r"url\((.*?)\)", style)
    if not match:
        return ""

    return match.group(1)


# 카테고리
def get_category_tag(article: Tag):
    div = article.find("div", class_="uicore-post-category")
    if not div:
        return

    a = div.find("a")
    if not a:
        return

    return a


def parsing_category_name(article: Tag) -> str:
    a = get_category_tag(article)
    if not a:
        return ""

    title = a.get("title")
    if not title:
        return ""

    match = re.search(r"View\s(.*?)\sposts", title)
    if not match:
        return ""

    return match.group(1)


def parsing_category_url(article: Tag) -> str:
    a = get_category_tag(article)
    if not a:
        return ""

    href = a.get("href")
    if not href:
        return ""

    return href


# 발췌
def parsing_excerpt(article: Tag) -> str:
    div = article.find("div", class_="uicore-post-info")
    if not div:
        return ""

    p = div.find("p")
    if not p:
        return ""

    title = p.get_text(strip=True)
    trimmedText = " ".join(title.split())
    return trimmedText


# 작성자
def get_author_tag(article: Tag):
    a = article.find("a", rel="author")
    if not a:
        return

    return a


def parsing_author_name(article: Tag) -> str:
    a = get_author_tag(article)
    if not a:
        return ""

    name = a.get_text(strip=True)
    if not name:
        return ""

    return name


def parsing_author_url(article: Tag) -> str:
    a = get_author_tag(article)
    if not a:
        return ""

    url = a.get("href")
    if not url:
        return ""

    return url


# 게시일
def get_post_footer_tags(article: Tag):
    div = article.find("div", class_="uicore-post-footer")
    if not div:
        return

    return div


def parsing_published_date(article: Tag) -> str:
    footer = get_post_footer_tags(article)
    if not footer:
        return ""

    spans = footer.find_all("span")
    if not spans:
        return ""

    date_text = spans[-1].get_text(strip=True)
    return date_text
