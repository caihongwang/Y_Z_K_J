#!/usr/bin/env bash
echo "------------------------------------------------------------------------------------"
echo "------------------------------------------------------------------------------------"
echo "------------------------------------------------------------------------------------"
echo "----------------deploy automation.server application--------------------------------"
echo "----------------deploy automation.server application--------------------------------"
echo "----------------deploy automation.server application--------------------------------"
echo "------------------------------------------------------------------------------------"
echo "------------------------------------------------------------------------------------"
echo "------------------------------------------------------------------------------------"




echo "------------------Start pull new code 【automation.server】 from repository----------"
mkdir -p /opt/automation.server
cd /opt/deployAllApplication
rm -rf /opt/deployAllApplication/Y_Z_K_J
git clone --depth=1 -b master https://github.com/caihongwang/Y_Z_K_J.git
echo "------------------pull new code 【automation.server】 done---------------------------"





echo "------------------start clone and package 【automation.server】----------------------"
cd /opt/deployAllApplication/Y_Z_K_J
mvn clean package -DskipTests
echo "------------------clone and package 【automation.server】 done-----------------------"





echo "------------------install 【xxl-job-core】-------------------------------------------"
cd /opt/deployAllApplication/xxl-job-core
mvn clean install
echo "------------------install 【xxl-job-core】 done--------------------------------------"





echo "-------------------start ready【defaultCommodPath】.---------------------"
mkdir -p /opt/defaultCommodPath
rm /opt/defaultCommodPath/1.Appium_start.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/1.Appium_start.sh /opt/defaultCommodPath/1.Appium_start.sh

rm /opt/defaultCommodPath/2.Automation_start.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/2.Automation_start.sh /opt/defaultCommodPath/2.Automation_start.sh

rm /opt/defaultCommodPath/3.Rethinkdb_start.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/3.Rethinkdb_start.sh /opt/defaultCommodPath/3.Rethinkdb_start.sh

rm /opt/defaultCommodPath/4.STF_start.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/4.STF_start.sh /opt/defaultCommodPath/4.STF_start.sh

rm /opt/defaultCommodPath/5.WebSSH_start.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/5.WebSSH_start.sh /opt/defaultCommodPath/5.WebSSH_start.sh

rm /opt/defaultCommodPath/rebootAllAndroidDevices.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/rebootAllAndroidDevices.sh /opt/defaultCommodPath/rebootAllAndroidDevices.sh

rm /opt/defaultCommodPath/shutDownAllAndroidDevices.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/shutDownAllAndroidDevices.sh /opt/defaultCommodPath/shutDownAllAndroidDevices.sh

rm /opt/defaultCommodPath/turnOffTheScreenForComputer.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/turnOffTheScreenForComputer.sh /opt/defaultCommodPath/turnOffTheScreenForComputer.sh

rm /opt/defaultCommodPath/turnOffTheScreenForAllAndroidDevices.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/turnOffTheScreenForAllAndroidDevices.sh /opt/defaultCommodPath/turnOffTheScreenForAllAndroidDevices.sh
echo "-------------------start ready【automation.server】done.-------------------------------"





echo "-------------------kill【automation.server】 pid will sleep 20s.---------------------"
rm /opt/automation.server/run.sh
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/bin/run.sh /opt/automation.server/run.sh
sh /opt/automation.server/run.sh stop
sleep 20s
echo "-------------------kill【automation.server】 pid done.-------------------------------"
echo "-------------------start 【automation.server】 project  will sleep 30s.--------------"
rm -r /opt/automation.server/logs
rm /opt/automation.server/automation.server.jar
cp /opt/deployAllApplication/Y_Z_K_J/automation.server/target/automation.server.jar /opt/automation.server/automation.server.jar
cd /opt/automation.server
sh /opt/automation.server/run.sh start
sleep 20s
echo "-------------------start 【automation.server】 project  done.------------------------"
echo "-------------------start 【automation.server】 done----------------------------------"
echo "-------------------start 【automation.server】 done----------------------------------"
echo "-------------------start 【automation.server】 done----------------------------------"







