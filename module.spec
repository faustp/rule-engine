%define major_ver __MAJOR_VER__
%define minor_ver __MINOR_VER__
%define module_name __MODULE_NAME__
%define service_name __SERVICE_NAME__
%define company_prefix __COMPANY_NAME__
%define trend /trend
%define trendlib /trend/java/%{module_name}

Name: %{company_prefix}-%{service_name}-%{module_name}
Summary: %{module_name} RPM Installer
Version: %{major_ver}
Release: %{minor_ver}%{?dist}
License: Copyright (C) 2011-2012 Trend Micro Incorporated. All rights reserved.
Packager: Solution Efficiency Team, Web Reputation Solution
#Vendor:
Group: Applications
Source: %{name}-%{major_ver}.%{minor_ver}.tar.gz
BuildRoot: %{_tmppath}/build-root-%{name}
Requires: %{company_prefix}-%{service_name}-wrs.common >= 1.1
Requires: %{company_prefix}-%{service_name}-wrs.common.murlin >= 2.0
#Provides: libc.so.6(GLIBC_PRIVATE)

Prefix: /trend
Url: http://www.trendmicro.com/

%description
%{module_name} is the common lib to hold URL in a pre-defined 
ADT. It provides the data interface and structure, and certain level
of marshalling/demarshalling for further usage.

%prep
echo "ok - building %{name}-%{major_ver}-%{minor_ver}"
if [ "$RPM_BUILD_ROOT" != "/" ] ; then
    echo "ok - removing old files [$RPM_BUILD_ROOT]"
    rm -rf $RPM_BUILD_ROOT
fi

%setup -q -n %{module_name}
%{__mkdir} -p $RPM_BUILD_ROOT%{trend}
%{__mkdir} -p $RPM_BUILD_ROOT%{trendlib}
%{__mkdir} -p $RPM_BUILD_ROOT%{trendlib}/%{major_ver}/
#Depricated: 20120527 - Roel
#%{__mkdir} -p $RPM_BUILD_ROOT%{trendlib}/%{major_ver}/lib/

%install
#Depricated: 20120527 - Roel
#cp -p dist/lib/* $RPM_BUILD_ROOT%{trendlib}/%{major_ver}/lib/
cp -p dist/%{module_name}* $RPM_BUILD_ROOT%{trendlib}/%{major_ver}/

%clean
echo "ok - cleaning up temporary files"
[ "$RPM_BUILD_ROOT" != "/" ] && rm -rf $RPM_BUILD_ROOT

%files
%defattr(0755,root,root,0755)
%{trendlib}/%{major_ver}/

%pre
# Create Other Required Folders
mkdir -p %{trend}
mkdir -p %{trendlib}
mkdir -p %{trendlib}/%{major_ver}
#Depricated: 20120527 - Roel
#mkdir -p %{trendlib}/%{major_ver}/lib/

%post
# Nothing to do for post installation
echo "ok - complete installing %{name}-%{major_ver}-%{minor_ver}"

%preun
if [ "$1" = 0 ] ; then
    if [ -d "%{trendlib}/%{major_ver}/" ] ; then
        echo "ok - uninstall removing %{trendlib}/%{major_ver} folder ..."
        rm -rf "%{trendlib}/%{major_ver}/"
    fi
elif [ "$1" = "1" ] ; then
    echo "ok - upgrading, nothing to delete"
fi

%changelog
* Thu May 27 2012 Roel Reyes 20120527
- Change folder structure of modules replace it with /trend/java/<component.name>/<version>
* Fri Dec 09 2011 Andrew Lee 20111209
- Initial Creation of spec file


