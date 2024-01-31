# Configure SSL
Step 1: Generate a Private Key for the CA:
```bash
openssl genrsa -out ca-key 2048
```
Step 2: Create a Self-Signed CA Certificate
```bash
openssl req -new -x509 -key ca-key -days 365 -out ca-cert
```
Step 3: Generate a KeyStore for Kafka Server:

```bash
keytool -keystore kafka.server.keystore.jks -alias localhost -validity 365 -genkey -keyalg RSA
```
Step 4: Export the Kafka Server Certificate
```bash
keytool -keystore kafka.server.keystore.jks -alias localhost -certreq -file cert-file
```
Step 5: Sign the Kafka Server Certificate with Your CA
```bash
openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:password
```
Step 6: Import the CA Certificate and Signed Certificate into the Kafka KeyStore:
```bash
keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file ca-cert
keytool -keystore kafka.server.keystore.jks -alias localhost -import -file cert-signed
```
Step 7: Create a TrustStore for Kafka
```bash
keytool -keystore kafka.server.truststore.jks -alias CARoot -import -file ca-cert
```