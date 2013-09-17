@echo on

set programdir=C:\Programming
set packagedir=%programdir%\Packages
set repodir=%programdir%\Repositories
set forgedir=%repodir%\MinecraftForge-1.5.2
set fmldir=%forgedir%\fml
set mcpdir=%forgedir%\mcp
set euryscore=%repodir%\EurysCore-FML
cd %mcpdir%

if not exist "%euryscore%" GOTO :ECFAIL
GOTO :EC

:EC
if exist "%mcpdir%\src" GOTO :COPYSRC
GOTO :ECFAIL

:COPYSRC
if not exist "%mcpdir%\src-work" GOTO :CREATESRC
GOTO :ECFAIL

:CREATESRC
mkdir "%mcpdir%\src-work"
xcopy "%mcpdir%\src\*.*" "%mcpdir%\src-work\" /S
if exist "%mcpdir%\src-work" GOTO :COPYEC
GOTO :ECFAIL

:COPYEC
xcopy "%euryscore%\SV-common\*.*" "%mcpdir%\src\minecraft" /S
pause
call %mcpdir%\recompile.bat
call %mcpdir%\reobfuscate.bat
echo Recompile and Reobf Completed Successfully
pause

:REPACKAGE
if not exist "%mcpdir%\reobf" GOTO :ECFAIL
if exist "%packagedir%\SlimevoidLib" (
del "%packagedir%\SlimevoidLib\*.*" /S /Q
rmdir "%packagedir%\SlimevoidLib" /S /Q
)
mkdir "%packagedir%\SlimevoidLib"
xcopy "%mcpdir%\reobf\minecraft\*.*" "%packagedir%\SlimevoidLib\" /S
xcopy "%euryscore%\SV-resources\*.*" "%packagedir%\SlimevoidLib\" /S
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
GOTO :ECCOMPLETE

:ECFAIL
echo Could not compile Eurys Core
pause
GOTO :EOF

:ECCOMPLETE
echo Slimevoid Library completed compile successfully
pause
GOTO :EOF

:EOF