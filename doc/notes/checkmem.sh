#!/bin/bash
# 统计docker内存占用
# 找出所有运行的容器
idNames=$(docker ps --format "{{.ID}}|{{.Names}},")

# 按,号分隔
OLD_IFS="$IFS"
IFS=","
arr=($idNames)
IFS="$OLD_IFS"

# 输出 Title
printf "%-15s %-30s %-15s\n" Id Name Mem

# 遍历所有容器
for item in ${arr[@]}; do
  # 容器ID和容器名字 按 | 分隔
  OLD_IFS="$IFS"
  IFS="|"
  array=($item)
  IFS="$OLD_IFS"

  # 当前容器的Pid
  pid=$(docker inspect -f '{{.State.Pid}}' ${array[0]})

  # 当前容器的内存
  mem=$(cat /proc/$pid/status | grep -e VmRSS | awk '{print $2}')

  # 输出结果
  printf "%-15s %-30s %-15s\n" ${array[0]} ${array[1]} $(($mem / 1024))M
done
