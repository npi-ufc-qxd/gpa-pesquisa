--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.6
-- Dumped by pg_dump version 9.3.6
-- Started on 2015-11-16 10:26:14 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET search_path = public, pg_catalog;

--
-- TOC entry 2049 (class 0 OID 59675)
-- Dependencies: 171
-- Data for Name: comentario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2078 (class 0 OID 0)
-- Dependencies: 170
-- Name: comentario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('comentario_id_seq', 1, false);


--
-- TOC entry 2051 (class 0 OID 59686)
-- Dependencies: 173
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2079 (class 0 OID 0)
-- Dependencies: 172
-- Name: documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('documento_id_seq', 2, true);


--
-- TOC entry 2053 (class 0 OID 59697)
-- Dependencies: 175
-- Data for Name: papel; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO papel (id, nome) VALUES (1, 'DIRECAO');
INSERT INTO papel (id, nome) VALUES (2, 'COORDENADOR');


--
-- TOC entry 2080 (class 0 OID 0)
-- Dependencies: 174
-- Name: papel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('papel_id_seq', 2, true);

--
-- TOC entry 2059 (class 0 OID 59724)
-- Dependencies: 181
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pessoa (id, cpf) VALUES (1, '00000000000');
INSERT INTO pessoa (id, cpf) VALUES (2, '00092349005');
INSERT INTO pessoa (id, cpf) VALUES (3, '00092349005');
INSERT INTO pessoa (id, cpf) VALUES (4, '00104294337');
INSERT INTO pessoa (id, cpf) VALUES (5, '00255406371');
INSERT INTO pessoa (id, cpf) VALUES (6, '00368794369');
INSERT INTO pessoa (id, cpf) VALUES (7, '00427166322');
INSERT INTO pessoa (id, cpf) VALUES (8, '00772302308');
INSERT INTO pessoa (id, cpf) VALUES (9, '00884821382');
INSERT INTO pessoa (id, cpf) VALUES (10, '01398758329');
INSERT INTO pessoa (id, cpf) VALUES (11, '01454644311');
INSERT INTO pessoa (id, cpf) VALUES (12, '01746255374');
INSERT INTO pessoa (id, cpf) VALUES (14, '01827123389');
INSERT INTO pessoa (id, cpf) VALUES (13, '02039091351');
INSERT INTO pessoa (id, cpf) VALUES (15, '27240450848');
INSERT INTO pessoa (id, cpf) VALUES (16, '02054721336');
INSERT INTO pessoa (id, cpf) VALUES (17, '02059472393');
INSERT INTO pessoa (id, cpf) VALUES (18, '02664908324');
INSERT INTO pessoa (id, cpf) VALUES (20, '02680541340');
INSERT INTO pessoa (id, cpf) VALUES (21, '27240450848');
INSERT INTO pessoa (id, cpf) VALUES (22, '64789969304');
INSERT INTO pessoa (id, cpf) VALUES (23, '67242227372');
INSERT INTO pessoa (id, cpf) VALUES (19, '99958201372');
INSERT INTO pessoa (id, cpf) VALUES (24, '02664908324');

SELECT pg_catalog.setval('pessoa_id_seq', 5, true);

--
-- TOC entry 2055 (class 0 OID 59705)
-- Dependencies: 177
-- Data for Name: parecer; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO parecer (id, dataatribuicao, datarealizacao, observacao, parecer, prazo, status, documento_id, parecerista_id) VALUES (2, '2015-11-09 11:42:56.218', NULL, 'Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit,', NULL, '2015-11-18 00:00:00', NULL, NULL, 5);
INSERT INTO parecer (id, dataatribuicao, datarealizacao, observacao, parecer, prazo, status, documento_id, parecerista_id) VALUES (1, '2015-11-09 10:47:03.226', '2015-11-09 11:09:06.292', '', 'No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful.', '2015-11-10 00:00:00', 'FAVORAVEL', NULL, 6);
INSERT INTO parecer (id, dataatribuicao, datarealizacao, observacao, parecer, prazo, status, documento_id, parecerista_id) VALUES (3, '2015-11-09 11:43:42.377', '2015-11-09 11:45:25.24', 'To take a trivial example, which of us ever undertakes laborious physical exercise', 'Et harum quidem rerum facilis est et expedita distinctio.', '2015-11-18 00:00:00', 'NAO_FAVORAVEL', NULL, 1);



