#!/bin/bash

echo '******************************************************'
echo '        Teste de API do sistema Biblioteca'
echo ' '
echo '  Serão usando comandos curl'
echo '  Métodos: GET,DELETE,POST,PUT'
echo '  a) Serão testes com resultados ESPERADOS'
echo '  b) Testes que devem gerar ERROS'
echo '******************************************************'

#DEBUG=on
DEBUG=off

whereis curl
if [ $? -eq 0 ]; then
    CURL=$(whereis curl | cut -d' ' -f 2)
    $CURL --version
else
    echo "Comando curl não encontrado. Saindo ...."
    exit -1
fi

# curl
# -i mostra cabeçalhos
# -v modo verboso
# -s modo silencioso (?)
# -X informa o método HTTP (padrão GET)
# -H informa dados de cabeçalho
# -d especifica o payload (necessário informar cabeçalho, senão é inferido 
#       Content-Type: application/x-www-form-urlencoded que pode gerar erro.

# Pode redirecionar saída para jq (Windows) ou json_pp (Linux) fins de gerar
#   saída formatada JSON 

if [ $DEBUG = 'on' ]; then
    CMD="$CURL -v"
else
    CMD="$CURL"
fi

# Para salvar em arquivo, usar opção -o file.json

echo -e "\n\n\n  ====================================================================="
echo -e "                 Testes com resultados ESPERADOS (sem ERRO)      \n\n"


echo -e '\n\n ***************    GET Livro  ****************************'
echo -e "\n    $CURL -i -s -X GET 'http://localhost:8082/livros'\n"
$CMD -i -s -X GET 'http://localhost:8082/livros'

echo -e '\n\n ***************    GET Membro  ****************************'
echo -e "\n    $CURL -i -s -X GET 'http://localhost:8082/membros' \n"
$CMD -i -s -X GET 'http://localhost:8082/membros'

echo -e '\n\n ***************    GET Livro (por id)  ****************************'
echo -e "\n    $CURL -i -s -X GET 'http://localhost:8082/livros/1' \n"
$CMD -i -s -X GET 'http://localhost:8082/livros/1'

echo -e '\n\n ***************    GET Membro (por id)   ****************************'
echo -e "\n    $CURL -i -s -X GET 'http://localhost:8082/membros/1' \n"
$CMD -i -s -X GET 'http://localhost:8082/membros/1'

echo -e '\n\n ***************    POST Livro  ****************************'
echo -e "\n    $CURL -i -s --header 'Content-Type:application/json' -X POST -d '{"titulo":"Java 8","isbn":"9788536509266","autorNome":"José","autorSobrenome":"Manzano","anoPublicacao":2014,"editoraNome":"Érica","emprestado":false}' http://localhost:8082/livros \n"
$CMD -i --header 'Content-Type:application/json' -X POST -d '{"titulo":"Java 8","isbn":"9788536509266","autorNome":"José","autorSobrenome":"Manzano","anoPublicacao":2014,"editoraNome":"Érica","emprestado":false}' http://localhost:8082/livros


echo -e '\n\n ***************    POST Membro  ****************************'
echo -e "\n    $CURL -i -s -H 'Content-Type:application/json;charset=utf-8' -X POST -d '{"nome":"Fulano","sobrenome":"de Tal","endereco":"São Paulo","contato":"fulano@gmail.com"}' http://localhost:8082/membros/membro \n"
$CMD -i -s -H 'Content-Type:application/json;charset=utf-8' -X POST -d '{"nome":"Fulano","sobrenome":"de Tal","endereco":"São Paulo","contato":"fulano@gmail.com"}' http://localhost:8082/membros


echo -e '\n\n ***************    DELETE Livro (by ID = 2)  *************************'

echo -e "\n    $CURL -i -s -X DELETE 'http://localhost:8082/livros/2' \n"
$CMD -i -s -X DELETE 'http://localhost:8082/livros/2'

echo -e '\n\n ***************    DELETE Membro (by ID = 2)  ***********************'

echo -e "\n    $CURL -i -s -X DELETE 'http://localhost:8082/membros/2' \n"
$CMD -i -s -X DELETE 'http://localhost:8082/membros/2'


echo -e '\n\n ************      PUT Livro (ano publicação:2024)     *************************'
echo -e "\n    $CURL -i -s --header 'Content-Type:application/json' -X PUT -d '{"id":1,"titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2024,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros \n"
$CMD -i --header 'Content-Type:application/json' -X PUT -d '{"id":1,"titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2024,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros


