:: 先执行javafx:jlink，生成app目录
@echo off
set "out_dir=target/FileTransfer"
if exist "%out_dir%" (
    rd /s /q "%out_dir%"
    echo delete %out_dir%
)

"D:\Program Files\Java\jdk-17.0.13+11\bin\jpackage.exe" --name FileTransfer ^
--type app-image ^
--runtime-image target/app ^
--module com.xiaqi.filetransfer/com.xiaqi.filetransfer.gui.start.StartApplication ^
--icon FileTransfer.ico ^
--dest target