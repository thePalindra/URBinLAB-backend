from django.urls import path
from . import views


urlpatterns = [
    path("", views.index, name="index"),
    path("transform/raster", views.transform_raster, name="transform_raster"),
    path("transform/vector", views.transform_vector, name="transform_raster")
]