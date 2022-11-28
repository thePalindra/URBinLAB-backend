FROM  python:3.10

WORKDIR /app

COPY . .

RUN apt-get update
RUN apt-get upgrade -y

RUN apt-get install -y libgdal-dev gdal-bin

RUN python3 -m pip install --upgrade pip
RUN python3 -m pip install --upgrade --no-cache-dir setuptools==58
RUN python3 -m pip install numpy
RUN python3 -m pip install Django
RUN pip install GDAL==$(gdal-config --version | awk -F'[.]' '{print $1"."$2}') --use-deprecated=legacy-resolver
RUN python3 -m pip install elasticsearch
RUN python3 -m pip install geojson
RUN python3 -m pip install django-cors-headers

ENV PYTHONUNBUFFERED=1

EXPOSE 5050

CMD [ "python", "manage.py", "runserver", "5050", "--noreload"]