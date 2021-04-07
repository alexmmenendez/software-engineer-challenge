#! /bin/bash
awk -v RS='\r\n' 'BEGIN { OFS = "," } {print $0, "$2a$10$qqKXKdsDBWXMpvz0tMoq..qcTiir2Pif0y6VJ2lIpHIcQtunnh.pW, 0"}' users.csv > usersv2.csv
sed '1iuuid, name, username, password, relevanceLevel' usersv2.csv > usersv3.csv
mongoimport --host mongodb --type csv -d picpay -c user --headerline --drop usersv3.csv

mongo --host mongodb < updateUserRelevanceLevel1.js
mongo --host mongodb < updateUserRelevanceLevel2.js
mongo --host mongodb < createIndex.js
