set -e

BASE_URL="${1:-http://localhost:8080}"
BASE_URL="${BASE_URL%/}"   # remove barra final, se houver (evita "//api/...")

echo "==> Verificando se a API esta no ar em $BASE_URL ..."
curl -s -o /dev/null -w "Status: %{http_code}\n" "$BASE_URL/" || {
  echo "Nao foi possivel conectar em $BASE_URL. A aplicacao esta rodando? (./gradlew bootRun)"
  exit 1
}

echo
echo "==> Criando Autor (Machado de Assis)..."
AUTOR_RESPONSE=$(curl -s -X POST "$BASE_URL/api/autores" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Machado de Assis","nacionalidade":"Brasileira"}')
echo "$AUTOR_RESPONSE"
AUTOR_ID=$(echo "$AUTOR_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "Autor criado com id=$AUTOR_ID"

echo
echo "==> Criando Livros vinculados ao Autor $AUTOR_ID..."
curl -s -X POST "$BASE_URL/api/livros?autorId=$AUTOR_ID" \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Dom Casmurro","anoPublicacao":1899}'
echo
curl -s -X POST "$BASE_URL/api/livros?autorId=$AUTOR_ID" \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Memorias Postumas de Bras Cubas","anoPublicacao":1881}'
echo

echo
echo "==> Criando Usuario (Haru)..."
USUARIO_RESPONSE=$(curl -s -X POST "$BASE_URL/api/usuarios" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Haru","email":"haru@exemplo.com"}')
echo "$USUARIO_RESPONSE"
USUARIO_ID=$(echo "$USUARIO_RESPONSE" | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "Usuario criado com id=$USUARIO_ID"

echo
echo "==> Criando Perfil vinculado ao Usuario $USUARIO_ID..."
curl -s -X POST "$BASE_URL/api/perfis?usuarioId=$USUARIO_ID" \
  -H "Content-Type: application/json" \
  -d '{"bio":"Estudante de POO","avatarUrl":"foto.png"}'
echo

echo
echo "==> Pronto! Para conferir no navegador ou com curl:"
echo "   $BASE_URL/api/autores    -> mostra o Autor com a lista de Livros (1:N)"
echo "   $BASE_URL/api/usuarios   -> mostra o Usuario com o Perfil (1:1)"
echo
echo "==> Bonus (mostra a regra de negocio do 1:1): tentar criar um SEGUNDO"
echo "    perfil para o mesmo usuario deve falhar:"
echo "   curl -i -X POST \"$BASE_URL/api/perfis?usuarioId=$USUARIO_ID\" -H \"Content-Type: application/json\" -d '{\"bio\":\"outro\"}'"
