Konnect : A social media app

[Presentation on Konnect](https://docs.google.com/presentation/d/1eB4AzLlFdBxkq7I2UY9-RCLlt3T_pPRQsHdVMDfRQ84/edit?usp=sharing)

![Alt](Konnect%20_%20A_Social_Media_App.jpeg)

Pre requisites:

A running *Redis* server. Port 6379\
A running *Mongo* server. Port 27017\
A running *Kafka* cluster. Bootstrap server port 9091


Each module (client, feed, post, ranking and user) is a separate microservice.\
Build in the following order.\
client\
user\
post\
ranking\
feed

Use scripts to build and run\
client -> installMinClients.sh\
user  -> install.sh\
post  -> install.sh\
ranking  -> install.sh\
client -> installAllClients.sh\
feed  -> install.sh

