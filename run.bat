@echo off
echo Limpando build antigo...
if exist bin rmdir /s /q bin

echo Criando pasta bin...
mkdir bin

echo Compilando projeto...
javac -d bin -sourcepath src src/application/Main.java

if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b
)

echo Executando...
java -cp bin application.Main

pause