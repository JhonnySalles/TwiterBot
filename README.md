# TwitterBot
> Programa para realizar postagens no twitter de uma lista de frases de origem do banco de dados MySQL.

<h4 align="center"> 
	🛰  Versão 0.0.1
</h4>

[![Build Status][travis-image]][travis-url]

Programa para realizar postagens no twitter de frases que estejam no banco de dados MySQL. Possui um time out randômico para a próxima postagem.


<p align="center">
 <a href="#Sobre">Sobre</a> •
 <a href="#Ambiente-de-Desenvolvimento">Ambiente de Desenvolvimento</a> • 
 <a href="#Histórico-de-Release">Histórico de Release</a> • 
 <a href="#Features">Features</a> • 
 <a href="#Meta">Meta</a> • 
 <a href="#Contribuindo">Contribuindo</a> • 
 <a href="#Licença">Licença</a>
</p>

## Sobre

Projetado para realizar postagens no twitter a cada alguns minutos, onde o tempo de delay será gerado randômicamente.

## Ambiente de Desenvolvimento

Necessário realizar préviamente a instalação do [java na versão 15](https://www.java.com/pt-BR/).
Necessário a utilização do banco de dados [MySQL](https://www.mysql.com/). 
Pode-se utilizar qualquer IDE de desenvolvimento de sua escolha, recomenda-se a versão 2020-12 do [eclipse](https://www.eclipse.org/downloads/).
Utilizado no projeto o mavem para controle e gerenciamento das dependências.

## Histórico de Release

* 0.0.1
    * Implementado a comunicação com o banco de dados MySQL.
    * Tela de configuração de conexão com o MySQL e dados referente a conta do twitter.
    * Implementado log de execução para facilitar erros e o processo que está sendo executado.
    * Timer para aguardar próxima postagem, sendo gerado por um valor randômico.

### Features

- [X] Comunicação com o MySQL
- [X] Configuração dos dados de loguin do twitter
- [X] Delay entre uma postagem e outra

## Meta

Distribuido sobre a licença GPL. Veja o arquivo ``COPYING`` para maiores informações.
[https://github.com/JhonnySalles/github-link](https://github.com/JhonnySalles/TwiterBot/blob/master/COPYING)

## Contribuindo

1. Fork (<https://github.com/JhonnySalles/TwiterBot/fork>)
2. Crie sua branch de recurso (`git checkout -b feature/fooBar`)
3. Faça o commit com suas alterações (`git commit -am 'Add some fooBar'`)
4. Realize o push de sua branch (`git push origin feature/fooBar`)
5. Crie um novo Pull Request

<!-- Markdown link & img dfn's -->

## Licença

[GPL-3.0 License](https://github.com/JhonnySalles/TwiterBot/blob/master/COPYING)
