<img width="740" height="426" alt="image" src="https://github.com/user-attachments/assets/45dde12a-8ac6-4887-862d-5d555ac45474" />
Jogo da Forca - App Android
Este é um aplicativo Android nativo do clássico Jogo da Forca, desenvolvido com Kotlin e Jetpack Compose. O projeto implementa uma arquitetura moderna (MVVM), persistência de dados local (Room) e autenticação de usuários (Firebase).

🚀 Funcionalidades
Autenticação de Usuários: Sistema completo de Login e Cadastro utilizando Firebase Authentication.

Jogo da Forca Clássico: Lógica de jogo principal onde o usuário tenta adivinhar a palavra com um limite de 6 erros.

Banco de Palavras Local: As palavras e categorias são armazenadas e sorteadas a partir de um banco de dados Room.

Sistema de Pontuação: A pontuação é calculada ao final de uma vitória (100 - (erros * 10)).

Ranking Global: Uma tela de "Classificação" exibe as melhores pontuações, persistidas localmente no banco Room.

Painel de Administrador:

Acesso especial através do login loginadmin@gmail.com.

Permite ao admin forçar uma palavra específica para o jogo.

Inclui uma tela de CRUD (Criar, Ler, Atualizar, Deletar) para gerenciar manualmente os registros do ranking.

🛠️ Arquitetura e Tecnologias
Este projeto utiliza uma arquitetura MVVM (Model-View-ViewModel), separando a lógica de negócio da interface do usuário.

Linguagem: 100% Kotlin

Interface (UI): Jetpack Compose

Arquitetura: MVVM, com UiState para gerenciar o estado da tela.

Navegação: Jetpack Navigation Compose

Persistência Local: Room Database para armazenar palavras e o ranking.

Programação Assíncrona: Kotlin Coroutines e Flow (para reatividade do Room).

Autenticação: Firebase Authentication.

Injeção de Dependência: Manual, através de ViewModelProvider.Factory (ex: JogoViewModelFactory).

🔧 Como Executar
Clone o repositório:

Bash

git clone https://[URL-DO-SEU-REPOSITORIO]
Abra no Android Studio:

Abra o Android Studio.

Selecione File > Open e navegue até a pasta do projeto clonado.

Configure o Firebase:

O projeto já contém um arquivo google-services.json.

Para usar sua própria instância do Firebase, acesse o Console do Firebase.

Crie um novo projeto Android, registre o app com o package name com.example.projetoforca.

Ative o Firebase Authentication (com provedor de E-mail/Senha).

Faça o download do seu próprio google-services.json e substitua o arquivo existente na pasta app/.

Execute o Aplicativo:

Pressione Shift + F10 (ou clique em "Run") no Android Studio para compilar e executar o app em um emulador ou dispositivo físico.
