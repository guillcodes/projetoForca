# 🎮 Jogo da Forca - App Android

Aplicativo Android nativo do clássico **Jogo da Forca**, desenvolvido com **Kotlin** e **Jetpack Compose**.  
O projeto utiliza arquitetura moderna (**MVVM**), persistência local com **Room** e **Firebase Authentication** para login.

---

## 🚀 Funcionalidades

### 🔐 Autenticação de Usuários
- Login e Cadastro utilizando **Firebase Authentication**.

### 🎲 Jogo da Forca Clássico
- Mecânica tradicional onde o usuário tenta adivinhar a palavra.
- Limite de **6 erros**.

### 🗂️ Banco de Palavras Local
- Palavras e categorias armazenadas no **Room Database**.
- Sorteio automático de palavras para cada partida.

### 🏆 Sistema de Pontuação
- Pontuação final calculada como:  
  **100 - (erros × 10)**

### 📊 Ranking Global
- Lista das melhores pontuações armazenadas no **Room**.
- Tela dedicada para exibir a classificação.

### 🛠️ Painel do Administrador
- Acesso via **loginadmin@gmail.com**.
- Permite:
  - Forçar uma palavra específica para o jogo.
  - Acessar uma tela com **CRUD** do ranking.

---

## 🛠️ Arquitetura e Tecnologias

- **Linguagem:** Kotlin  
- **UI:** Jetpack Compose  
- **Arquitetura:** MVVM + UiState  
- **Navegação:** Navigation Compose  
- **Persistência:** Room Database  
- **Assíncrono:** Kotlin Coroutines + Flow  
- **Autenticação:** Firebase Authentication  
- **Injeção de Dependência:** Manual via `ViewModelProvider.Factory` (ex.: `JogoViewModelFactory`)

---

## 🔧 Como Executar

📸 Screenshot
<p align="center"> <img width="740" height="426" src="https://github.com/user-attachments/assets/45dde12a-8ac6-4887-862d-5d555ac45474" alt="App Screenshot"> </p>
