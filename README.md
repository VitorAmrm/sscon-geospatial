# 📌 SSCON Geospatial

Projeto **Spring Boot 3.5.4** para gerenciamento de pessoas, cálculo de salários e idades com regras específicas.

---

## 🚀 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Web**
- **Spring Validation**
- **MapStruct** (mapeamento de DTOs)
- **Jib** (para gerar container sem Dockerfile)
- **JUnit 5** (testes unitários)
- **Docker Compose** (para subir o container)

---

## ⚙️ Como Rodar a Aplicação

### ▶️ Rodando localmente
```bash
./mvnw spring-boot:run
```

### 🐳 Rodando com Docker/Jib
## 1️⃣ Gerar imagem container com Jib:
```bash
./mvnw compile jib:dockerBuild -Dimage=sscon-geospatial:latest
```
2️⃣ Subir com Docker Compose:
```bash
docker compose up -d
```

O serviço estará disponível em:
👉 http://localhost:8080

📬 Endpoints Principais
- GET /person → Lista todas as pessoas
- POST /person → Cria uma nova pessoa
- GET /person/{id} → Detalha uma pessoa
- GET /person/{id}/age?output=YEARS|MONTHS|DAYS → Calcula idade
- GET /person/{id}/salary?output=FULL|MIN → Calcula salário
> mais informações podem ser encontradas no [swagger.yml](https://github.com/VitorAmrm/sscon-geospatial/blob/main/swagger.yml)

### 🧪 Testando com Postman
✅ Existe uma **coleção Postman** para testes dos endpoints com cenários já montados.

📂 **Coleção Postman:**  
[👉 Clique aqui para baixar a coleção](https://github.com/VitorAmrm/sscon-geospatial/blob/main/SSCON%20GEOSPATIAL.postman_collection.json)

📌 **Como usar:**
1. Abra o **Postman**
2. Importe o arquivo `.json` disponível no link acima
3. Selecione a coleção e clique em **Runner** para executar todos os testes automaticamente

🔍 Testes Unitários
Rodar todos os testes do projeto:
```bash
./mvnw test
```