--
-- TOC entry 2061 (class 0 OID 59732)
-- Dependencies: 183
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (2, ' Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. ', NULL, 'PESQ0002', '"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat."', '2015-11-18 00:00:00', 'UFC - Campus Quixadá', 'Et harum quidem rerum facilis est et expedita distinctio', NULL, 'APROVADO', '2015-11-25 00:00:00', '2016-11-28 00:00:00', NULL, 4, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (7, 'he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."', NULL, 'PESQ0007', ' we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided.', '2015-11-27 00:00:00', 'UFC - Campus Quixadá', 'On the other hand', 'Adaptação automática de processos para incremento da maturidade em organizações de desenvolvimento de software', 'AGUARDANDO_PARECER', '2016-06-15 00:00:00', '2016-11-27 00:00:00', NULL, 12, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (5, 'o take a trivial example, which of us ever undertakes laborious physical exercise.', NULL, 'PESQ0005', 'Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"', '2015-11-27 00:00:00', 'UFC - Campus Quixadá', 'Passagem padrão original de Lorem Ipsum', 'Adaptação automática de processos para incremento da maturidade em organizações de desenvolvimento de software', 'AGUARDANDO_PARECER', '2015-12-20 00:00:00', '2015-11-30 00:00:00', NULL, 10, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (6, 'Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit', NULL, 'PESQ0006', ' Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.', '2015-11-27 00:00:00', 'UFC - Campus Quixadá', 'Finibus Bonorum et Malorum', 'Adaptação automática de processos para incremento da maturidade em organizações de desenvolvimento de software', 'AGUARDANDO_AVALIACAO', '2016-05-20 00:00:00', '2016-12-05 00:00:00', NULL, 11, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (8, 'objetivo pesquisar, desenvolver e avalia', NULL, 'PESQ0008', 'Um ensino de qualidade deve promover atividades que possam estimular e ajudar o aluno na compreensão dos conceitos como: questionamentos, debates, investigação, e o uso das tecnologias. Desta maneira, o aluno passa a entender a ciência como construção histórica e como saber prático, sem levar em consideração um ensino fundamentado na memorização de definições e classificações que não fazem sentido para ele. O projeto tem como objetivo pesquisar, desenvolver e avaliar recursos didáticos alternativos para o ensino de Ciências que contribuam para a melhoria do processo ensino-aprendizagem.', '2015-11-19 00:00:00', 'UFC - Campus Quixadá', 'nvestigando Recursos Didáticos para o ensino de Ciências ', NULL, 'SUBMETIDO', '2015-11-15 00:00:00', '2016-12-05 00:00:00', NULL, 15, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', NULL, 'PESQ0001', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2015-11-10 00:00:00', 'UFC - Campus Quixadá', 'Lorem ipsum dolor sit amet', NULL, 'SUBMETIDO', '2015-11-09 00:00:00', '2016-07-14 00:00:00', NULL, 5, NULL, 1);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (9, 'pesquisar, desenvolver e avalia', NULL, 'PESQ0009', 'Um ensino de qualidade deve promover atividades que possam estimular e ajudar o aluno na compreensão dos conceitos como: questionamentos, debates, investigação, e o uso das tecnologias. Desta maneira, o aluno passa a entender a ciência como construção histórica e como saber prático, sem levar em consideração um ensino fundamentado na memorização de definições e classificações que não fazem sentido para ele. O projeto tem como objetivo pesquisar, desenvolver e avaliar recursos didáticos alternativos para o ensino de Ciências que contribuam para a melhoria do processo ensino-aprendizagem.', '2015-11-26 00:00:00', 'UFC - Campus Quixadá', ' Investigando Recursos Didáticos para o ensino de Ciências ', NULL, 'REPROVADO', '2015-11-12 00:00:00', '2016-11-22 00:00:00', NULL, 16, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (10, 'Avaliamos também o impacto de doenças cerebrais (modelos experimentais) que afetam a memória e o efeito drogas que potencialmente podem contribuir para amenizar os danos destas doenças. ', NULL, 'PESQ0010', 'Têm sido bem estabelecido na literatura que a memória em animais (e em humanos) envolve várias estruturas cerebrais e vários sistemas de neurotransmissores/neuromoduladores. Neste projeto, usamos ratos e/ou camundongos em várias tarefas comportamentais (aversivas e não aversivas) para avaliarmos memória de curta e/ou longa duração. Avaliamos também o impacto de doenças cerebrais (modelos experimentais) que afetam a memória e o efeito drogas que potencialmente podem contribuir para amenizar os danos destas doenças. Uma abordagem muito relevante é avaliar o efeito da idade no declínio normal da memória em animais e os parâmetros bioquímicos envolvidos neste declínio.', '2015-12-04 00:00:00', 'UFC - Campus Quixadá', ' Parâmetros cerebrais envolvidos em processos de memória em animais', NULL, 'APROVADO_COM_RESTRICAO', '2015-11-27 00:00:00', '2018-12-05 00:00:00', NULL, 18, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (3, 'Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus', NULL, 'PESQ0003', 'On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."', '2015-11-28 00:00:00', 'UFC - Campus Quixadá', 'Seção 1.10.33 de "de Finibus Bonorum et Malorum", escrita por Cícero em 45 AC', NULL, 'APROVADO', '2015-12-20 00:00:00', '2016-11-09 00:00:00', NULL, 7, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (4, 'The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains', NULL, 'PESQ0004', 'On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."', '2015-11-26 00:00:00', 'UFC - Campus Quixadá', 'Tradução para o inglês por H. Rackha, feita em 1914', 'Adaptação automática de processos para incremento da maturidade em organizações de desenvolvimento de software', 'AGUARDANDO_AVALIACAO', '2015-11-25 00:00:00', '2016-10-05 00:00:00', NULL, 8, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (13, 'estudar e refletir sobre processos de formação inicial', NULL, 'PESQ0013', 'Este projeto de pesquisa tem por objetivos analisar e interpretar práticas de formação e de atuação de professores de Matemática de forma a compreender como se constitui o professor de Matemática, identificando as estratégias mais eficientes e reconhecendo quais saberes docentes são importantes para um exercício pleno da profissão. A pesquisa será realizada com professores da educação básica buscando uma integração entre a universidade e as escolas de forma a intensificar a aproximação entre ambas e enriquecer a qualidade da educação com um todo. Será realizado um estudo bibliográfico sobre metodologias voltadas ao ensino de Matemática e uso de tecnologias identificando suas qualidades e seus limites. Além disso, estudar e refletir sobre processos de formação inicial e continuada de professores analisando a forma como estes processos interferem em sua prática docente.', '2015-12-05 00:00:00', 'UFC - Campus Quixadá', ' EDUCAÇÃO CIENTÍFICA: AS TECNOLOGIAS EDUCATIVAS NO PROCESSO DE APRENDIZAGEM', NULL, 'APROVADO_COM_RESTRICAO', '2015-11-12 00:00:00', '2018-03-05 00:00:00', NULL, 23, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (11, 'organização superiores como o populacional ou de comunidade', NULL, 'PESQ0011', 'A avaliação de problemas ambientais, a detecção precoce dos efeitos deletérios por contaminantes ambientais tem sido considerada uma prioridade. Esta necessidade levou à utilização de biomarcadores de poluição de forma intensa nos últimos anos. Os biomarcadores podem ser definidos como qualquer alteração bioquímica, fisiológica, comportamental ou de outra natureza que indiquem a resposta dos organismos frente a um poluente ou uma mistura de poluentes. Ainda deve ser salientado que nos últimos tempos tem se estabelecido como paradigma que os biomarcadores possuam relevância ecológica, no sentido de que as respostas dos biomarcadores registradas num organismo devem indicar problemas que possam acontecer a níveis de organização superiores como o populacional ou de comunidade. Essa avaliação possibilitará a produção e divulgação de materiais didáticos a serem utilizados pela comunidade científica educacional.', '2015-11-30 00:00:00', 'UFC - Campus Quixadá', 'Avaliação integrada do ambiente aquático: produção e divulgação científica', NULL, 'REPROVADO', '2015-11-27 00:00:00', '2016-12-05 00:00:00', NULL, 22, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (12, 'explicações das características anátomo-fisiológicas dos sistemas reprodutores, na preocupação com a manutenção da família nucleaR.', NULL, 'PESQ0012', 'O presente projeto visa dar continuidade à pesquisa que foi realizada com professoras das séries iniciais do Ensino Fundamental de escolas públicas e particulares do município de Rio Grande quando participaram do curso Discutindo e refletindo sexualidade-AIDS com professoras das séries iniciais do Ensino Fundamental. Essa investigação evidenciou a existência de distintos discursos (com predomínio do biológico) e práticas para lidar com as questões relacionadas à sexualidade, em sala de aula. Falas e métodos esses, autorizados e instituídos pelos próprios programas escolares e refletidos nas explicações das características anátomo-fisiológicas dos sistemas reprodutores, na preocupação com a manutenção da família nuclear (branca, heterossexual, cristã), na crença de que a criança é inocente e assexuada, assim como, na percepção da sexualidade como ato sexual. Face aos resultados apontados, esse projeto de pesquisa que tem como objetivos principais investigar e discutir com os/as professores/as as suas práticas escolares relacionadas à sexualidade e investigar como as/os acadêmicas/os dos cursos de licenciatura falam sobre a sexualidade.', '2015-12-03 00:00:00', 'UFC - Campus Quixadá', ' Pensando práticas relacionadas à sexualidade na formação inicial e continuada de professores/as do Ensino Fundamental e Médio', NULL, 'APROVADO', '2015-11-12 00:00:00', '2016-12-04 00:00:00', NULL, 23, NULL, NULL);
INSERT INTO projeto (id, atividades, avaliacao, codigo, descricao, inicio, local, nome, observacaoavaliacao, status, submissao, termino, ata_id, autor_id, oficio_id, parecer_id) VALUES (14, '', NULL, 'PESQ0014', ' O objetivo geral deste projeto é desenvolver e avaliar o funcionamento de um mecanismo que possibilite a reutilização de processos de software amparado pela adaptação automática desses processos para contextos específicos.', '2015-11-27 00:00:00', 'UFC - Campus Quixadá', 'Reuso de Processos de Software: Adaptação automática de processos para incremento da maturidade em organizações de desenvolvimento de software', NULL, 'APROVADO_COM_RESTRICAO', '2015-11-12 00:00:00', '2016-11-27 00:00:00', NULL, 17, NULL, NULL);



--
-- TOC entry 2062 (class 0 OID 59741)
-- Dependencies: 184
-- Data for Name: papel_pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (1, 1);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (1, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (2, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (3, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (4, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (5, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (6, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (7, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (8, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (9, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (10, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (11, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (12, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (13, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (14, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (15, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (16, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (17, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (18, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (19, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (20, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (20, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (20, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (20, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (21, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (21, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (22, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (23, 2);
INSERT INTO papel_pessoa (pessoa_id, papel_id) VALUES (24, 2);




--
-- TOC entry 2081 (class 0 OID 0)
-- Dependencies: 176
-- Name: parecer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('parecer_id_seq', 3, true);


--
-- TOC entry 2057 (class 0 OID 59716)
-- Dependencies: 179
-- Data for Name: participacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (1, 2015, 2015, 400.00, 20, 2, 12, 1, 1);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (2, 2015, 2016, 450.00, 100, 11, 9, 7, 3);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (3, 2015, 2016, 600.00, 120, 2, 10, 7, 4);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (4, 2015, 2016, 350.00, 70, 5, 6, 9, 4);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (5, 2015, 2016, 350.00, 60, 4, 4, 10, 5);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (6, 2015, 2016, 400.00, 120, 4, 5, 12, 7);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (7, 2015, 2016, 400.00, 65, 2, 5, 9, 7);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (8, 2015, 2016, 400.00, 200, 12, 5, 20, 9);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (9, 2015, 2016, 500.00, 150, 12, 5, 18, 10);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (10, 2015, 2016, 400.00, 100, 12, 11, 20, 10);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (11, 2015, 2016, 400.00, 200, 12, 12, 22, 11);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (12, 2015, 2016, 400.00, 200, 12, 5, 23, 12);
INSERT INTO participacao (id, anoinicio, anotermino, bolsavalormensal, cargahorariamensal, mesinicio, mestermino, participante_id, projeto_id) VALUES (13, 2015, 2016, 400.00, 120, 12, 10, 21, 14);


--
-- TOC entry 2082 (class 0 OID 0)
-- Dependencies: 178
-- Name: participacao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('participacao_id_seq', 13, true);



--
-- TOC entry 2083 (class 0 OID 0)
-- Dependencies: 180
-- Name: pessoa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pessoa_id_seq', 5, true);



--
-- TOC entry 2084 (class 0 OID 0)
-- Dependencies: 182
-- Name: projeto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('projeto_id_seq', 14, true);


-- Completed on 2015-11-16 10:26:14 BRT

--
-- PostgreSQL database dump complete
--

