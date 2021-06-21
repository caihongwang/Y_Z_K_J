#关机所有Android设备
adb devices | awk '{print $1}' | xargs -I {} adb -s {} reboot -p
