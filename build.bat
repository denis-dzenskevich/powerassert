set JDK6=S:\\Soft\\jdk6
set JDK7=S:\\Soft\\jdk7
set JDK8=S:\\Soft\\jdk8
set MAVEN_CMD=call S:\Soft\maven\bin\mvn.bat

set MAVEN_OPTS=-DjvmArgs=-ea

echo "Building"
set JAVA_HOME=%JDK8%
%MAVEN_CMD% %MAVEN_OPTS% clean package install
if %errorlevel% neq 0 exit /b %errorlevel%

echo "Testing under JDK7"
set JAVA_HOME=%JDK7%
%MAVEN_CMD% %MAVEN_OPTS% clean test --projects powerassert-tests
if %errorlevel% neq 0 exit /b %errorlevel%

echo "Testing under JDK6"
set JAVA_HOME=%JDK6%
%MAVEN_CMD% %MAVEN_OPTS% clean test --projects powerassert-tests
if %errorlevel% neq 0 exit /b %errorlevel%
