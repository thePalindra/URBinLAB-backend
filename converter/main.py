from osgeo import gdal, ogr, osr
from flask import Flask, flash, request, redirect, url_for
from werkzeug.utils import secure_filename
import geopandas

UPLOAD_FOLDER = "files/"
RESULT_FOLDER = "result/"

app = Flask(__name__)


@app.route("/transform/raster", methods=['GET', 'POST'])
def transform_raster():
    file = request.files["file"]
    filename = secure_filename(file.filename)
    file.save(UPLOAD_FOLDER+filename)

    aux = request.files.getlist("aux")
    for i in aux:
        name = secure_filename(i.filename)
        i.save(UPLOAD_FOLDER + name)

    ds = gdal.Open(UPLOAD_FOLDER+filename, gdal.GA_ReadOnly)
    warp = gdal.Warp(RESULT_FOLDER+filename, ds, dstSRS='EPSG:4326')
    geo_transform = warp.GetGeoTransform()
    res = {"origin": [geo_transform[0],
                      geo_transform[3]],
           "limit": [geo_transform[1]*warp.RasterXSize+geo_transform[0],
                     geo_transform[5]*warp.RasterXSize+geo_transform[3]]}
    return res


@app.route("/transform/vector", methods=['GET', 'POST'])
def transform_vector():
    file = request.files["file"]
    filename = secure_filename(file.filename)
    file.save(UPLOAD_FOLDER + filename)

    aux = request.files.getlist("aux")
    for i in aux:
        name = secure_filename(i.filename)
        i.save(UPLOAD_FOLDER + name)

    ds = ogr.Open(UPLOAD_FOLDER+filename, gdal.GA_ReadOnly)

    source = osr.SpatialReference()
    source.ImportFromEPSG(2927)

    target = osr.SpatialReference()
    target.ImportFromEPSG(4326)

    print(dir(ds.GetLayer().GetNextFeature().GetGeometryRef().GetSpatialReference()))

    transform = osr.CoordinateTransformation(source, target)
    return "shape"


if __name__ == "__main__":
    app.run(debug=True)
