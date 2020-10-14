#!/bin/sh

file="/root/git-repository/Minimal-E-Commerce-Management-System/target/management-0.0.1-SNAPSHOT.jar"

echo "=====Step-1: Enter Backend Project git Directory"
cd /root/git-repository/Minimal-E-Commerce-Management-System

echo "=====Step-2: Checkout git Branch For Master"
git checkout master

echo "=====Step-3: Git pull======================"
git pull

echo "=====Step-4: Delete Target Folder==========="
rm -rf target

echo "=====Step-5: Maven Package Project, Create Jar File======="
mvn clean package -Dmaven.test.skip=true


echo "=====Step-6: Check java -jar PID==============="
pwd

PID=$(ps -ef | grep "java -jar ${file}" | grep -v grep | awk '{print $2}')

if [ $PID ]; then
  echo "process id: $PID"
  kill -9 ${PID}
  if [ $? -ne 0 ]; then
     echo "kill is failed"
     exit 1
  else
    echo "kill is succeed"
  fi
else 
  echo "process is not run"
fi

if [ ! -f "$file" ]; then 
  echo "file is not exist"
  exit 2
fi

nohup java -jar ${file} &

PID=$(ps -ef | grep "java -jar ${file}" | grep -v grep | awk '{print $2}')

echo "=======Backend Project PID is >>>> ${PID}"


echo "================sleep 10s========================="
for i in {1..5}
do
        echo $i"s"
        sleep 1s
done