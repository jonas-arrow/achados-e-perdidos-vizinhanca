from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

# Cria a API e já configura os nomes para o seu PDF da faculdade
app = FastAPI(
    title="API Achados e Perdidos - Vizinhança",
    description="Servidor backend para o aplicativo Android (Avaliação A4)",
    version="1.0.0"
)

# Define o formato exato do "Post" que o Android vai mandar pela internet
class PostItem(BaseModel):
    titulo: str
    descricao: str
    status: str
    fotoCaminho: str = "" # Opcional, caso não tenha foto

# Nosso "banco de dados" remoto na memória do servidor
banco_de_dados = []

# Rota para o Android ENVIAR um novo post para a nuvem
@app.post("/posts/")
def receber_post_do_android(post: PostItem):
    banco_de_dados.append(post)
    return {"mensagem": "Post recebido e salvo na nuvem com sucesso!"}

# Rota para o Android (ou o navegador) LER os posts que estão na nuvem
@app.get("/posts/", response_model=List[PostItem])
def listar_posts_da_nuvem():
    return banco_de_dados