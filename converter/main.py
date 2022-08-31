from osgeo import gdal
from flask import Flask, flash, request, redirect, url_for

UPLOAD_FOLDER = "/files"

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route("/transform/raster")
def transform_raster():
    file = request.files['file']
    ds = gdal.Open(file.filename, gdal.GA_ReadOnly)
    warp = gdal.Warp(file.filename, ds, dstSRS='EPSG:4326')
    return "<p>Hello, World!</p>"


@app.route("/transform/vector")
def transform_vector():
    return "<p>Hello, World!</p>"


if __name__ == "__main__":
    #app.run(debug=True)

    dataset = gdal.Open("something.jpg", gdal.GA_ReadOnly)
    print(dir(dataset))
    print(dataset.GetLayer())
    print("Driver: {}/{}".format(dataset.GetDriver().ShortName,
                                 dataset.GetDriver().LongName))
    print("Size is {} x {} x {}".format(dataset.RasterXSize,
                                        dataset.RasterYSize,
                                        dataset.RasterCount))
    print("Projection is {}".format(dataset.GetProjection()))
    geotransform = dataset.GetGeoTransform()
    print(geotransform)
    if geotransform:
        print("Origin = ({}, {})".format(geotransform[0], geotransform[3]))
        print("Pixel Size = ({}, {})".format(geotransform[1], geotransform[5]))

    print("Valor das contas: ({}, {})".format(geotransform[1]*dataset.RasterXSize+geotransform[0],
                                              geotransform[5]*dataset.RasterYSize+geotransform[3]))

    #warp = gdal.Warp("something.jpg", dataset, dstSRS='EPSG:4326')
