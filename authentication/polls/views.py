from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
from django_auth_ldap.backend import LDAPBackend
from django.http import HttpResponse
from django.core.wsgi import get_wsgi_application
from django.conf import settings
from django_auth_ldap.config import LDAPSearch
import socket


@csrf_exempt
def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")


@csrf_exempt
def outro(request):
    import ldap3

    ldap_server = "ldaps://ldap2012.cifa.fa.utl.pt"
    ldap_port = 636
    ldap_bind_dn = "urbbind@cifa.fa.utl.pt"
    ldap_bind_password = "Turing2023#."

    sock = socket.create_connection((ldap_server, ldap_port), timeout=5)
    print("Successfully connected to the LDAP server")
    sock.close()

    # Set up the LDAP connection
    server = ldap3.Server(ldap_server)
    print(server)
    connection = ldap3.Connection(server, user=ldap_bind_dn, password=ldap_bind_password)

    if connection.bind():
        print("LDAP connection successful")
        connection.unbind()
    else:
        print("LDAP connection failed:", connection.result)

    if request.method == 'POST':
        user = authenticate(username='urbbind@cifa.fa.utl.pt', password='Turing2023#.')
        if user is not None:
            return HttpResponse("Ok")
        else:
            return HttpResponse("Not Ok")