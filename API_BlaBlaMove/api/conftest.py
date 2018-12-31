from .models import init_db

import pytest

@pytest.fixture(scope="session", autouse=True)
def db(request):
    init_db()