echo -e '\n\n ************      PUT Membro (contato:para@gmail.com)     *************************'
echo -e "\n    $CURL -i -s -H 'Content-Type:application/json;charset=utf-8' -X PUT -d '{"id":1,"nome":"Amazonas","sobrenome":"no Pará","endereco":"Estado do Pará","contato":"para@gmail.com","multaAcumulada":0}' http://localhost:8082/membros \n"
$CMD -i -s -H 'Content-Type:application/json;charset=utf-8' -X PUT -d '{"id":1,"nome":"Amazonas","sobrenome":"no Pará","endereco":"Estado do Pará","contato":"para@gmail.com","multaAcumulada":0}' http://localhost:8082/membros


echo -e '\n\n ************  PUT Livro (sem ID -> CRIA ENTIDADE)  -> HTTP 201    *************************'
echo -e "\n    $CURL -i -s --header 'Content-Type:application/json' -X PUT -d '{"titulo":"Java: como NÃO programar","isbn":"000-999999","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2024,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros \n"
$CMD -i --header 'Content-Type:application/json' -X PUT -d '{"titulo":"Java: como NÃO programar","isbn":"000-999999","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2024,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros


echo -e '\n\n ************  PUT Membro (sem ID -> CRIA ENTIDADE)  -> HTTP 201    *************************'
echo -e "\n    $CURL -i -s -H 'Content-Type:application/json;charset=utf-8' -X PUT -d '{"nome":"São Francisco","sobrenome":"em Minas Gerais","endereco":"Estado de Minas Gerais","contato":"+55 31 22233333","multaAcumulada":0}' http://localhost:8082/membros \n"
$CMD -i -s -H 'Content-Type:application/json;charset=utf-8' -X PUT -d '{"nome":"São Francisco","sobrenome":"em Minas Gerais","endereco":"Estado de Minas Gerais","contato":"+55 31 22233333","multaAcumulada":0}' http://localhost:8082/membros



echo -e "\n\n\n  ====================================================================="
echo -e "                 Testes que devem gerar ERRO      \n\n"

echo -e '\n\n *********   GET  Livro (id não existe) -> HTTP 404   *****************'
echo -e "\n    $CURL -i -s -X GET 'http://localhost:8082/livros/10' \n"
$CMD -i -s -X GET 'http://localhost:8082/livros/10'

echo -e '\n\n *********   GET  Membro (id não existe) -> HTTP 404   *****************'
echo -e "\n    $CURL -i -s -X GET 'http://localhost:8082/membros/10' \n"
$CMD -i -s -X GET 'http://localhost:8082/membros/10'

echo -e '\n\n *********    POST Livro  (ISBN já existe) -> HTTP 409   ********************'
echo -e "\n    $CURL -i -s --header 'Content-Type:application/json' -X POST -d '{"titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2016,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros \n"
$CMD -i --header 'Content-Type:application/json' -X POST -d '{"titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2016,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros

echo -e '\n\n ***    POST Membro  -> HTTP 201 (sempre será incluído, como novo Id)   ********'
echo -e "\n    $CURL -i -s -H 'Content-Type:application/json;charset=utf-8' -X POST -d '{"nome":"Amazonas","sobrenome":"no Pará","endereco":"Estado do Pará","contato":"amazonas@para.com"}' http://localhost:8082/membros \n"
$CMD -i -s -H 'Content-Type:application/json;charset=utf-8' -X POST -d '{"nome":"Amazonas","sobrenome":"no Pará","endereco":"Estado do Pará","contato":"amazonas@para.com"}' http://localhost:8082/membros



echo -e '\n\n **************    DELETE  (Id não existe) -> 404    **************'

echo -e "\n    $CURL -i -s -X DELETE 'http://localhost:8082/livros/20' \n"
$CMD -i -s -X DELETE 'http://localhost:8082/livros/20'

echo -e '\n\n **************   DELETE  (Id não existe) -> 404  **************'

echo -e "\n    $CURL -i -s -X DELETE 'http://localhost:8082/membros/20' \n"
$CMD -i -s -X DELETE 'http://localhost:8082/membros/20'



