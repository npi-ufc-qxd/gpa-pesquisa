package ufc.quixada.npi.gpa.utils;

public class Constants {

	public static final String USUARIO_LOGADO = "usuario";

	/** Páginas */

	public static final String PAGINA_CADASTRAR_PROJETO = "projeto/cadastrar";

	public static final String PAGINA_VINCULAR_PARTICIPANTES_PROJETO = "projeto/participacoes";

	public static final String PAGINA_DETALHES_PROJETO = "projeto/detalhes";

	public static final String PAGINA_EDITAR_PROJETO = "projeto/editar";
	
	public static final String PAGINA_UPLOAD_DOCUMENTOS_PROJETO = "projeto/upload-documentos";
	
	public static final String PAGINA_LISTAR_PROJETO = "projeto/listar";

	public static final String PAGINA_SUBMETER_PROJETO = "projeto/submeter";

	public static final String PAGINA_INICIAL_DIRECAO = "direcao/index";

	public static final String PAGINA_DIRECAO_BUSCAR_PESSOA = "direcao/buscar-participante";

	public static final String PAGINA_EMITIR_PARECER = "projeto/emitir-parecer-tecnico";
	
	public static final String PAGINA_EMITIR_PARECER_RELATOR ="projeto/emitir-parecer-relator";

	public static final String PAGINA_ATRIBUIR_PARECERISTA = "direcao/atribuir-parecerista";

	public static final String PAGINA_HOMOLOGAR_PROJETO = "direcao/homologar-projeto";

	public static final String PAGINA_DETALHES_PARTICIPANTE = "direcao/participante";

	public static final String PAGINA_RELATORIOS = "direcao/relatorios";

	public static final String PAGINA_ADMINISTRACAO = "administracao/index";

	public static final String PAGINA_ADMINISTRACAO_VINCULAR_PAPEL = "administracao/vincularPapel";
	
	public static final String PAGINA_ADMINISTRACAO_FONTES_DE_FINANCIAMENTO = "administracao/fontes-financiamento";

	public static final String PAGINA_ATRIBUIR_RELATOR = "direcao/atribuir-relator";
	
	public static final String PAGINA_LOGIN = "login";
	
	public static final String PAGINA_403 = "403";
	
	public static final String PAGINA_404 = "404";
	
	public static final String PAGINA_500 = "500";

	
	/** Redirecionamentos */

	public static final String REDIRECT_PAGINA_LISTAR_PROJETO = "redirect:/projeto";

	public static final String REDIRECT_PAGINA_RELATORIOS = "redirect:/direcao/relatorios";

	public static final String REDIRECT_PAGINA_INICIAL_DIRECAO = "redirect:/direcao";

	public static final String REDIRECT_PAGINA_INICIAL_ADMINISTRACAO = "redirect:/administracao";

	public static final String REDIRECT_PAGINA_BUSCAR_PARTICIPANTE = "redirect:/direcao/buscar";
	
	public static final String REDIRECT_PAGINA_ADMINISTRACAO_VINCULAR_PAPEL = "redirect:/administracao/pessoa/vincular/";
	
	public static final String REDIRECT_PAGINA_ADMINISTRACAO_FONTES_DE_FINANCIAMENTO = "redirect:/administracao/fonte-financiamento/mostrar";

	public static final String REDIRECT_PAGINA_VINCULAR_PARTICIPANTES_PROJETO = "redirect:/projeto/participacoes/";
	
	public static final String REDIRECT_PAGINA_UPLOAD_DOCUMENTOS_PROJETO = "redirect:/projeto/uploadDocumento/";
	
	/** Mensagens */
	public static final String MENSAGEM_PERMISSAO_NEGADA = "Permissão negada";

	public static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo obrigatório";

	public static final String MENSAGEM_PROJETO_INEXISTENTE = "Projeto inexistente";

	public static final String MENSAGEM_SEM_PARTICIPANTES = "Projeto sem participações cadastradas";

