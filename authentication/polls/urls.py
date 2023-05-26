from django.urls import path
from . import views

urlpatterns = [
    path("", views.index, name=""),
    path("outro", views.outro, name="outro"),
]