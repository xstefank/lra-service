# lra-service
Narayana LRA example application

## Running

1. build service-model
 * `cd {project.dir}/service-model`
 * `mvn clean install`

2. run minishift instance according to [setup file](setup.md)

3. create and build api-gateway app
 * `cd {project.dir}/api-gateway`
 * `mvn clean package fabric8:deploy`

4. create and build order-service app
 * `cd {project.dir}/order-service`
 * `mvn clean package fabric8:deploy`

5. create and build shipment-service app
 * `cd {project.dir}/shipment-service`
 * `mvn clean package fabric8:deploy`

6. create and build invoice-service app
 * `cd {project.dir}/invoice-service`
 * `mvn clean package fabric8:deploy`

7. create and build lra-coordinator app
 * `cd {project.dir}/lra-coordinator`
 * `mvn clean package fabric8:deploy`

## Execution

### complete

`curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"testProduct\"}" "http://order-service-myproject.`minishift ip`.nip.io/api/order"`

### compensate 

`curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"failInvoice\"}" "http://order-service-myproject.`minishift ip`.nip.io/api/order"`

`curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"failShipment\"}" "http://order-service-myproject.`minishift ip`.nip.io/api/order"`


