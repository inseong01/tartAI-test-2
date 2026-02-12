from bs4.element import Tag
from . import parsing_utils


def parse_article_tag(article: Tag):
    return {
        "id": parsing_utils.parsing_id(article),
        "url": parsing_utils.parsing_post_url(article),
        "title": parsing_utils.parsing_title(article),
        "thumbnail": parsing_utils.parsing_thumbnail(article),
        "category": {
            "name": parsing_utils.parsing_category_name(article),
            "url": parsing_utils.parsing_category_url(article),
        },
        "excerpt": parsing_utils.parsing_excerpt(article),
        "author": {
            "name": parsing_utils.parsing_author_name(article),
            "url": parsing_utils.parsing_author_url(article),
        },
        "published_date": parsing_utils.parsing_published_date(article),
    }
