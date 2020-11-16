#!/usr/bin/env bash
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "-------------------------------deploy oilStationMap.server application----------------------------------"
echo "-------------------------------deploy oilStationMap.server application----------------------------------"
echo "-------------------------------deploy oilStationMap.server application----------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"




#echo "------------------Start clear 【oilStationMap.server】redis-----------------"
#db=1
#/opt/redis/redis-3.2.6/src/redis-cli -h localhost -p 6379 <<END
#select ${db}
#flushdb
#END
#echo "------------------Start clear 【oilStationMap.server】redis done-----------------"




echo "------------------Start pull new code 【oilStationMap.server】 from repository-----------------"
mkdir /opt/oilStationMap.server
cd /opt/deployAllApplication
rm -rf Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "----------------------------pull new code 【oilStationMap.server】 done------------------------"





echo "----------------------------start clone and package 【oilStationMap.server】-------------------"
cd Y_Z_K_J
mvn clean package -DskipTests
echo "----------------------------clone and package 【oilStationMap.server】 done--------------------"





echo "----------------------------install 【xxl-job-core】-------------------"
cd xxl-job-core
mvn clean install
echo "----------------------------install 【xxl-job-core】 done--------------------"





echo "-------------------kill【oilStationMap.server】 pid will sleep 20s.---------------------"
rm /opt/oilStationMap.server/run.sh
cp /opt/deployAllApplication/Y_Z_K_J/oilStationMap.server/bin/run.sh /opt/oilStationMap.server/run.sh
sh /opt/oilStationMap.server/run.sh stop
sleep 20s
echo "-------------------kill【oilStationMap.server】 pid done.-------------------------------"






echo "-------------------start 【oilStationMap.server】 project  will sleep 30s.--------------"
rm -r /opt/oilStationMap.server/logs
rm /opt/oilStationMap.server/oilStationMap.server.jar
cp /opt/deployAllApplication/Y_Z_K_J/oilStationMap.server/target/oilStationMap.server.jar /opt/oilStationMap.server/oilStationMap.server.jar
cd /opt/oilStationMap.server
sh /opt/oilStationMap.server/run.sh start
sleep 20s
echo "-------------------start 【oilStationMap.server】 project  done.------------------------"






echo "--------------------------------- start【oilStationMap.server】 done--------------------"
echo "--------------------------------- start【oilStationMap.server】 done--------------------"
echo "--------------------------------- start【oilStationMap.server】 done--------------------"







