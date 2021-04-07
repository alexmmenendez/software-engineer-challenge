![PicPay](https://user-images.githubusercontent.com/1765696/26998603-711fcf30-4d5c-11e7-9281-0d9eb20337ad.png)

# Teste Backend

Na minha solução decidi adicionar um campo de password para autenticação e um campo que indica-se a prioridade do usuário na busca, de acordo com as listas de relevância fornecidas. Isso é feito ao criar um mongo no container

Utilizei também um cache dos users para a busca no momento de autentica-lo na aplicação.

Comandos para subir o codigo via terminal:
- git clone https://github.com/alexmmenendez/software-engineer-challenge.git
- cd software-engineer-challenge
- git checkout origin feature/picpay-challenge 

Neste momento é necessário baixar o arquivo .csv fornecido e coloca-lo no pacote /mongoseed

- docker-compose build
- docker-compose up

Ao subir os containers, será importado os dados do .csv para um database no mongo, alterado o nivel de relevância dos usuários, de acordo com as listas fornecidas e a criação dos indexes de busca baseado no name e username.
