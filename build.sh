#!/bin/sh

curr_dir=`dirname $0`
local_dir=`cd $curr_dir; pwd`
build_conf="$local_dir/build.xml"
global_ver="1.0.1002"
version=""
test_build="false"
build_flag="$local_dir/BUILD_SUCCESS"
force_build="false"
build_rpm="build_rpm.sh"

########################################################
#                     MAIN PROGRAM                     #
########################################################
while getopts tfv: opt
do
  case "$opt" in
    v) global_ver="$OPTARG";;
    t) test_build="true";;
    f) force_build="true";;
    \?) help_exit;;
  esac
done

if [ "x${JAVA_HOME}" = "x" ] ; then
  echo "fail - you must specify JAVA_HOME env by modifying this shell or export JAVA_HOME=xxxx"
  exit -1
fi

if [ "x${ANT_HOME}" = "x" ] ; then
  echo "fail - you must specify ANT_HOME env by modifying this shell or export ANT_HOME=xxxx"
  exit -1
fi

ANT_BIN="$ANT_HOME/bin/ant"
if [ ! -e "$ANT_BIN" ] ; then
  echo "fail - $ANT_BIN does not exist on this machine, the env is not standard apache ANT"
  echo "fail - please contact $contact build master or to fix it"
  exit -1
fi

if [ -e "$local_dir/VERSION" ] ; then
  version=$(grep "local_ver=" $local_dir/VERSION | grep -v "^#" | grep -v "^$" | cut -d"=" -f2-)
  if [ "x${version}" = "x" ] ; then
    echo "fail - $local_dir/VERSION format is incorrect, missing local_ver=x.x.yyyy definition"
    exit -5
  fi
  echo "ok - applying local VERSION file with value $version"
else
  version=$global_ver
  echo "ok - applying global version $version"
fi

if [ "x${force_build}" = "xfalse" ] ; then
  if [ -e "$build_flag" ] ; then
    echo "ok - build already exist, don't rebuild again, unless force build is specified"
    if [ -e "$build_rpm" ] ; then
      chmod 755 ./$build_rpm
      ./$build_rpm
      ret=$?
      if [ "$ret" -ne "0" ] ; then
        echo "fail - rpm script build failed, exiting with error code $ret"
        exit $?
      else
        echo "ok - rpm build successfully"
      fi
    fi
    exit 0
  fi
else
  if [ -e "$build_flag" ] ; then
    rm -f "$build_flag"
    echo "ok - force_build enabled, delete previous build flag $build_flag and continue build"
  fi
fi

echo "ok - building $local_dir with global_ver=$global_ver version=$version"

$ANT_BIN -f "$build_conf" clean
ret=$?
if [ "$ret" -ne "0" ] ; then
  echo "fail - failed to clean up current build residuals, risky, exiting with error code $ret"
  exit $ret
fi

if [ "x${test_build}" = "xtrue" ] ; then
  echo "ok - performing test automation on new build $global_ver"
  $ANT_BIN -f "$build_conf" -Dversion=${version} -Dglobal_ver=${global_ver} build test
  ret=$?
  if [ "$ret" -ne "0" ] ; then
    echo "fail - failed to perform unit test before build release, exiting with error code $ret"
    exit $?
  fi
else
  $ANT_BIN -f "$build_conf" -Dversion=${version} -Dglobal_ver=${global_ver}
  ret=$?
  if [ "$ret" -ne "0" ] ; then
    echo "fail - build/compile code failed, exiting with error code $ret"
    exit $?
  fi
fi

date -u "+%s" > "$build_flag"

if [ -e "$build_rpm" ] ; then
  chmod 755 ./$build_rpm
  ./$build_rpm
  if [ "$ret" -ne "0" ] ; then
    echo "fail - rpm script build failed, exiting with error code $ret"
    exit $?
  fi
fi

exit 0
