import pytest
import requests

from scraper.url_utils import fetch_url
from scraper.exceptions.scraper_error import ScraperError


@pytest.mark.parametrize(
    "mock_exception, expected_code",
    [(None, None)],
)
def test_success_fetch_url(monkeypatch, mock_exception, expected_code):
    url = "https://example.com"

    def mock_get(*args, **kwargs):
        response = requests.models.Response()
        response.status_code = 200
        response._content = b"OK"
        return response

    monkeypatch.setattr(requests, "get", mock_get)

    response = fetch_url(url)
    assert response.status_code == 200
    assert response.text == "OK"


@pytest.mark.parametrize(
    "mock_exception, expected_code",
    [
        (requests.exceptions.Timeout("timeout"), "TIMEOUT"),
        (requests.exceptions.ConnectionError("connection failed"), "CONNECTION_ERROR"),
        (
            requests.exceptions.HTTPError(response=requests.models.Response()),
            "HTTP_ERROR",
        ),
        (requests.exceptions.RequestException("generic error"), "REQUEST_FAILED"),
    ],
)
def test_fail_fetch_url(monkeypatch, mock_exception, expected_code):
    url = "https://example.com"

    def mock_get(*args, **kwargs):
        if expected_code == "HTTP_ERROR":
            response = requests.models.Response()
            response.status_code = 404
            response._content = b"Not Found"
            response.reason = "Not Found"
            return response
        raise mock_exception

    monkeypatch.setattr(requests, "get", mock_get)

    with pytest.raises(ScraperError) as exc:
        fetch_url(url)
    assert exc.value.code == expected_code
    assert exc.value.detail["url"] == url
