# Login Seguro — Spring Boot + Thymeleaf + MongoDB Atlas

Sistema acadêmico modular de autenticação e autorização, preparado para receber módulos de qualquer PFC futuro.

## Tecnologias
- Java 21
- Spring Boot 4.1.1
- Spring Security 7.1.x
- Spring Data MongoDB
- Spring Session Data MongoDB
- Thymeleaf
- Maven
- MongoDB Atlas

## Funcionalidades
- Cadastro, login e logout.
- Senhas armazenadas com PasswordEncoder (DelegatingPasswordEncoder/bcrypt).
- Validação de dados e política mínima de senha.
- Três perfis: ADMIN, MANAGER e USER.
- Autorização por rota.
- CRUD de usuários para ADMIN.
- Sessões persistidas no MongoDB.
- CSRF habilitado por padrão.
- Proteção contra fixação de sessão.
- Limite de três sessões simultâneas.
- Tema separado em `templates/themes/default` e CSS separado.

## MongoDB Atlas
1. Crie um cluster no MongoDB Atlas.
2. Crie um usuário de banco com senha forte.
3. Configure o acesso de rede de acordo com seu ambiente. Para produção, prefira regras restritivas em vez de liberar o cluster inteiro.
4. Copie a connection string.
5. Crie `.env` a partir de `.env.example` ou exporte as variáveis no sistema operacional.
6. Nunca publique a URI, senha ou `.env` no GitHub.

Exemplo:
`MONGODB_URI=mongodb+srv://usuario:senha@cluster.mongodb.net/?retryWrites=true&w=majority`

## Execução local
Requisitos: Java 21 e Maven 3.9+.

Linux/macOS:
```bash
export MONGODB_URI='mongodb+srv://...'
export MONGODB_DATABASE='login_seguro'
export SEED_ADMIN_ENABLED='true'
export SEED_ADMIN_USERNAME='admin'
export SEED_ADMIN_EMAIL='admin@example.com'
export SEED_ADMIN_PASSWORD='Troque_Esta_Senha_123!'
mvn spring-boot:run
```

Windows PowerShell:
```powershell
$env:MONGODB_URI='mongodb+srv://...'
$env:MONGODB_DATABASE='login_seguro'
$env:SEED_ADMIN_PASSWORD='Troque_Esta_Senha_123!'
mvn spring-boot:run
```

Se Maven estiver instalado globalmente, use `mvn spring-boot:run`.

Acesse `http://localhost:8080`.

## Usuário inicial
O seed cria um administrador somente se o usuário ainda não existir. Altere a senha imediatamente e, em produção, desative `SEED_ADMIN_ENABLED` após o primeiro provisionamento.

## Perfis
- `USER`: acesso autenticado ao painel.
- `MANAGER`: acesso às áreas de gestão previstas para o futuro.
- `ADMIN`: administração de usuários e todos os privilégios de gestão.

## Estrutura
```text
src/main/java/br/com/loginseguro/
├── config/       # segurança, sessão e seed
├── controller/   # endpoints web
├── dto/          # objetos de entrada/validação
├── model/        # documentos e enums
├── repository/   # persistência MongoDB
├── security/     # UserDetailsService
└── service/      # regras de negócio
src/main/resources/
├── static/css/   # apresentação
└── templates/
    ├── fragments/
    └── themes/default/
```

Para novas funcionalidades, use `feature/*`; para correções, `bugfix/*`; para preparação de versão, `release/*`; e para correções urgentes em produção, `hotfix/*`.

## Segurança para publicação
- Não publique `.env`.
- Não coloque credenciais diretamente em `application.yml`.
- Use HTTPS em produção e `SESSION_COOKIE_SECURE=true`.
- Restrinja o Network Access do MongoDB Atlas.
- Troque a senha do administrador inicial.
- Revise as permissões antes de criar novos roles.