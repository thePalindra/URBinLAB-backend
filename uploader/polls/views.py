from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.files.storage import FileSystemStorage 
from django import forms
from datetime import *
import requests
import uuid
import os
import shutil
import json



FILES_PATH = "/data/uploads/"


def starter():
    cleaned = [x[0] for x in os.walk(FILES_PATH)]
    
    for i in cleaned:
        if i != FILES_PATH:
            shutil.rmtree(i)

    return dict()


files = starter()


class UploadFileForm(forms.Form):
    title = forms.CharField(max_length=50)
    file = forms.FileField()


@csrf_exempt
def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")


@csrf_exempt
def save(request):
    if request.method == 'POST':
        print(request.POST["files"])
        temp = files[request.POST["files"]]
        for i in temp["files"]:
            payload = {"document": request.POST["id"]}
            file = {
                    "file": open(i, "rb")
                }
            r = requests.post("http://main-backend:8080/file/add", data=payload, files=file)

    clean_done(request.POST["files"])
    return HttpResponse(json.dumps(str("Ok")))


@csrf_exempt
def raster(request):
    if request.method == 'POST':
        request.POST["file"]
        temp = []
        for i in files[request.POST["id"]]["files"]:
            if i.split("/")[-1] == request.POST["file"]:
                temp.append(("file", open(i, "rb")))
            else:
                temp.append(("aux", open(i, "rb")))

        mbox = file_upload(temp, "transform/raster")
        clean_expired()

    return HttpResponse(mbox)


@csrf_exempt
def vector(request):
    if request.method == 'POST':
        request.POST["file"]
        temp = []
        for i in files[request.POST["id"]]["files"]:
            if i.split("/")[-1] == request.POST["file"]:
                temp.append(("file", open(i, "rb")))
            else:
                temp.append(("aux", open(i, "rb")))

        mbox = file_upload(temp, "mbox")

        clean_expired()

    return HttpResponse(mbox)


@csrf_exempt
def upload(request):
    if request.method == 'POST':
        fs = FileSystemStorage(location=FILES_PATH)
        key = uuid.uuid4()
        names = []
        os.makedirs(FILES_PATH + str(key))

        temp = request.FILES.getlist("files")
        for i in temp:
            name = FILES_PATH + str(key) + "/" + i.name
            fs.save(name, i)
            names.append(name)

        files[str(key)] = {
                "files": names,
                "expired": datetime.now()+timedelta(days=2)
            }

        clean_expired()

    return HttpResponse(json.dumps(str(key)))


@csrf_exempt
def add(request):
    if request.method == 'POST':
        fs = FileSystemStorage(location=FILES_PATH)
        key = request.POST["key"]
        file = request.FILES["file"]
        name = FILES_PATH + str(key) + "/" + file.name

        fs.save(name, file)

        files[key]["files"].append(name)

    return HttpResponse(json.dumps(str(key)))


@csrf_exempt
def delete(request):
    if request.method == 'POST':
        key = request.POST["key"]
        file = request.POST["file"]
        name = FILES_PATH + str(key) + "/" + file

        os.remove(name)

        files[key]["files"].remove(name)

    return HttpResponse(json.dumps(str(key)))


def file_upload(temp_files, transform):
    r = requests.post("http://aux-backend:5050/" + transform, files=temp_files)

    return r.content


def clean_expired():
    for k,v in files.items():
        if datetime.now() > v["expired"]:
            del files[k]
            shutil.rmtree(FILES_PATH + str(k))


def clean_done(key):
    del files[key]
    shutil.rmtree(FILES_PATH + key)