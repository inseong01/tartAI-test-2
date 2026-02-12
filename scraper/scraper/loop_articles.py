from .article_parser import parse_article_tag
from .validator.required_key_validator import validate_required_key
from .validator.key_format_validator import validate_key_format


def loop_processing_articles(articles):
    data = []

    for article in articles:
        try:
            json_data = parse_article_tag(article)

            validate_required_key(json_data)
            validate_key_format(json_data)

            data.append(json_data)
        except:
            continue

    return data
