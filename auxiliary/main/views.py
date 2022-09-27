from django.shortcuts import render
from osgeo import gdal, ogr, osr
from django import forms
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.core.files.storage import FileSystemStorage 
import json
import requests
import time
import os



UPLOAD_FOLDER = "files/"
RESULT_FOLDER = "result/"


class UploadFileForm(forms.Form):
    title = forms.CharField(max_length=50)
    file = forms.FileField()


def index(request):
    a = json.dumps({"first": 1, "second": 2})
    return HttpResponse(a)


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
        res = {"origin": [geo_transform[0],
                          geo_transform[3]],
               "limit": [geo_transform[1] * warp.RasterXSize + geo_transform[0],
                         geo_transform[5] * warp.RasterXSize + geo_transform[3]]}
                         
        os.remove(UPLOAD_FOLDER + filename)
        for i in aux:
            name = i.name
            os.remove(UPLOAD_FOLDER + name)

    return HttpResponse(json.dumps(res))


# Unusable for now
@csrf_exempt
def transform_vector(request):
    print(request.method)
    print(request.FILES)
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


def conversion(filename):
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
        print(geom)
        outFeature = ogr.Feature(outLayerDefn)
        outFeature.SetGeometry(geom)
        for i in range(0, outLayerDefn.GetFieldCount()):
            outFeature.SetField(str(outLayerDefn.GetFieldDefn(i).GetNameRef()), str(inFeature.GetField(i)))
        outLayer.CreateFeature(outFeature)
        outFeature.Destroy()
        inFeature.Destroy()
        inFeature = inLayer.GetNextFeature()

    file.Destroy()
    outDataSet.Destroy()
    with open(RESULT_FOLDER + filename[:-3] + "json") as outfile:
        return json.load(outfile)
