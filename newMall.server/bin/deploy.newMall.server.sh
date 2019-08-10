#!/usr/bin/env bash
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "-------------------------------deploy newMall.server application----------------------------------"
echo "-------------------------------deploy newMall.server application----------------------------------"
echo "-------------------------------deploy newMall.server application----------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"
echo "---------------------------------------------------------------------------------------"




echo "------------------Start clear 【newMall.server】redis-----------------"
db=2
/opt/redis/redis-3.2.6/src/redis-cli -h localhost -p 6379 <<END
select ${db}
flushdb
END
echo "------------------Start clear 【newMall.server】redis done-----------------"




echo "------------------Start pull new code 【newMall.server】 from repository-----------------"
cd /opt/deployAllApplication
rm -rf Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "----------------------------pull new code 【newMall.server】 done------------------------"





echo "----------------------------start clone and package 【newMall.server】-------------------"
cd Y_Z_K_J
mvn clean package -DskipTests
echo "----------------------------clone and package 【newMall.server】 done--------------------"






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







