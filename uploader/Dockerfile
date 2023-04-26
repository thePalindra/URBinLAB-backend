FROM  python:3.10

WORKDIR /app

COPY . .

RUN apt-get update
RUN apt-get upgrade -y

RUN python3 -m pip install --upgrade pip
RUN python3 -m pip install --upgrade --no-cache-dir setuptools==58
RUN python3 -m pip install numpy
RUN python3 -m pip install Django
RUN python3 -m pip install requests
RUN python3 -m pip install uuid

ENV PYTHONUNBUFFERED=1

EXPOSE 7000

CMD [ "python", "manage.py", "runserver", "0.0.0.0:7000"]