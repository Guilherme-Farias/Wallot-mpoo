package com.ufrpe.bsi.mpoo.wallotapp.infra.persistencia;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.negocio.CategoriaServices;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.negocio.SubCategoriaServices;

public class DBFill {
    private CategoriaServices categoriaServices = new CategoriaServices();
    private SubCategoriaServices subCategoriaServices = new SubCategoriaServices();
    public void start() {
        cadastraCategorias();
        cadastraSubCategorias();
    }
    private void cadastraCategorias(){
        //categoria sem categoria
        Categoria categoria = getCategoria("Sem Categoria", R.drawable.icone_custom);
        categoria.setFkUsuario(0);
        categoriaServices.cadastrar(categoria);

        //Categoria Alimentação
        criaCategoria("Alimentação", R.drawable.icone_alimentacao);

        //Categoria Casa
        criaCategoria("Casa", R.drawable.icone_casa);


        //Categoria Compras
        criaCategoria("Compras", R.drawable.icone_compras);

        //Categoria Comunicação
        criaCategoria("Comunicação", R.drawable.icone_comunicacao);

        //Categoria Transporte
        criaCategoria("Transporte", R.drawable.icone_transporte);


        //Categoria Veículo
        criaCategoria("Veículo", R.drawable.icone_veiculo);

        //Categoria Vida e Lazer
        criaCategoria("Vida e Lazer", R.drawable.icone_vida_lazer);

        //Categoria Outros
        criaCategoria("Outros", R.drawable.icone_custom);
    }

    private void criaCategoria(String outros, int p) {
        Categoria categoria = getCategoria(outros, p);
        categoriaServices.cadastroInicial(categoria);
    }

    private Categoria getCategoria(String outros, int p) {
        Categoria categoria = new Categoria();
        categoria.setNome(outros);
        categoria.setIcone(categoriaServices.bitmapToByteArray(p));
        return categoria;
    }

    private void cadastraSubCategorias() {

        //Subcategoria sem subcategoria
        cadastraSubcategoria("Sem Subcategoria", R.drawable.icone_custom, 1);

        //Subcategoria Fast Food (Alimentação)
        cadastraSubcategoria("Fast Food", R.drawable.icone_fast_food, 2);

        //Subcategoria Feira (Alimentação)
        cadastraSubcategoria("Feira", R.drawable.icone_feira, 2);

        //Subcategoria Restaurante (Alimentação)
        cadastraSubcategoria("Restaurante", R.drawable.icone_restaurante, 2);

        //Subcategoria Água (Casa)
        cadastraSubcategoria("Água", R.drawable.icone_agua, 3);

        //Subcategoria Aluguel (Casa)
        cadastraSubcategoria("Aluguel", R.drawable.icone_aluguel, 3);

        //Subcategoria Gás (Casa)
        cadastraSubcategoria("Gás", R.drawable.icone_gas, 3);

        //Subcategoria Luz (Casa)
        cadastraSubcategoria("Luz", R.drawable.icone_luz, 3);

        //Subcategoria Manuntenção (Casa)
        cadastraSubcategoria("Manuntenção", R.drawable.icone_manuntencao_casa, 3);

        //Subcategoria Animais (Compras)
        cadastraSubcategoria("Animais", R.drawable.icone_animais, 4);

        //Subcategoria Beleza (Compras)
        cadastraSubcategoria("Beleza", R.drawable.icone_beleza, 4);

        //Subcategoria Eletrônicos (Compras)
        cadastraSubcategoria("Eletrônicos", R.drawable.icone_eletronicos, 4);

        //Subcategoria Farmácia (Compras)
        cadastraSubcategoria("Farmácia", R.drawable.icone_farmacia, 4);

        //Subcategoria Vestuário (Compras)
        cadastraSubcategoria("Vestuário", R.drawable.icone_vestuario, 4);

        //Subcategoria Internet (Comunicação)
        cadastraSubcategoria("Internet", R.drawable.icone_internet, 5);

        //Subcategoria Serviços Postais (Comunicação)
        cadastraSubcategoria("Serviços Postais", R.drawable.icone_servicos_postais, 5);

        //Subcategoria Telefone (Comunicação)
        cadastraSubcategoria("Telefone", R.drawable.icone_telefone, 5);

        //Subcategoria Longas Distâncias (Transportes)
        cadastraSubcategoria("Longas Distâncias", R.drawable.icone_longas_distancias, 6);

        //Subcategoria Particular (Transportes)
        cadastraSubcategoria("Particular", R.drawable.icone_transporte_particular, 6);

        //Subcategoria Público (Transportes)
        cadastraSubcategoria("Público", R.drawable.icone_transporte_publico, 6);

        //Subcategoria Combustível (Veículo)
        cadastraSubcategoria("Combustível", R.drawable.icone_combustivel, 7);

        //Subcategoria Estacionamento (Veículo)
        cadastraSubcategoria("Estacionamento", R.drawable.icone_estacionamento, 7);

        //Subcategoria Manuntenção (Veículo)
        cadastraSubcategoria("Manuntenção", R.drawable.icone_manuntencao_carro, 7);

        //Subcategoria Seguro (Veículo)
        cadastraSubcategoria("Seguro", R.drawable.icone_seguro, 7);

        //Subcategoria Bebida e Cigarro (Vida e Lazer)
        cadastraSubcategoria("Bebida e Cigarro", R.drawable.icone_bebida_cigarro, 8);

        //Subcategoria Educação (Vida e Lazer)
        cadastraSubcategoria("Educação", R.drawable.icone_educacao, 8);

        //Subcategoria Fitness (Vida e Lazer)
        cadastraSubcategoria("Fitness", R.drawable.icone_fitness, 8);

        //Subcategoria Saúde (Vida e Lazer)
        cadastraSubcategoria("Saúde", R.drawable.icone_saude, 8);

        //Subcategoria Software e Jogos (Vida e Lazer)
        cadastraSubcategoria("Software e Jogos", R.drawable.icone_software, 8);

        //Subcategoria TV e Streaming (Vida e Lazer)
        cadastraSubcategoria("TV e Streaming", R.drawable.icone_tv_streaming, 8);

        //Subcategoria Viagens (Vida e Lazer)
        cadastraSubcategoria("Viagens", R.drawable.icone_viagens, 8);
    }

    private void cadastraSubcategoria(String nome, int p, int i) {
        SubCategoria subCategoria = new SubCategoria();
        subCategoria.setNome(nome);
        subCategoria.setIcone(subCategoriaServices.bitmapToByteArray(p));
        subCategoria.setFkCategoria(i);
        subCategoriaServices.cadastrarInicial(subCategoria);
    }

}
