#息屏所有Android设备
adb devices | awk '{print $1}' | xargs -I {} adb -s {} shell input keyevent 26
