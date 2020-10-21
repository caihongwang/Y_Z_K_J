#!/usr/bin/env bash
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "-------------------------------deploy zuul.server application----------------------------------"
echo "-------------------------------deploy zuul.server application----------------------------------"
echo "-------------------------------deploy zuul.server application----------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"






echo "------------------Start pull new code 【zuul.server】 from repository-----------------"
cd /opt/deployAllApplication
rm -rf Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "----------------------------pull new code 【zuul.server】 done------------------------"





echo "----------------------------start clone and package 【zuul.server】-------------------"
cd Y_Z_K_J
mvn clean package -DskipTests
echo "----------------------------clone and package 【zuul.server】 done--------------------"






echo "-------------------kill【zuul.server】 pid will sleep 20s.---------------------"
rm /opt/zuul.server/run.sh
cp /opt/deployAllApplication/Y_Z_K_J/zuul.server/bin/run.sh /opt/zuul.server/run.sh
sh /opt/zuul.server/run.sh stop
sleep 20s
echo "-------------------kill【zuul.server】 pid done.-------------------------------"






echo "-------------------start 【zuul.server】 project  will sleep 30s.--------------"
rm -r /opt/zuul.server/logs
rm /opt/zuul.server/zuul.server.jar
cp /opt/deployAllApplication/Y_Z_K_J/zuul.server/target/zuul.server.jar /opt/zuul.server/zuul.server.jar
cd /opt/zuul.server
sh /opt/zuul.server/run.sh start
sleep 20s
echo "-------------------start 【zuul.server】 project  done.------------------------"






echo "--------------------------------- start【zuul.server】 done--------------------"
echo "--------------------------------- start【zuul.server】 done--------------------"
echo "--------------------------------- start【zuul.server】 done--------------------"







