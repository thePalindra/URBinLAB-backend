FROM  python:3.10

ENV PYTHONUNBUFFERED 1
ENV DJANGO_SETTINGS_MODULE authentication.settings

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        libldap2-dev \
        libsasl2-dev \
        ldap-utils \
    && rm -rf /var/lib/apt/lists/*

RUN python3 -m pip install --upgrade pip
RUN python3 -m pip install numpy
RUN python3 -m pip install Django
RUN python3 -m pip install requests
RUN python3 -m pip install django-cors-headers
RUN python3 -m pip install django-auth-ldap

WORKDIR /app

COPY . .

EXPOSE 6000

CMD [ "python", "manage.py", "runserver", "0.0.0.0:6000"]