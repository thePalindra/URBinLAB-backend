from django.shortcuts import render
from osgeo import gdal, ogr, osr
from django import forms
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.files.storage import FileSystemStorage 
from elasticsearch import Elasticsearch
import json
import geojson
import os


DICTIONARY_PATH="/data/dictionary.txt"


def load_dictionary():
    with open(DICTIONARY_PATH, "r", encoding='utf8') as f:
        results = f.read().split("\n")

        return set(results)


UPLOAD_FOLDER = "files/"
RESULT_FOLDER = "result/"
INDEX = "urbinlab"
es = Elasticsearch('http://elastic-search:9200')
dic = load_dictionary()


class UploadFileForm(forms.Form):
    title = forms.CharField(max_length=50)
    file = forms.FileField()


def index(request):
    a = json.dumps({"first": 1, "second": es.ping()})
    return HttpResponse(a)


@csrf_exempt
def help(request):
    form = {
            "collection": request.POST["collection"],
            "document": request.POST["document"]
        }
    if request.method == 'POST':
        server = requests.post("http://localhost:8080/generic/add_collection", data=form)
        print(server)
    
    return HttpResponse(json.dumps(form))


@csrf_exempt
def transform_raster(request):
    if request.method == 'POST':
        file = request.FILES["file"]
        filename = file.name
        fs = FileSystemStorage()
        fs.save(UPLOAD_FOLDER + filename, file)
    
        aux = request.FILES.getlist("aux")
        for i in aux:
            name = i.name
            fs.save(UPLOAD_FOLDER + name, i)

        ds = gdal.Open(UPLOAD_FOLDER + filename, gdal.GA_ReadOnly)
        warp = gdal.Warp(RESULT_FOLDER + filename, ds, dstSRS='EPSG:4326')
        geo_transform = warp.GetGeoTransform()
        res = { "origin": [geo_transform[0],
                          geo_transform[3]],
                "limit": [geo_transform[1] * warp.RasterXSize + geo_transform[0],
                         geo_transform[5] * warp.RasterXSize + geo_transform[3]],
                "EPSG": 4326}

         
        ds = None
        warp = None
        geo_transform = None
        os.remove(UPLOAD_FOLDER + filename)
        for i in aux:
            name = i.name
            os.remove(UPLOAD_FOLDER + name)

    return HttpResponse(json.dumps(res))


@csrf_exempt
def transform_vector(request):
    if request.method == "POST":
        file = request.FILES["file"]
        filename = file.name
        fs = FileSystemStorage()
        fs.save(UPLOAD_FOLDER + filename, file)

        aux = request.FILES.getlist("aux")
        for i in aux:
            name = i.name
            fs.save(UPLOAD_FOLDER + name, i)
        
        res = conversion(filename)

        os.remove(UPLOAD_FOLDER + filename)
        for i in aux:
            name = i.name
            os.remove(UPLOAD_FOLDER + name)

    return HttpResponse(open(RESULT_FOLDER + filename[:-3] + "json"))


@csrf_exempt
def put_ES(request):
    if request.method == "POST":
        temp = set([])
        desc = ""
        for key in request.POST:
            value = str(request.POST[key])
            desc += value + " "

        for i in desc.lower().split(" "):
            dic.add(i)
            temp.add(i)

        with open(DICTIONARY_PATH, "w") as f:
            for j in temp:
                f.write(j)
                f.write("\n")

        print(temp)
        print(dic)
        doc = {"text": desc.lower()}
        resp = es.index(index=INDEX, id=request.POST["id"], document=doc)

    return HttpResponse(json.dumps(str(es.get(index=INDEX, id=request.POST["id"]))))


@csrf_exempt
def search_ES(request):
    if request.method == "POST":
        query = request.POST["query"].split(" ")
        clauses = [{
            "span_multi": {
                "match": {
                    "fuzzy": {
                        "text": {
                            "value": i, 
                            "fuzziness": 0
                        }
                    }
                }
            }
        } for i in query]

        payload = {
            "query": {
                "bool": {
                    "must": [{
                        "span_near": {
                            "clauses": clauses, 
                            "slop": 12, 
                            "in_order": False
                        }
                    }]
                }
            }
        }

        """query = {
            "query": {
                "fuzzy": {
                    "text": {
                        "value": search,
                        "fuzziness": "AUTO"
                    }
                }
            },
            "sort": [
                {
                    "_score": {
                        "order": "desc"
                    }
                }
            ]
        }"""
        resp = es.search(index=INDEX, body=payload, size="10000")
        res = []
        print(resp)
        for i in resp["hits"]["hits"]:    
            res.append(int(i["_id"]))

    return HttpResponse(json.dumps(res))


@csrf_exempt
def generate_mbox(request):
    if request.method == "POST":
        file = request.FILES["file"]
        filename = file.name
        fs = FileSystemStorage()
        fs.save(UPLOAD_FOLDER + filename, file)

        aux = request.FILES.getlist("aux")
        for i in aux:
            name = i.name
            fs.save(UPLOAD_FOLDER + name, i)
        
        res = conversion(filename)

        os.remove(UPLOAD_FOLDER + filename)
        for i in aux:
            name = i.name
            os.remove(UPLOAD_FOLDER + name)

    return HttpResponse(json.dumps(res))


@csrf_exempt
def get_dictionary(request):
    print(dic)
    return HttpResponse(json.dumps(list(dic)))


def conversion(filename):
    print("começou")
    file = ogr.Open(UPLOAD_FOLDER + filename, 1)
    inLayer = file.GetLayer()
    inSpatialRef = inLayer.GetSpatialRef()

    sr = osr.SpatialReference(str(inSpatialRef))
    res = sr.AutoIdentifyEPSG()
    srid = sr.GetAuthorityCode(None)

    outSpatialRef = osr.SpatialReference()
    outSpatialRef.ImportFromEPSG(4326)
    outSpatialRef.SetAxisMappingStrategy(osr.OAMS_TRADITIONAL_GIS_ORDER)

    coordTrans = osr.CoordinateTransformation(inSpatialRef, outSpatialRef)

    driver = ogr.GetDriverByName('GeoJSON')
    outDataSet = driver.CreateDataSource(RESULT_FOLDER + filename[:-3] + "json")
    outLayer = outDataSet.CreateLayer(filename)

    inLayerDefn = inLayer.GetLayerDefn()

    for i in range(0, inLayerDefn.GetFieldCount()):
        fieldDefn = inLayerDefn.GetFieldDefn(i)
        outLayer.CreateField(fieldDefn)

    # get the output layer's feature definition
    outLayerDefn = outLayer.GetLayerDefn()

    # loop through the input features
    inFeature = inLayer.GetNextFeature()

    while inFeature:
        geom = inFeature.GetGeometryRef()
        geom.Transform(coordTrans)
        outFeature = ogr.Feature(outLayerDefn)
        outFeature.SetGeometry(geom)
        outLayer.CreateFeature(outFeature)
        outFeature.Destroy()
        inFeature.Destroy()
        inFeature = inLayer.GetNextFeature()

    file.Destroy()
    outDataSet.Destroy()
    with open(RESULT_FOLDER + filename[:-3] + "json") as outfile:
        poly = geojson.load(outfile)
        return bbox(list(geojson.utils.coords(poly)))


def bbox(coord_list):
    box = []
    for i in (0,1):
        res = sorted(coord_list, key=lambda x:x[i])
        box.append((res[0][i],res[-1][i]))

    ret = {"origin": [box[0][0], box[1][1]],
                "limit": [box[0][1], box[1][0]],
                "EPSG": 4326}
    print(ret)
    return ret
