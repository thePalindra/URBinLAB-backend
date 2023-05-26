from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path("upload", views.upload, name="upload"),
    path("add", views.add, name="add"),
    path("delete", views.delete, name="delete"),
    path("save", views.save, name="save"),
    path("raster", views.raster, name="raster"),
    path("vector", views.vector, name="vector"),
]