echo -e '\n\n ************      PUT Livro (inexistente) -> 400     *************************'
echo -e "\n    $CURL -i -s --header 'Content-Type:application/json' -X PUT -d '{"id":300,"titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2024,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros \n"
$CMD -i --header 'Content-Type:application/json' -X PUT -d '{"id":300,"titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2024,"editoraNome":"Pearson Education","emprestado":false}' http://localhost:8082/livros


echo -e '\n\n ************      PUT Membro (inexistente) -> 400    *************************'
echo -e "\n    $CURL -i -s -H 'Content-Type:application/json;charset=utf-8' -X PUT -d '{"id":300,"nome":"Amazonas","sobrenome":"no Pará","endereco":"Estado do Pará","contato":"para@gmail.com","multaAcumulada":0}' http://localhost:8082/membros \n"
$CMD -i -s -H 'Content-Type:application/json;charset=utf-8' -X PUT -d '{"id":300,"nome":"Amazonas","sobrenome":"no Pará","endereco":"Estado do Pará","contato":"para@gmail.com","multaAcumulada":0}' http://localhost:8082/membros


echo -e '\n\n ************      PATCH Livro (altera titulo) -> 405     *************************'
echo -e "\n    $CURL -i -s --header 'Content-Type:application/json' -X PATCH -d '{"id":1,"titulo":"Rust: como programar"}' http://localhost:8082/livros \n"
$CMD -i --header 'Content-Type:application/json' -X PATCH -d '{"id":300,"titulo":"Rust: como programar"}' http://localhost:8082/livros


echo -e '\n\n ************      PATCH Membro (altera nome) -> 405    *************************'
echo -e "\n    $CURL -i -s -H 'Content-Type:application/json;charset=utf-8' -X PATCH -d '{"id":1,"nome":"Parana"}' http://localhost:8082/membros \n"
$CMD -i -s -H 'Content-Type:application/json;charset=utf-8' -X PATCH -d '{"id":1,"nome":"Parana"}' http://localhost:8082/membros



# Para PUT com erro LIVRO: http://localhost:8082/livros
#{
#    "id":300,
#    "titulo":"Java: como programar",
#    "isbn":"978-8543004792",
#    "autorNome":"Harvey",
#    "autorSobrenome":"Deitel",
#    "anoPublicacao":2024,
#    "editoraNome":"Pearson Education",
#    "emprestado":false
#}

# Para PUT com erro MEMBRO: http://localhost:8082/membros
#{
#    "id":300,
#    "nome":"Amazonas",
#    "sobrenome":"no Pará",
#    "endereco":"Estado do Pará",
#    "contato":"para@gmail.com",
#    "multaAcumulada":0
#}



# "titulo":"Java: como programar","isbn":"978-8543004792","autorNome":"Harvey","autorSobrenome":"Deitel","anoPublicacao":2016,"editoraNome":"Pearson Education","emprestado":false"



# "titulo":"Java 8","isbn":"9788536509266","autorNome":"José","autorSobrenome":"Manzano","anoPublicacao":2014,"editoraNome":"Érica","emprestado":false"

#"headers": { "Accept": "application/json, text/plain, */*", "Content-Type": "application/json;charset=utf-8" },
#{
#    "titulo":"Java 8",
#    "isbn":"9788536509266",
#    "autorNome":"José",
#    "autorSobrenome":"Manzano",
#    "anoPublicacao":2014,
#    "editoraNome":"Érica",
#    "emprestado":false
#}

# curl -v -i -s -X POST -d '{"titulo":"Java 8","isbn":"9788536509266","autorNome":"José","autorSobrenome":"Manzano","anoPublicacao":2014,"editoraNome":"Érica","emprestado":false"}' http://localhost:8082/livros/ --header "Content-Type:application/json;charset=utf-8"

# curl -v -i -s -X POST -d '{"titulo":"Java 8","isbn":"44444","autorNome":"José","autorSobrenome":"Manzano","anoPublicacao":2014,"editoraNome":"Érica","emprestado":false"}' http://localhost:8082/livros/ --header "Content-Type:application/json;charset=utf-8"

#{
#    "nome":"Fulano",
#    "sobrenome":"de Tal",
#    "endereco":"São Paulo",
#    "contato":"fulano@gmail.com"
#}

#  "dataNascimento": "2010-10-10"


echo -e "\n\n"
#echo -e '\n\n ******************************************************'
