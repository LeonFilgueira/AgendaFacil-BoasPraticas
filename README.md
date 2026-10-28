# Sobre o projeto
O programa Agenda Fácil tem como objetivo o gerenciamento de consultas médicas, permitindo marcar atendimentos, remarcá-los e listar os atendimentos registrados para um determinado dia.
# Compilando
```bash 
  javac -d out -sourcepath src src/AgendaBase.java
```
# Executando
```bash
  javac -cp out AgendaBase
```
# Gerando documentação
```bash 
  javadoc -d docs -sourcepath src src/AgendaBase.java src/Atendimento.java src/Validador.java -subpackages exceptions
```
