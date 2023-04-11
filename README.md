# Multithreaded_Client_Server
A multithreaded client-server communication system in Java for TCP and UDP protocols.

(1) programming language and version
	Java version: 11.0.17 
(2) integrated development environment (IDE) or editor used to develop the project.
	Apache Netbeans
(3)Steps for F membership tracking:
Clean and Build the Generic Node
Build Docker Server Containers like (Ex:1/5/3 containers)
Get these Docker ips and fill in /temp/nodes.cfg as(IP:PORT)
Then run this code in docker: sudo docker cp /path/of/file/in/localmachine ContainerId:filename
Example: sudo docker cp ../temp/nodes.cfg 5daeae1ea08c:nodes.cfg
Then execute the docker file.
Example: sudo docker exec -it d6a627be1db1 bash
Then run bigtc.sh in client docker
(4) Comment:
We have implemented F Static file membership tracking – file is not reread and T TCP membership tracking – servers are configured to refer to central membership server. All the operations would be performed in static file membership tracking(F) and in (T) TCP membership tracking only node membership has been implemented which can be checked through store operation.
