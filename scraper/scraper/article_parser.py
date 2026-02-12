from bs4.element import Tag
from . import url_utils


def parse_article_tag(article: Tag):
    return {
        "id": url_utils.parsing_id(article),
        "url": url_utils.parsing_post_url(article),
        "title": url_utils.parsing_title(article),
        "thumbnail": url_utils.parsing_thumbnail(article),
        "category": {
            "name": url_utils.parsing_category_name(article),
            "url": url_utils.parsing_category_url(article),
        },
        "excerpt": url_utils.parsing_excerpt(article),
        "author": {
            "name": url_utils.parsing_author_name(article),
            "url": url_utils.parsing_author_url(article),
        },
        "published_date": url_utils.parsing_published_date(article),
    }
