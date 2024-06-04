## WSDL

`http://localhost:8080/ws/hotels.wsdl`

### Run

`mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring.profiles.active=local`

## Hotels

### Pagination and filter by `name`
```shell
curl --location 'http://localhost:8080/ws/' \
--header 'Content-Type: text/xml' \
--data '<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getHotelsRequest xmlns="http://www.choice.com/eduardo/spring/soap/gen">
        <name>Resort</name>
        <page>2</page>
    </getHotelsRequest>
  </soap:Body>
</soap:Envelope>
'
```
### Create

```shell
curl --location 'http://localhost:8080/ws/' \
--header 'Content-Type: text/xml' \
--data '<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <createHotelRequest xmlns="http://www.choice.com/eduardo/spring/soap/gen">
        <Hotel>
            <name>The Crown plaza</name>
            <address>Changuitos avenue # 33</address>
            <ratting>2</ratting>
            <amenities>3,4</amenities>
        </Hotel>
    </createHotelRequest>
  </soap:Body>
</soap:Envelope>
'
```
### Update

```shell
curl --location 'http://localhost:8080/ws/' \
--header 'Content-Type: text/xml' \
--data '<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <updateHotelRequest xmlns="http://www.choice.com/eduardo/spring/soap/gen">
        <Hotel>
            <id>12</id>
            <name>The Crown plaza new3</name>
            <address>Changuitos avenue # 35</address>
            <ratting>3</ratting>
            <amenities>3,4</amenities>
        </Hotel>
    </updateHotelRequest>
  </soap:Body>
</soap:Envelope>
'
```

### Delete 

```shell
curl --location 'http://localhost:8080/ws/' \
--header 'Content-Type: text/xml' \
--data '<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <deleteHotelRequest xmlns="http://www.choice.com/eduardo/spring/soap/gen">
        <id>9</id>
    </deleteHotelRequest>
  </soap:Body>
</soap:Envelope>
'
```

## Amenities

### Read
```shell
curl --location 'http://localhost:8080/ws/' \
--header 'Content-Type: text/xml' \
--data '<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getAmenitiesRequest xmlns="http://www.choice.com/eduardo/spring/soap/gen">
        
    </getAmenitiesRequest>
  </soap:Body>
</soap:Envelope>
'
```

## Common Response

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns2:commonResponse xmlns:ns2="http://www.choice.com/eduardo/spring/soap/gen">
            <ns2:success>false</ns2:success>
            <ns2:message>Hotel with name `The Crown plaza` already exists</ns2:message>
        </ns2:commonResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

## Authentication
