@echo off

set programdir=C:\Programming\Minecraft\1.5.2
set packagedir=%programdir%\Packages
set repodir=%programdir%\Git
set forgedir=%programdir%\Forge
set fmldir=%forgedir%\fml
set mcpdir=%forgedir%\mcp
set slimelib=%repodir%\SlimevoidLibrary
cd %mcpdir%

if not exist "%slimelib%" GOTO :SVFAIL
GOTO :SV

:SV
if exist "%mcpdir%\src" GOTO :COPYSRC
GOTO :SVFAIL

:COPYSRC
if not exist "%mcpdir%\src-work" GOTO :CREATESRC
GOTO :SVFAIL

:CREATESRC
mkdir "%mcpdir%\src-work"
xcopy "%mcpdir%\src\*.*" "%mcpdir%\src-work\" /S
if exist "%mcpdir%\src-work" GOTO :COPYEC
GOTO :SVFAIL

:COPYEC
xcopy "%slimelib%\SV-common\*.*" "%mcpdir%\src\minecraft" /S
pause
call %mcpdir%\recompile.bat
call %mcpdir%\reobfuscate.bat
echo Recompile and Reobf Completed Successfully
pause

:REPACKAGE
if not exist "%mcpdir%\reobf" GOTO :SVFAIL
if exist "%packagedir%\SlimevoidLib" (
del "%packagedir%\SlimevoidLib\*.*" /S /Q
rmdir "%packagedir%\SlimevoidLib" /S /Q
)
mkdir "%packagedir%\SlimevoidLib"
xcopy "%mcpdir%\reobf\minecraft\*.*" "%packagedir%\SlimevoidLib\" /S
xcopy "%slimelib%\SV-resources\*.*" "%packagedir%\SlimevoidLib\" /S
echo "Slimevoid Library Packaged Successfully
pause

ren "%mcpdir%\src" src-old
echo Recompiled Source folder renamed
pause
ren "%mcpdir%\src-work" src
echo Original Source folder restored
pause
del "%mcpdir%\src-old" /S /Q
del "%mcpdir%\reobf" /S /Q
if exist "%mcpdir%\src-old" rmdir "%mcpdir%\src-old" /S /Q
if exist "%mcpdir%\reobf" rmdir "%mcpdir%\reobf" /S /Q
echo Folder structure reset
GOTO :SVCOMPLETE

:SVFAIL
echo Could not compile Slimevoid Library
pause
GOTO :EOF

:SVCOMPLETE
echo Slimevoid Library completed compile successfully
pause
GOTO :EOF

:EOF