	public static final String MENSAGEM_COORDENADOR_NAO_PARTICIPANTE = "Coordenador do projeto deve ter pelo menos uma participação cadastrada";

	public static final String MENSAGEM_PARTICIPACAO_INEXISTENTE = "Participação inexistente ou não relacionado com projeto";

	public static final String MENSAGEM_PROJETO_ATUALIZADO = "Projeto atualizado com sucesso";
	
	public static final String MENSAGEM_DOCUMENTO_UPLOAD = "Documentos adicionados com sucesso";

	public static final String MENSAGEM_PROJETO_CADASTRADO = "Projeto cadastrado com sucesso";

	public static final String MENSAGEM_PARECERISTA_ATRIBUIDO = "Parecerista atribuído com sucesso";

	public static final String MENSAGEM_PROJETO_SUBMETIDO = "Projeto submetido com sucesso";
	
	public static final String MENSAGEM_PROJETO_RESOLUCAO_PENDENCIAS = "Resolução de pendências submetido com sucesso";
	
	public static final String MENSAGEM_PROJETO_RESOLUCAO_RESTRICOES = "Resolução de restrições submetido com sucesso";

	public static final String MENSAGEM_PARECER_EMITIDO = "Parecer emitido com sucesso";

	public static final String MENSAGEM_PROJETO_REMOVIDO = "Projeto removido com sucesso";

	public static final String MENSAGEM_PARTICIPACAO_REMOVIDA = "Participação removida com sucesso";

	public static final String MENSAGEM_PROJETO_HOMOLOGADO = "Projeto homologado com sucesso";

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

	public static final String MENSAGEM_PARECERISTA_ALTERADO = "Parecerista alterado com sucesso";

	public static final String MENSAGEM_FONTE_DE_FINANCIAMENTO_CADASTRADA = "Fonte de financiamento cadastrada com sucesso";
	
	public static final String MENSAGEM_FONTE_DE_FINANCIAMENTO_INEXISTENTE = "Fonte de financiamento inexistente";
	
	public static final String MENSAGEM_FONTE_DE_FINANCIAMENTO_REFERENCIADA = "Não é possível excluir! Possívelmente esta fonte de financiamento é usada em algum projeto.";
	
	public static final String MENSAGEM_RELATOR_ATRIBUIDO = "Relator atribuído com sucesso";

	public static final String MENSAGEM_RELATOR_ALTERADO = "Relator alterado com sucesso";
	
	public static final String MENSAGEM_USUARIO_SENHA_INVALIDOS = "Usuário e/ou senha inválidos!";
	
	public static final String MENSAGEM_PAGINA_NAO_ENCONTRADA = "Oops, página não encontrada.";
	
	public static final String MENSAGEM_ERRO_TECNICO = "Oops, o site teve um erro técnico.";
	
	public static final String MENSAGEM_OCORREU_UM_ERRO = "Ocorreu um erro, contate o administrador do sistema";

	public static final String MENSAGEM_RESULTADO_OK = "ok";
	
	public static final String MENSAGEM_RESULTADO_ERRO = "erro";
	
	/* Papeis */
	public static final String PAPEL_DIRECAO = "DIRECAO";

	public static final String PAPEL_ADMINISTRACAO = "ADMINISTRACAO";

	public static final String PAPEL_COORDENACAO = "COORDENADOR";

	/* Permissões */
	public static final String PERMISSAO_DIRECAO = "direcao";
	
	public static final String PERMISSAO_COORDENADOR = "coordenador";
	
	public static final String PERMISSAO_PARECERISTA = "parecerista";
	
	public static final String PERMISSAO_RELATOR = "relator";
	
	public static final String PERMISSAO_PARTICIPANTE = "participante";
	
	/* Arquivos */
	public static final String PASTA_DOCUMENTOS_GPA = "/gpa-pesquisa-uploads";
	
	/* Action */
	public static final String CADASTRAR = "cadastrar";
	
	public static final String ATRIBUIR = "atribuir";
	
