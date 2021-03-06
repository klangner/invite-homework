# Invite App Homework

## API Specification

Since initial specification can be changed here is slightly modified version which is implemented in this application.

### 1) CREATE INVITATION

**Request**: POST /invitation HTTP/1.1

**Content-Type**: application/json;charset=utf-8

```json
{ "invitee": "John Smith", "email": "john@smith.mx" }
```

**Response**: HTTP/1.1 201 Created

```json
{ "id":"1", "invitee": "John Smith", "email": "john@smith.mx", "status":"NotConfirmed" }
```

**Usage**
```bash
curl -H "Content-Type: application/json" -POST -d '{ "invitee": "John Smith", "email": "john@smith.mx" }' localhost:8080/invitation
```


### 2) LIST INVITATIONS

**Request**: GET /invitation HTTP/1.1

**Content-Type**: application/json;charset=utf-8

**Response**: HTTP/1.1 200 OK

```json
[
 { "id":"1", "invitee": "John Smith", "email": "john@smith.mx", "status":"NotConfirmed" }
]
```

**Usage**
```bash
curl -H "Content-Type: application/json" localhost:8080/invitation
```


### 3) CONFIRM INVITATION

**Request**: POST /invitation/<invitation-id>/confirm HTTP/1.1

**Content-Type**: application/json;charset=utf-8

**Response**: HTTP/1.1 200 OK

**Usage**
```bash
curl -H "Content-Type: application/json" -POST -d '' localhost:8080/invitation/1/confirm
```


### 4) DECLINE INVITATION

**Request**: POST /invitation/<invitation-id>/decline HTTP/1.1

**Content-Type**: application/json;charset=utf-8

**Response**: HTTP/1.1 200 OK

**Usage**
```bash
curl -H "Content-Type: application/json" -POST -d '' localhost:8080/invitation/1/decline
```


## Running

Run tests:
```bash
sbt test
```

Run with sbt
```bash
sbt run
```

Or shell script
```bash
./run.sh
```

Create and execute fat jar:
```bash
sbt assembly
java -jar target/scala-2.12/invite-homework.jar
```

# Redistributing

invite-homework source code is distributed under the Apache-2.0 license.
