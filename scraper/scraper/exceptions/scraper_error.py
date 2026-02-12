class ScraperError(Exception):
    def __init__(self, code: str, message: str, detail: dict | None = None):
        self.code = code
        self.message = message
        self.detail = detail
        super().__init__(message)
