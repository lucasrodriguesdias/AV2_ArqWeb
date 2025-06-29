# API de Autenticação JWT Interna – AV2 Arquitetura Web

> Versão 1.0.0 • OAS 3.0  
> API REST com autenticação, geração e verificação de tokens JWT. Ideal para uso em arquiteturas de microserviços com autenticação centralizada.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **JWT (Json Web Token)**
- **SpringDoc Swagger UI**
- **H2 Database (Em memória)**
- **JPA / Hibernate**
- **Maven**

## 📌 Funcionalidades

- ✅ Registro e login de usuários com geração de token JWT
- ✅ Autorização baseada em roles: `ADMIN`, `USER`
- ✅ CRUD de Alunos (somente ADMIN)
- ✅ CRUD de Cursos (somente USER)
- ✅ Validação de tokens JWT
- ✅ Swagger UI para testes interativos

## 🎯 Papéis de Acesso

| Role     | Permissões                                                                 |
|----------|----------------------------------------------------------------------------|
| ADMIN    | Gerenciar alunos (CRUD completo)                                           |
| USER     | Gerenciar cursos (CRUD completo)                                           |

## 📚 Documentação Swagger

Acesse em:  
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

## 🔐 Endpoints da API

### 🔒 Autenticação

| Método | Rota            | Acesso | Descrição                                |
|--------|------------------|--------|------------------------------------------|
| POST   | `/auth/login`    | Público | Realiza login e retorna token JWT        |
| POST   | `/auth/validate` | Público | Valida se o token JWT ainda é válido     |

### 👥 Alunos (`/students`) – Role: ADMIN

| Método | Rota             | Descrição                         |
|--------|------------------|-----------------------------------|
| GET    | `/students/{id}` | Consulta um aluno específico      |
| GET    | `/students`      | Lista todos os alunos             |
| POST   | `/students`      | Cadastra um novo aluno            |
| PUT    | `/students/{id}` | Atualiza dados de um aluno        |
| DELETE | `/students/{id}` | Remove um aluno                   |

### 📘 Cursos (`/courses`) – Role: USER

| Método | Rota             | Descrição                           |
|--------|------------------|-------------------------------------|
| GET    | `/courses/{id}`  | Consulta um curso específico        |
| GET    | `/courses`       | Lista todos os cursos               |
| POST   | `/courses`       | Cadastra novo curso                 |
| PUT    | `/courses/{id}`  | Atualiza dados de um curso          |
| DELETE | `/courses/{id}`  | Remove um curso                     |

## 🧪 Como Usar

### 1. Clone o projeto

```bash
git clone https://github.com/lucasrodriguesdias/AV2_ArqWeb.git
cd AV2_ArqWeb
```

### 2. Rode localmente

```bash
./mvnw spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`

### 3. Autenticação via Token

1. Acesse `/auth/login`
2. Informe `username` e `password`
3. Copie o token JWT retornado
4. No Swagger, clique em **Authorize** e cole:
   ```
   Bearer SEU_TOKEN_AQUI
   ```

## 🧾 Exemplo de Requisição (Login)

```json
POST /auth/login
{
  "username": "admin",
  "password": "123456"
}
```

Resposta:

```json
"eyJhbGciOiJIUzI1NiIsInR5cCI6..."
```

## 🌐 Deploy Online

Este projeto foi submetido à plataforma [Render](https://render.com/) para publicação e testes de deploy em ambiente real.

> A aplicação está configurada para iniciar automaticamente via `Render.yaml` ou a partir da detecção do `Spring Boot` no `pom.xml`.

🔗 **URL do Render (após deploy):** *em breve*

Para testar localmente, use: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 👤 Autor

Desenvolvido por **Lucas Rodrigues Dias**  
Projeto acadêmico para a AV2 – Disciplina de Arquitetura Web – 2024

---
