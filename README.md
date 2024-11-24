# AGENDA AI

## Sobre
RESTFul API em Java usando o framework spring boot para fornecer serviços seguros, escalaveis e robustos para o aplicativo de agendamento "AgendaAi"
## Instalacao 
Para baixar e exeecutar o projeto você precisa:
- Clonar o Repositorio: 
```sh
git clone git@github.com:Gebarito/AgendaAi-API.git
cd AgendaAi-API
```
IMPORTANTE!\
Após fazer alterações neste repositorio volte ao repositorio pai e atualize o submodulo com:
`git submodule update`
Ou então
`git add path_submodulo && git commit -m "chore: Atualiza subrepo"`

## Dependencias
### JVM, JDK & Maven
```sh
# JAVA
openjdk 21.0.4 2024-07-16
OpenJDK Runtime Environment (build 21.0.4+7-Ubuntu-1ubuntu224.04)
OpenJDK 64-Bit Server VM (build 21.0.4+7-Ubuntu-1ubuntu224.04, mixed mode, sharing)
javac 21.0.4
```
Também instale o gerenciador de pacotes Maven.
### Bibliotecas
(Lista com todas as dependencias)[https://github.com/Gebarito/AgendaAi-API/network/dependencies]

### Contribuindo
- crie uma nova branch ou de um fork no repositorio
- siga o guia de implementacao e commits
- Uma vez pronto squash em todos para que fique no maximo 1-2 commits
- Crie um Pull Request no submodulo (este repositorio) e a atualização de submodulo e documentação no repositorio pai
- Se o teste de "able to merge" passar e um contribuidor aprovar, o projeto pode ser mergeado!
- Apague a branch velha

## Subindo o projeto
Este projeto é hospedado na AWS
para subir é necessario:
- Criar um banco RDS postgre
- Colocar as informações em `application.properties`
- Subir o repositorio em um EC2 T3/T4.micro

## Contribuidores
Gabriel Iope
João Gebara
Maria Eduarda
Rafael Abreu
Rhamza Mourad
