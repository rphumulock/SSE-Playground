## Javascript Implementations

You will have to generate new certs for http2. Can use openssl.

#### Generate Private Key:

openssl genpkey -algorithm RSA -out server.key

#### Generate Certificate Signing Request (CSR):

openssl req -new -key server.key -out server.csr

#### Generate Self-Signed Certificate:

openssl req -x509 -sha256 -days 365 -key server.key -in server.csr -out server.crt
