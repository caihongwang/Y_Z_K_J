#!/usr/bin/env bash
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "-------------------------------deploy AllServer application----------------------------------"
echo "-------------------------------deploy AllServer application----------------------------------"
echo "-------------------------------deploy AllServer application----------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"






echo "------------------Start pull new code 【AllServer】 from repository-----------------"
cd /opt/deployAllApplication
rm -rf Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "----------------------------pull new code 【oilStationMap.server】 done------------------------"






echo "----------------------------start clone and package 【AllServer】-------------------"
cd Y_Z_K_J
mvn clean package -DskipTests
echo "----------------------------clone and package 【AllServer】 done--------------------"






echo "----------------------------install 【xxl-job-core】-------------------"
cd xxl-job-core
mvn clean install
echo "----------------------------install 【xxl-job-core】 done--------------------"






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






echo "-------------------kill【admin.server】 pid will sleep 20s.---------------------"
rm /opt/admin.server/run.sh
cp /opt/deployAllApplication/Y_Z_K_J/admin.server/bin/run.sh /opt/admin.server/run.sh
sh /opt/admin.server/run.sh stop
sleep 20s
echo "-------------------kill【admin.server】 pid done.-------------------------------"






echo "-------------------start 【admin.server】 project  will sleep 30s.--------------"
rm -r /opt/admin.server/logs/*.*
rm /opt/admin.server/admin.server.jar
cp /opt/deployAllApplication/Y_Z_K_J/admin.server/target/admin.server.jar /opt/admin.server/admin.server.jar
cd /opt/admin.server
sh /opt/admin.server/run.sh start
sleep 20s
echo "-------------------start 【admin.server】 project  done.------------------------"






echo "--------------------------------- start【admin.server】 done--------------------"
echo "--------------------------------- start【admin.server】 done--------------------"
echo "--------------------------------- start【admin.server】 done--------------------"






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






echo "-------------------kill【newMall.server】 pid will sleep 20s.---------------------"
rm /opt/newMall.server/run.sh
cp /opt/deployAllApplication/Y_Z_K_J/newMall.server/bin/run.sh /opt/newMall.server/run.sh
sh /opt/newMall.server/run.sh stop
sleep 20s
echo "-------------------kill【newMall.server】 pid done.-------------------------------"






echo "-------------------start 【newMall.server】 project  will sleep 30s.--------------"
rm -r /opt/newMall.server/logs
rm /opt/newMall.server/newMall.server.jar
cp /opt/deployAllApplication/Y_Z_K_J/newMall.server/target/newMall.server.jar /opt/newMall.server/newMall.server.jar
cd /opt/newMall.server
sh /opt/newMall.server/run.sh start
sleep 20s
echo "-------------------start 【newMall.server】 project  done.------------------------"





echo "--------------------------------- start【newMall.server】 done--------------------"
echo "--------------------------------- start【newMall.server】 done--------------------"
echo "--------------------------------- start【newMall.server】 done--------------------"

