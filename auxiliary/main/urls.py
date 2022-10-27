from django.urls import path
from . import views


urlpatterns = [
    path("", views.index, name="index"),
    path("transform/raster", views.transform_raster, name="transform_raster"),
    path("transform/vector", views.transform_vector, name="transform_vector"),
    path("mbox", views.generate_mbox, name="mbox"),
    path("es/put", views.put_ES, name="put_ES"),
    path("es/search", views.search_ES, name="search_ES"),
]