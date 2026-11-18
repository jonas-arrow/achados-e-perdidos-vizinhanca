# Achados e Perdidos Vizinhança (App Android + API REST)

Aplicação full-stack desenvolvida para conectar moradores de uma mesma vizinhança no registro, busca e devolução de objetos perdidos ou encontrados.

## 🏗️ Arquitetura do Projeto

O repositório está dividido em duas frentes principais:
- **`AchadosPerdidosApp/` (Mobile Android):** Aplicativo nativo em Kotlin com telas de autenticação (`LoginActivity`), feed de ocorrências (`MainActivity` + `PostAdapter`), cadastro de novos itens (`NovoPostActivity`), persistência local (`DatabaseHelper` e `PostDao`) e comunicação HTTP com o servidor (`ApiService`).
- **`ServidorVizinhanca/` (Back-end):** API REST em Python (`main.py`) responsável pela integração e sincronização dos dados entre os usuários.
- **`Projeto_Desenvolvimento_Mobile.pdf`:** Documentação técnica, fluxos e requisitos do sistema.

## 🛠️ Tecnologias Utilizadas
- **Mobile:** Kotlin, Android SDK, XML Layouts, RecyclerView, SQLite/DAO
- **Back-end:** Python, API REST, JSON
- **Build & Ferramentas:** Gradle (Kotlin DSL), Android Studio, Git

## 🚀 Como Executar

### 1. Servidor Back-end
```bash
cd ServidorVizinhanca
python main.py