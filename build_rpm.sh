#!/bin/sh

curr_dir=`dirname $0`
local_dir=`cd $curr_dir; pwd`
global_ver="1.0.1002"
version=""
test_build="false"
build_flag="$local_dir/BUILD_SUCCESS"
force_build="false"
service_name="wrs"
company_prefix="tm"

########################################################
#                     MAIN PROGRAM                     #
########################################################
while getopts v: opt
do
  case "$opt" in
    v) global_ver="$OPTARG";;
    \?) help_exit;;
  esac
done

if [ -e "$local_dir/VERSION" ] ; then
  version=$(grep "local_ver=" $local_dir/VERSION | grep -v "^#" | grep -v "^$" | cut -d"=" -f2-)
  if [ "x${version}" = "x" ] ; then
    echo "fail - $local_dir/VERSION format is incorrect, missing local_ver=x.x.yyyy definition"
    exit -5
  fi
  echo "ok - applying local VERSION file with value $version"
  comp_prefix=$(grep "company_prefix=" $local_dir/VERSION | grep -v "^#" | grep -v "^$" | cut -d"=" -f2-)
  serv_name=$(grep "service_name=" $local_dir/VERSION | grep -v "^#" | grep -v "^$" | cut -d"=" -f2-)
  if [ "x${comp_prefix}" != "x" ] ; then
    company_prefix="$comp_prefix"
  fi
  if [ "x${serv_name}" != "x" ] ; then
    service_name="$serv_name"
  fi
else
  version=$global_ver
  echo "ok - applying global version $version"
fi

spec_dir="/usr/src/redhat/SPECS/"
mkdir -p "/usr/src/redhat/SOURCES/"
mkdir -p "/usr/src/redhat/RPMS/"
mkdir -p "/usr/src/redhat/SRPMS/"
mkdir -p "/usr/src/redhat/BUILD/"
mkdir -p "$spec_dir"

module_name=`basename $(dirname $(pwd))`
mod_tmp_dir="/tmp/${module_name}-${version}.tmp/"
spec_file="$spec_dir/${module_name}.${version}.spec"

major_ver=`echo $version | cut -d. -f1-2`
minor_ver=`echo $version | cut -d. -f3-`

if [ ! -e "module.spec" ] ; then
  echo "fail - cannot find spec file ${module_name}.spec, can't continue, exiting" 1>&2
  exit -7
else
  rm -f "$spec_file"
  cat "module.spec" | sed -e "s/__MAJOR_VER__/$major_ver/g" | sed -e "s/__MINOR_VER__/$minor_ver/g" | sed -e "s/__MODULE_NAME__/$module_name/g" | sed -e "s/__SERVICE_NAME__/$service_name/g" | sed -e "s/__COMPANY_NAME__/$company_prefix/g" > "$spec_file"
  chmod 755 "$spec_file"
fi

if [ -e "$build_flag" ] ; then
  echo "ok - previous build process successfully completed, creating RPM installer"
  if [ -d "dist" ] ; then
    mkdir -p "${mod_tmp_dir}/${module_name}"
    cp -r dist "${mod_tmp_dir}/${module_name}/"
    tar -cvzf "/usr/src/redhat/SOURCES/${company_prefix}-${service_name}-${module_name}-${version}.tar.gz" -C "$mod_tmp_dir" ${module_name}
    rpmbuild -vv -ba --target=noarch --clean $spec_file
    ret=$?
    if [ "$ret" -ne "0" ] ; then
      echo "fail - failed to build rpm for $module_name, exiting with error code $ret"
      exit $?
    else
      rm -rf "${mod_tmp_dir}"
    fi
  fi
else
  echo "fail - build not complete yet, cannot generate RPM for $module_name, exiting" 1>&2
  exit -8
fi


exit 0


