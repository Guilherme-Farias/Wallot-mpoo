package com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;

public class InsertData {
    public void start() {
        CategoriaServices categoriaServices = new CategoriaServices();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        SubCategoriaServices subCategoriaServices = new SubCategoriaServices();
        SubCategoriaDAO subCategoriaDAO = new SubCategoriaDAO();

        cadastraCategorias(categoriaServices, categoriaDAO);
        cadastraSubCategoria(subCategoriaServices, subCategoriaDAO);

    }

    public void cadastraCategorias(CategoriaServices categoriaServices, CategoriaDAO categoriaDAO) {
        Categoria categoria;

        //Categoria Sem Categoria

        categoria = new Categoria();

        categoria.setId(1);
        categoria.setNome("Sem Categoria");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_custom_round));
        categoria.setFkUsuario(0);

        categoriaDAO.cadastrar(categoria);

        //Categoria Alimentação

        categoria = new Categoria();

        categoria.setId(2);
        categoria.setNome("Alimentação");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_alimentacao_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);

        //Categoria Casa

        categoria = new Categoria();

        categoria.setId(3);
        categoria.setNome("Casa");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_casa_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);


        //Categoria Compras

        categoria = new Categoria();

        categoria.setId(4);
        categoria.setNome("Compras");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_compras_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);

        //Categoria Comunicação

        categoria = new Categoria();

        categoria.setId(5);
        categoria.setNome("Comunicação");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_comunicacao_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);

        //Categoria Transporte

        categoria = new Categoria();

        categoria.setId(6);
        categoria.setNome("Transporte");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_transporte_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);


        //Categoria Veículo

        categoria = new Categoria();

        categoria.setId(7);
        categoria.setNome("Veículo");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_veiculo_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);

        //Categoria Vida e Lazer

        categoria = new Categoria();

        categoria.setId(8);
        categoria.setNome("Vida e Lazer");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_vida_lazer_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);

        //Categoria Outros

        categoria = new Categoria();

        categoria.setId(9);
        categoria.setNome("Outros");
        categoria.setIcone(categoriaServices.bitmapToByteArray(R.drawable.ic_custom_round));
        categoria.setFkUsuario(-1);

        categoriaDAO.cadastrar(categoria);
    }

    public void cadastraSubCategoria(SubCategoriaServices subCategoriaServices, SubCategoriaDAO subCategoriaDAO) {
        SubCategoria subCategoria;

        //Subcategoria Sem Subcategoria (Sem Categoria)

        subCategoria = new SubCategoria();

        subCategoria.setId(1);
        subCategoria.setNome("Sem SubCategoria");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_custom_round));
        subCategoria.setFkCategoria(1);
        subCategoria.setFkUsuario(0);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Fast Food (Alimentação)

        subCategoria = new SubCategoria();

        subCategoria.setId(2);
        subCategoria.setNome("Fast Food");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_fast_food_round));
        subCategoria.setFkCategoria(2);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Feira (Alimentação)

        subCategoria = new SubCategoria();

        subCategoria.setId(3);
        subCategoria.setNome("Feira");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_feira_round));
        subCategoria.setFkCategoria(2);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Restaurante (Alimentação)

        subCategoria = new SubCategoria();

        subCategoria.setId(4);
        subCategoria.setNome("Restaurante");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_restaurante_round));
        subCategoria.setFkCategoria(2);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Água (Casa)

        subCategoria = new SubCategoria();

        subCategoria.setId(5);
        subCategoria.setNome("Água");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_agua_round));
        subCategoria.setFkCategoria(3);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Aluguel (Casa)

        subCategoria = new SubCategoria();

        subCategoria.setId(6);
        subCategoria.setNome("Aluguel");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_aluguel_round));
        subCategoria.setFkCategoria(3);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Gás (Casa)

        subCategoria = new SubCategoria();

        subCategoria.setId(7);
        subCategoria.setNome("Gás");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_gas_round));
        subCategoria.setFkCategoria(3);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Luz (Casa)

        subCategoria = new SubCategoria();

        subCategoria.setId(8);
        subCategoria.setNome("Luz");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_luz_round));
        subCategoria.setFkCategoria(3);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Manuntenção (Casa)

        subCategoria = new SubCategoria();

        subCategoria.setId(9);
        subCategoria.setNome("Manuntenção");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_manuntencao_casa_round));
        subCategoria.setFkCategoria(3);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Animais (Compras)

        subCategoria = new SubCategoria();

        subCategoria.setId(10);
        subCategoria.setNome("Animais");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_animais_round));
        subCategoria.setFkCategoria(4);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Beleza (Compras)

        subCategoria = new SubCategoria();

        subCategoria.setId(11);
        subCategoria.setNome("Beleza");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_beleza_round));
        subCategoria.setFkCategoria(4);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Eletrônicos (Compras)

        subCategoria = new SubCategoria();

        subCategoria.setId(12);
        subCategoria.setNome("Eletrônicos");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_eletronicos_round));
        subCategoria.setFkCategoria(4);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Farmácia (Compras)

        subCategoria = new SubCategoria();

        subCategoria.setId(13);
        subCategoria.setNome("Farmácia");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_farmacia_round));
        subCategoria.setFkCategoria(4);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Vestuário (Compras)

        subCategoria = new SubCategoria();

        subCategoria.setId(14);
        subCategoria.setNome("Vestuário");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_vestuario_round));
        subCategoria.setFkCategoria(4);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Internet (Comunicação)

        subCategoria = new SubCategoria();

        subCategoria.setId(15);
        subCategoria.setNome("Internet");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_internet_round));
        subCategoria.setFkCategoria(5);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Serviços Postais (Comunicação)

        subCategoria = new SubCategoria();

        subCategoria.setId(16);
        subCategoria.setNome("Serviços Postais");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_servicos_postais_round));
        subCategoria.setFkCategoria(5);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Telefone (Comunicação)

        subCategoria = new SubCategoria();

        subCategoria.setId(17);
        subCategoria.setNome("Telefone");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_telefone_round));
        subCategoria.setFkCategoria(5);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Longas Distâncias (Transportes)

        subCategoria = new SubCategoria();

        subCategoria.setId(18);
        subCategoria.setNome("Longas Distâncias");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_longas_distancias_round));
        subCategoria.setFkCategoria(6);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Particular (Transportes)

        subCategoria = new SubCategoria();

        subCategoria.setId(19);
        subCategoria.setNome("Particular");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_particular_round));
        subCategoria.setFkCategoria(6);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Público (Transportes)

        subCategoria = new SubCategoria();

        subCategoria.setId(20);
        subCategoria.setNome("Público");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_publico_round));
        subCategoria.setFkCategoria(6);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Combustível (Veículo)

        subCategoria = new SubCategoria();

        subCategoria.setId(21);
        subCategoria.setNome("Combustível");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_combustivel_round));
        subCategoria.setFkCategoria(7);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Estacionamento (Veículo)

        subCategoria = new SubCategoria();

        subCategoria.setId(22);
        subCategoria.setNome("Estacionamento");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_estacionamento_round));
        subCategoria.setFkCategoria(7);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Manuntenção (Veículo)

        subCategoria = new SubCategoria();

        subCategoria.setId(23);
        subCategoria.setNome("Manuntenção");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_manuntencao_carro_round));
        subCategoria.setFkCategoria(7);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Seguro (Veículo)

        subCategoria = new SubCategoria();

        subCategoria.setId(24);
        subCategoria.setNome("Seguro");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_seguro_round));
        subCategoria.setFkCategoria(7);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Bebida e Cigarro (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(25);
        subCategoria.setNome("Bebida e Cigarro");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_bebida_cigarro_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Educação (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(26);
        subCategoria.setNome("Educação");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_educacao_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Fitness (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(27);
        subCategoria.setNome("Fitness");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_fitness_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Saúde (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(28);
        subCategoria.setNome("Saúde");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_saude_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Software e Jogos (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(29);
        subCategoria.setNome("Software e Jogos");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_software_jogos_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria TV e Streaming (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(30);
        subCategoria.setNome("TV e Streaming");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_tv_streaming_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

        subCategoriaDAO.cadastrar(subCategoria);

        //Subcategoria Viagens (Vida e Lazer)

        subCategoria = new SubCategoria();

        subCategoria.setId(31);
        subCategoria.setNome("Viagens");
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(R.drawable.ic_viagens_round));
        subCategoria.setFkCategoria(8);
        subCategoria.setFkUsuario(-1);

    }

}
