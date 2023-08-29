Konnect : A social media app

Prerequsites:

A running *Redis* server. Port 6379\
A running *Mongo* server. Port 27017\
A running *Kafka* cluster. Bootstrap server port 9091


Each module (client, feed, post, ranking and user) is a separate project.\
Build in the following order.\
client\
user\
post\
ranking\
feed

Use scripts to build and run\
client -> buildAllClients.sh\
user  -> buildAndRun.sh\
post  -> buildAndRun.sh\
ranking  -> buildAndRun.sh\
feed  -> buildAndRun.sh

