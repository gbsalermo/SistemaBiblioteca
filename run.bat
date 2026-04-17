@echo off
echo Limpando build antigo...
rmdir /s /q bin

echo Criando pasta bin...
mkdir bin

echo Compilando projeto...
javac -d bin src\application\Main.java src\view\TelaPrincipal.java src\model\*.java src\repository\*.java

if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b
)

echo Executando...
java -cp bin application.Main

pause