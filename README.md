# lra-service
Narayana LRA example application

## Running

### Minishift

1. run minishift instance according to the [setup file](setup.md)

#### Quick start

* `./run.sh`

this script assumes the configured and run minishift instance from the previous step

#### Manual steps

1. build service-model
 * `cd {project.dir}/service-model`
 * `mvn clean install`

2. create and build api-gateway app
 * `cd {project.dir}/api-gateway`
 * `mvn clean package fabric8:deploy`

3. create and build order-service app
 * `cd {project.dir}/order-service`
 * `mvn clean package fabric8:deploy`

4. create and build shipment-service app
 * `cd {project.dir}/shipment-service`
 * `mvn clean package fabric8:deploy`

5. create and build invoice-service app
 * `cd {project.dir}/invoice-service`
 * `mvn clean package fabric8:deploy`

6. create and build lra-coordinator app
 * `cd {project.dir}/lra-coordinator`
 * `mvn clean package fabric8:deploy`

### Docker compose

1. `mvn clean install -Dfabric8.skip`

2. `docker-compose up -d --build`

3. shutdown - `docker-compose down --remove-orphans`


## Running

### Minishift

#### complete

``curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"testProduct\"}" "http://order-service-myproject.`minishift ip`.nip.io/api/order"``

#### compensate

``curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"failInvoice\"}" "http://order-service-myproject.`minishift ip`.nip.io/api/order"``

``curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"failShipment\"}" "http://order-service-myproject.`minishift ip`.nip.io/api/order"``

### Docker compose

#### complete

`curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"testProduct\"}" "http://localhost:8080/api/order"`

#### compensate

`curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"failInvoice\"}" "http://localhost:8080/api/order"`

`curl -X POST --header "Content-Type: application/json" --header "Accept: */*" -d "{\"comment\": \"testComment\", \"price\": 100, \"productId\": \"failShipment\"}" "http://localhost:8080/api/order"`


