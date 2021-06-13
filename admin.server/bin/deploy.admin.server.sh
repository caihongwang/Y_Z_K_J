#!/usr/bin/env bash
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "-------------------------------deploy admin.server application----------------------------------"
echo "-------------------------------deploy admin.server application----------------------------------"
echo "-------------------------------deploy admin.server application----------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"






echo "------------------Start pull new code 【admin.server】 from repository-----------------"
mkdir /opt/admin.server
cd /opt/deployAllApplication
rm -rf Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "----------------------------pull new code 【admin.server】 done------------------------"





echo "----------------------------start clone and package 【admin.server】-------------------"
cd Y_Z_K_J
mvn clean package -DskipTests
echo "----------------------------clone and package 【admin.server】 done--------------------"





echo "----------------------------install 【xxl-job-core】-------------------"
cd xxl-job-core
mvn clean install
echo "----------------------------install 【xxl-job-core】 done--------------------"





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







