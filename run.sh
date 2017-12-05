#!/bin/bash

CHECK_MVN="$(type -p mvn)"
CHECK_JAVA="$(type -p java)"
if [ $CHECK_JAVA &> /dev/null ]
then
	if [ $CHECK_MVN &> /dev/null ]
	then
	mvn exec:java
	else
	curl -O "http://www-us.apache.org/dist/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.tar.gz"
	tar -xvzf apache-maven-3.5.2-bin.tar.gz
	rm -f apache-maven-3.5.2-bin.tar.gz
	expJava=$'\n\nexport JAVA_HOME='
	javaPath="$(/usr/libexec/java_home -v 1.8)"
	expM2=$'\nexport M2_HOME='
	m2Path="${PWD}""/apache-maven-3.5.2"
	expPath=$'\nexport PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin'
	cwd="${PWD}"
	cd ~
	touch ~/.bash_profile
	bp=$(<~/.bash_profile)
	echo "${bp}${expJava}${javaPath}${expM2}${m2Path}${expPath}" | pbcopy
	pbpaste > ~/.bash_profile
	source ~/.bash_profile
	cd $cwd
	mvn exec:java
	fi
else
	echo "It seems like you don't have Java yet. Please download and install Java SE Development Kit for macOS through this website: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html"
fi
