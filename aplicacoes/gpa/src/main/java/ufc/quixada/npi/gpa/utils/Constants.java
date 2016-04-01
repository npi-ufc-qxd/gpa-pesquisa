package ufc.quixada.npi.gpa.utils;

public class Constants {

	public static final String USUARIO_LOGADO = "usuario";

	/** Páginas */

	public static final String PAGINA_CADASTRAR_PROJETO = "projeto/cadastrar";

	public static final String PAGINA_VINCULAR_PARTICIPANTES_PROJETO = "projeto/participacoes";

	public static final String PAGINA_DETALHES_PROJETO = "projeto/detalhes";

	public static final String PAGINA_EDITAR_PROJETO = "projeto/editar";

	public static final String PAGINA_LISTAR_PROJETO = "projeto/listar";

	public static final String PAGINA_SUBMETER_PROJETO = "projeto/submeter";

	public static final String PAGINA_INICIAL_DIRECAO = "direcao/index";

	public static final String PAGINA_DIRECAO_BUSCAR_PESSOA = "direcao/buscar-participante";

	public static final String PAGINA_EMITIR_PARECER = "projeto/emitir-parecer";

	public static final String PAGINA_ATRIBUIR_PARECERISTA = "direcao/atribuir-parecerista";

	public static final String PAGINA_AVALIAR_PROJETO = "direcao/avaliar-projeto";

	public static final String PAGINA_DETALHES_PARTICIPANTE = "direcao/participante";

	public static final String PAGINA_RELATORIOS = "direcao/relatorios";

	public static final String PAGINA_ADMINISTRACAO = "administracao/index";

	public static final String PAGINA_ADMINISTRACAO_VINCULAR_PAPEL = "administracao/vincularPapel";

	/** Redirecionamentos */

	public static final String REDIRECT_PAGINA_LISTAR_PROJETO = "redirect:/projeto";

	public static final String REDIRECT_PAGINA_RELATORIOS = "redirect:/direcao/relatorios";

	public static final String REDIRECT_PAGINA_INICIAL_DIRECAO = "redirect:/direcao";

	public static final String REDIRECT_PAGINA_INICIAL_ADMINISTRACAO = "redirect:/administracao";

	public static final String REDIRECT_PAGINA_BUSCAR_PARTICIPANTE = "redirect:/direcao/buscar";

	/** Mensagens */
	public static final String MENSAGEM_PERMISSAO_NEGADA = "Permissão negada";

	public static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo obrigatório";

	public static final String MENSAGEM_PROJETO_INEXISTENTE = "Projeto inexistente";

	public static final String MENSAGEM_SEM_PARTICIPANTES = "Projeto sem participações cadastradas";

	public static final String MENSAGEM_COORDENADOR_NAO_PARTICIPANTE = "Coordenador do projeto deve ter pelo menos uma participação cadastrada";

	public static final String MENSAGEM_PARTICIPACAO_INEXISTENTE = "Participação inexistente ou não relacionado com projeto";

	public static final String MENSAGEM_PROJETO_ATUALIZADO = "Projeto atualizado com sucesso";

	public static final String MENSAGEM_PROJETO_CADASTRADO = "Projeto cadastrado com sucesso";

	public static final String MENSAGEM_PARECERISTA_ATRIBUIDO = "Parecerista atribuído com sucesso";

	public static final String MENSAGEM_PROJETO_SUBMETIDO = "Projeto submetido com sucesso";

	public static final String MENSAGEM_PARECER_EMITIDO = "Parecer emitido com sucesso";

	public static final String MENSAGEM_PROJETO_REMOVIDO = "Projeto removido com sucesso";

	public static final String MENSAGEM_PARTICIPACAO_REMOVIDA = "Participação removida com sucesso";

	public static final String MENSAGEM_PROJETO_AVALIADO = "Projeto avaliado com sucesso";

	public static final String MENSAGEM_DATA_TERMINO_FUTURA = "A data de término não pode ser anterior à data atual";

	public static final String MENSAGEM_DATA_INICIO_TERMINO = "A data de início deve ser anterior à data de término";

	public static final String MENSAGEM_CAMPO_OBRIGATORIO_SUBMISSAO = "É necessário preencher todas as informações do projeto para submetê-lo";

	public static final String MENSAGEM_CAMPO_OBRIGATORIO_PARTICIPACAO = "É necessário preencher todas as informações da participação";

	public static final String MENSAGEM_DATA_INVALIDA = "Data inválida";

	public static final String MENSAGEM_ERRO_UPLOAD = "Ocorreu um erro no upload de arquivos";

	public static final String MENSAGEM_DOCUMENTO_INEXISTENTE = "Documento inexistente";

	public static final String MENSAGEM_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

	public static final String MENSAGEM_ERRO_PERSISTIR_USUARIO = "Ocorreu um erro ao persistir o usuário.";

	public static final String MENSAGEM_ERRO_VINCULAR_PAPEIS = "Ocorreu um erro ao vincular papéis ao usuário.";

	public static final String MENSAGEM_ERRO_ATUALIZAR_PAPEIS = "Ocorreu um erro ao atualizar papéis";

	public static final String MENSAGEM_SUCESSO_VINCULAR_PAPEIS = "Papéis vinculados com sucesso!";

	public static final String MENSAGEM_PARTICIPACAO_MESMO_PERIODO = "Não pode haver mais de uma partipação no mesmo período";

	/* Papeis */
	public static final String PAPEL_DIRECAO = "DIRECAO";

	public static final String PAPEL_ADMINISTRACAO = "ADMINISTRACAO";

	public static final String PAPEL_COORDENACAO = "COORDENADOR";

	/* Arquivos */
	public static final String PASTA_DOCUMENTOS_GPA = "/home/erick.silva/Área de Trabalho/gpa-pesquisa-uploads";

}
