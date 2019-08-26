# exit when any command fails
set -e

main(){
	init $*
	run $*
}

init(){
	
	#Declaring necessary variables
	CUR_FOLDER="$(pwd)"
	APP_FOLDER="$HOME/work/chargebee-app"
	REV_FOLDER="$HOME/work/chargebee-analytics-v2"
	SCRIPTS_FOLDER="$APP_FOLDER/scripts"
	TOMCAT_BIN="tomcat/bin"
	APP_BRANCH="dev"
	REV_BRANCH="dev"
	CAN_CHECKOUT_APP=true
	CAN_CHECKOUT_REV=false
	JOB_ENABLED=true
	APP_BUILD=true
	REV_LOCAL_BUILD=false
	REPORT_CSV="reports.csv"
	EXP_CSV="expressions.csv"
	DIRECT_QUERIES="direct_queries.csv"

	#Loading the contents of utils
	. $SCRIPTS_FOLDER/functions.sh
	. $SCRIPTS_FOLDER/util_functions.sh

	checkDirs
	checkRequiredArgs $*

}

run(){
	shutDownServers  $REV_FOLDER $APP_FOLDER
	buildChargebeeApp 
	loadMetas
	buildRevStory 
}

checkDirs(){
	if [ ! -d $APP_FOLDER ]; then
		echoExit "Please clone chargebee-app under work directory (eg. $APP_FOLDER) "
	elif [ ! -d $REV_FOLDER ]; then
		echoExit "Please chone chargebee-analytics-v2 under work directory (eg. REV_FOLDER) "
	fi

}

checkRequiredArgs(){
	checkMaxArgs $#  6 "--job <true/false> --app-branch <branch-name/tagname> --rev-branch <branch-name/tagname>"	
	while getopts ":-" option ; do
		case "${option}" in
			- ) [ $OPTIND -ge 1 ] && optind=$(expr $OPTIND - 1 ) || optind=$OPTIND
         		eval OPTION="\$$optind"
         		OPTARG=$(echo $OPTION | cut -d'=' -f2)
         		OPTION=$(echo $OPTION | cut -d'=' -f1)
         		case $OPTION in
             		--job       ) 
						if [ $OPTARG=="false" ]; then   
							JOB_ENABLED=false 
						fi        
						;;
             		--app-branch  ) APP_BRANCH="$OPTARG"         ;;
					--no-app-build ) APP_BUILD=false 		;;
             		--rev-branch    ) REV_BRANCH="$OPTARG"               ;;
					--rev-local-build ) REV_LOCAL_BUILD=true 	;;
             		--help      ) printScriptUsage ;;
					- )  printScriptUsage ;;
             	* )  echoExit "\n*****Invalid option*****" ;;
         		esac
       			OPTIND=1
       			shift
      			;;
			\? )
      			echo "Invalid option: $OPTARG" 1>&2
      			;;
    		: )
      			echo "Invalid option: $OPTARG requires an argument" 1>&2
      			;;
		esac
	done
}

printScriptUsage(){
	echoExit "Usage : --job <true/false> --app-branch <branch name> --rev-branch <branch-name/tagname>" 
}


shutDownServers(){
	for var in "$@" ;
	do
		# if [ "$var" == "$APP_FOLDER" ]; then
		# 	cd $var/$TOMCAT_BIN
		# 	sh restart.sh jobs stop &
		# 	tailpid=$!
		# 	sleep 18
		# 	kill -9 $tailpid
		# fi
		sh "$var/$TOMCAT_BIN/killTomcat.sh"
	done
}

buildChargebeeApp(){
	moveToDir $APP_FOLDER
	if  $APP_BUILD  ; then 
		echo "\n***Starting to build chargebee application***"
		checkoutToBranch $APP_BRANCH
		addRevMicroService
		disableJob
		buildApp "appbuild/build.xml" "start_server" 
	else 
		echo "\n***Starting chargebee application without putting build***"
		startServer "restart_no_tail.sh"
	fi
}

buildRevStory(){
	moveToDir $REV_FOLDER
	if ! $REV_LOCAL_BUILD ; then 
		checkoutToBranch $REV_BRANCH
	fi
	buildApp "appbuild/build.xml" "start_server"
}

checkMaxArgs()
{
	if [ $1 -gt $2 ]
	then
		echoExit "Usage $0 $3"
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

disableJob(){
	echo "Making job exec as false"
	sed -i '' 's/jobs.exec.enabled=true/jobs.exec.enabled=false/g' $APP_FOLDER/webapp/conf/environment/core/core.conf
}

addRevMicroService(){
	sed -i.bak 's/[[:space:]]*microservice.clusters[[:space:]]*=.*/microservice.clusters=["common", "general", "events", "analytics","analytics_postgre"]/' "$APP_FOLDER/webapp/conf/environment/microservice.conf"

}

buildApp(){

	#First argument - build file
	#Second argument - "start-server" argument for starting the server or not

	if [ ! -f $1 ]; then
		echoExit "\n*****$(pwd)/$1 does not exists*****\n" 
	fi
	$APP_FOLDER/ant/bin/ant -f $1
	if [ $# -gt 1 ] && [ $2 == "start_server" ]; then
		startServer "restart_no_tail.sh" 
	fi
}

startServer(){

	#First argument - Server start file
	cd $TOMCAT_BIN
	echo "JOB : $JOB_ENABLED"
	if  [ $JOB_ENABLED ] && [ "$(pwd)"=="$APP_FOLDER/$TOMCAT_BIN" ]; then
		echo "Restarting server with Job pickers"
		sh $1 jobs start &
	else
		echo "Restarting server without Job Pickers"
		sh $1 jobs stop &
	fi
	echo "\n"
}

getLastProcessAndKill(){
		tailpid=$!
		echo "Sleeping for job"
		sleep 18
		kill -9 $tailpid
}

stringNotContains() { 

	#Inverted the condition based on the need
	[ ! -z "${2##*$1*}" ]; 
}

checkReqArgs()
{
	if [ $1 -ne 0 ] && [ $1 -ne 2 ];
	then
		echoExit "Usage $0 $4"
	fi
}

executeAction(){
	echo -e "\n$1 : "
	stat=$(echo $($2))
	echo -e "\n$(echo $stat | tr ' ' '\n')\n"
}

loadMetas(){
	cp "$CUR_FOLDER/reports.csv" "$REV_FOLDER/webapp/assets/javascript/server/javascripts/reports.csv"
	cp "$CUR_FOLDER/expressions.csv" "$REV_FOLDER/webapp/assets/javascript/server/javascripts/expressions.csv"
}

main $*