	public static final String ALTERAR = "alterar";
	
	public static final String RESULTADO = "resultado";
	
	public static final String EDITAR = "editar";

	/* Attributes Names*/
	public static final String ANO = "ano";
	
	public static final String INFO = "info";
	
	public static final String ERRO = "erro";
	
	public static final String ACTION = "action";
	
	public static final String ALERT = "alert";
	
	public static final String BUSCA = "busca";
	
	public static final String RESULT = "result";
	
	public static final String MENSAGEM = "mensagem";
	
	public static final String PERMISSAO = "permissao";
	
	public static final String VALIDACAO = "validacao";
	
	public static final String TIPOS_DE_PARTICIPACAO = "tiposDeParticipacao";
	
	public static final String FONTE_FINANCIAMENTO = "fonteFinanciamento";
	
	public static final String FONTES_FINANCIAMENTO = "fontesFinanciamento";
	
	public static final String PAPEIS = "papeis";
	
	public static final String USUARIO = "usuario";
	
	public static final String USUARIOS = "usuarios";
	
	public static final String PESSOA = "pessoa";
	
	public static final String PESSOAS = "pessoas";
	
	public static final String PESSOAS_EXTERNAS = "pessoasExternas";
	
	public static final String PROJETO = "projeto";
	
	public static final String OLD_PROJETO = "oldProjeto";
	
	public static final String PROJETOS = "projetos";
	
	public static final String PROJETOS_NAO_HOMOLOGADOS = "projetosNaoHomologados";
	
	public static final String PARTICIPACOES_EM_PROJETOS = "participacoesEmProjetos";
	
	public static final String PROJETOS_AGUARDANDO_PARECER = "projetosAguardandoParecer";
	
	public static final String PROJETOS_PARECER_EMITIDO = "projetosParecerEmitido";
	
	public static final String PROJETOS_AGUARDANDO_AVALIACAO = "projetosAguardandoAvaliacao";
	
	public static final String PROJETOS_AVALIADOS = "projetosAvaliados";
	
	public static final String PROJETOS_HOMOLOGADOS = "projetosHomologados";
	
	public static final String PROJETOS_EM_TRAMITACAO = "projetosEmTramitacao";
	
	public static final String PARTICIPACAO = "participacao";
	
	public static final String PARTICIPACAO_EXTERNA = "participacaoExterna";
	
	public static final String PARTICIPANTES = "participantes";
	
	public static final String PARECER = "parecer";
	
	public static final String POSICIONAMENTO = "posicionamento";
	
	public static final String COMENTARIO = "comentario";
	
	public static final String AUTOR = "autor";
	
	public static final String PROJETOS_COORDENA = "projetosCoordena";
	
	public static final String PROJETOS_COORDENOU = "projetosCoordenou";
	
	public static final String PROJETOS_PARTICIPA = "projetosParticipa";
	
	public static final String PROJETOS_PARTICIPOU = "projetosParticipou";
	
	public static final String TIPO_RELATORIO = "tipoRelatorio";
	
	public static final String RELATORIO = "relatorio";
	
	public static final String DATA_PESQUISA = "data_pesquisa";
	
	public static final String INICIO_INTERVALO_INICIO = "inicio_intervalo_inicio";
	
	public static final String TERMINO_INTERVALO_INICIO = "termino_intervalo_inicio";
	
	public static final String INICIO_INTERVALO_TERMINO = "inicio_intervalo_termino";
	
	public static final String TERMINO_INTERVALO_TERMINO = "termino_intervalo_termino";
	
	public static final String DATA_INICIO_INTERVALO = "data_inicio_intervalo";
	
	public static final String DATA_TERMINO_INTERVALO = "data_termino_intervalo";
	
	/*Tipos de relatório*/
	
	public static final String APROVADOS = "aprovados";
	
	public static final String REPROVADOS = "reprovados";
	
	public static final String POR_PESSOA = "por-pessoa";
};
