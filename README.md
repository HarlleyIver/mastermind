# Mastermind Game

Jogo de adivinhação de cores com backend em Spring Boot + PostgreSQL e frontend em Angular.  
Autenticação via JWT. Arquitetura separada cliente-servidor.

## Pré-requisitos
- Java 17+, Maven 3.8+  
- Node.js 18+, Angular CLI 16+  
- PostgreSQL 15+

## Rodando Backend

git clone <URL_DO_REPOSITORIO>
cd backend
cp .env.example .env # ajuste conforme seu banco
mvn clean install
mvn spring-boot:run

API em `http://localhost:8080`

## Rodando Frontend

cd frontend
npm install

configure API_URL no .env ou environment.ts

ng serve

Acesse `http://localhost:4200`

## .env.example

DB_HOST=localhost
DB_PORT=5432
DB_NAME=mastermind
DB_USER=usuario
DB_PASSWORD=senha

JWT_SECRET=seu_token_secreto
JWT_EXPIRATION=3600000

API_URL=http://localhost:8080
