# 同步数据库脚本

current_date=`date -v-0d "+%Y%m%d"`

rm /Users/caihongwang/Workspaces/MyProgram_Git/Y_Z_K_J/oilStationMap.server/target/AllDataBase_${current_date}.sql

wget -P /Users/caihongwang/Workspaces/MyProgram_Git/Y_Z_K_J/oilStationMap.server/target/ http://www.91caihongwang.com/resourceOfOilStationMap/webapp/mysqlDataBak/AllDataBase_${current_date}.sql

mysql -ucaihongwang -phejinrong@520 -A < /Users/caihongwang/Workspaces/MyProgram_Git/Y_Z_K_J/oilStationMap.server/target/AllDataBase_${current_date}.sql
