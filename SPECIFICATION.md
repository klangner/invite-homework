# Specification

Write an application in Scala to serve four endpoints described below. 
Selection of libraries and frameworks is up to you. 
The source directory must have the file run.sh which let you run the application in systems with Unix or run.bat for Windows. 
README.md file should contain a brief description of your approach to the assignment. 
The application doesn't have to save anything persistently, the answers can be permanently placed in the code 
although each addition is plus. 
We provide two samples for API requests, designing other two is part of the homework. 
Solving the homework should take around few hours.

Solution of this assignment should be placed in a Bitbucket repository with the history of changes. 
Bitbucket has a limitation which doesn't allow to grant access to a team. 
Thus in order to give us access you need to transfer ownership of the repository to the 'evojam-recruitment' 
team: https://confluence.atlassian.com/bitbucket/change-or-transfer-repository-ownership-289964397.html

## Task description

The business goal of the task is building a small rest service that allows managing party invitations. 
In the scope of the task we have:

  * Creation of invitation, which should trigger email notification to invitee
  * Listing invitations
  * Declining/confirming the invitation, both should trigger email notification to invitee

Sending of email can be mocked.

Note: This is not a complete specification. The task is defined vaguely on purpose so that you can show your skills, 
approach and attitude.


## Sample API requests

Please remember it’s a potential example, not specification!


### 1) CREATE INVITATION

Request:
POST /invitation HTTP/1.1

Content-Type: application/json;charset=utf-8

```json
{ "invitee": "John Smith", "email": "john@smith.mx" }
```

Response:
HTTP/1.1 201 Created


### 2) LIST INVITATIONS

Request:

GET /invitation HTTP/1.1

Response:

HTTP/1.1 200 OK

Content-Type: application/json;charset=utf-8

```json
[
 { "invitee": "John Smith", "email": "john@smith.mx" }
]
```


### 3) CONFIRM INVITATION

Request:

POST /invitation/confirm HTTP/1.1

```json
{ "invitee": "John Smith", "email": "john@smith.mx" }
```

Response:

HTTP/1.1 200 OK

Content-Type: application/json;charset=utf-8


### 4) DECLINE INVITATION

Request:

POST /invitation/decline HTTP/1.1

```json
{ "invitee": "John Smith", "email": "john@smith.mx" }
```

Response:

HTTP/1.1 200 OK

Content-Type: application/json;charset=utf-8

