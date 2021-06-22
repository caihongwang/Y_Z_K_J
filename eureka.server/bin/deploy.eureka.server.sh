#!/usr/bin/env bash
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "-------------------------------deploy eureka.server application----------------------------------"
echo "-------------------------------deploy eureka.server application----------------------------------"
echo "-------------------------------deploy eureka.server application----------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"






echo "------------------Start pull new code 【eureka.server】 from repository-----------------"
mkdir /opt/eureka.server
cd /opt/deployAllApplication
rm -rf /opt/deployAllApplication/Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "----------------------------pull new code 【eureka.server】 done------------------------"





echo "----------------------------start clone and package 【eureka.server】-------------------"
cd /opt/deployAllApplication/Y_Z_K_J
mvn clean package -DskipTests
echo "----------------------------clone and package 【eureka.server】 done--------------------"






echo "-------------------kill【eureka.server】 pid will sleep 20s.---------------------"
rm /opt/eureka.server/run.sh
cp /opt/deployAllApplication/Y_Z_K_J/eureka.server/bin/run.sh /opt/eureka.server/run.sh
sh /opt/eureka.server/run.sh stop
sleep 20s
echo "-------------------kill【eureka.server】 pid done.-------------------------------"
echo "-------------------start 【eureka.server】 project  will sleep 30s.--------------"
rm -r /opt/eureka.server/logs
rm /opt/eureka.server/eureka.server.jar
cp /opt/deployAllApplication/Y_Z_K_J/eureka.server/target/eureka.server.jar /opt/eureka.server/eureka.server.jar
cd /opt/eureka.server
sh /opt/eureka.server/run.sh start
sleep 20s
echo "-------------------start 【eureka.server】 project  done.------------------------"
echo "--------------------------------- start【eureka.server】 done--------------------"
echo "--------------------------------- start【eureka.server】 done--------------------"
echo "--------------------------------- start【eureka.server】 done--------------------"







