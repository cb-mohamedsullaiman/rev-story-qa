# exit when any command fails
set -e

main(){
  init $*
  run $*
}

init(){
	
	#Declaring necessary variables
	APP_FOLDER="$HOME/work/chargebee-app"
	REV_FOLDER="$HOME/work/chargebee-analytics-v2"
	SCRIPTS_FOLDER="$APP_FOLDER/scripts"
	TOMCAT_BIN="tomcat/bin"

	#Loading the contents of utils
	. $SCRIPTS_FOLDER/functions.sh
	. $SCRIPTS_FOLDER/util_functions.sh


	checkArgs $*

}

checkArgs(){
	if [ $# == 1 ] && [  "$1" != *"job"*  ] ; then
			echoExit "Usage <app branch-name/tagname> <rev-story branch-name/ tagname>"
	else 
		echo "Sullaiman bug..."
	fi	
	checkReqArgs $# 0 2 "<app branch-name/tagname> <rev-story branch-name/ tagname>"	
}

run(){

	shutDownServers $APP_FOLDER $REV_FOLDER
	moveToDir $APP_FOLDER
	#checkoutToBranch $1
	addRevMicroService
	buildApp "appbuild/build.xml" "start_server" $3  
	buildRevStory $2
 }

buildRevStory(){
	moveToDir $REV_FOLDER
	#checkoutToBranch $1
	buildApp "appbuild/build.xml" "start_server"
}

shutDownServers(){
	for var in "$@"
	do
    	sh "$var/$TOMCAT_BIN/killTomcat.sh"
	done
}

buildApp(){

	#First argument - build file
	#Second argument - "start-server" argument for starting the server or not

	if [ ! -f $1 ]; then
		echoExit "\n*****$(pwd)/$1 does not exists*****\n" 
	fi
	$APP_FOLDER/ant/bin/ant -f $1
	if [ $# -gt 1 ] && [ $2 == "start_server" ]; then
		startServer "restart.sh" $3
	fi
}

startServer(){

	#First argument - Server start file
	cd $TOMCAT_BIN
	if [ "$3"  == *"job"* ] && [ $(pwd) -eq "$APP_FOLDER/$TOMCAT_BIN" ]; then
		script_output=$(sh $1 jobs start )&
		tailpid=$!
		sleep 5
		echo "Hi this is Vignesh"
		kill $pid
	else
		sh $1 &
		tailpid=$!
		sleep 10
		kill -9 $tailpid
	fi
}

addRevMicroService(){
	sed -i.bak 's/[[:space:]]*microservice.clusters[[:space:]]*=.*/microservice.clusters=["common", "general", "events", "analytics","analytics_postgre"]/' "$APP_FOLDER/webapp/conf/environment/microservice.conf"

	sed -i.bak 's/[[:space:]]*jobs.exec.enabled[[:space:]]*=.*/jobs.exec.enabled=false/' "$APP_FOLDER/webapp/conf/environment/core/core.conf"

}

checkReqArgs()
{
  if [ $1 -ne 0 ] && [ $1 -ne 2 ];
  then
  	echoExit "Usage $0 $4"
  fi
}

moveToDir(){
	if [ ! -d $1 ]; then
		echoExit "$1 does not exists"
	else
		cd $1
	fi

}

checkoutToBranch(){
	if [ -z $1 ]; then
		return
	fi
  git fetch --all
  executeAction "Checkout" "git checkout $1"
  git pull origin $1
}

executeAction(){
  echo -e "\n$1 : "
  stat=$(echo $($2))
  echo -e "\n$(echo $stat | tr ' ' '\n')\n"
}



main $*