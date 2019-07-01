package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.gui.LoginActivity;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private UsuarioServices usuarioServices = new UsuarioServices();
    private NavigationView navigationView;
    private Usuario usuario =  SessaoUsuario.instance.getUsuario();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //pega itens do layout
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //cria o navbar
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerLayout.closeDrawer(GravityCompat.START);

        //pre seta o intent e algumas informações
        inicioIntent();
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        //seta as irnformações do usuario no navBar
        TextView txtProfileName = navigationView.getHeaderView(0).findViewById(R.id.nome_usuario_main);
        txtProfileName.setText(usuario.getNome());
        TextView txtProfileEmail = navigationView.getHeaderView(0).findViewById(R.id.email_usuario_main);
        txtProfileEmail.setText(usuario.getEmail());

    }
    //navega pelas opções do navbar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        int id = menuItem.getItemId();
        if (id == R.id.inicio){
            inicioIntent();
        } else if (id == R.id.registro) {
            registroIntent();
        } else if (id == R.id.grafico) {
            graficoIntent();
        } else if (id == R.id.metas) {
            metasIntent();
        } else if (id == R.id.orcamento) {
            orcamentoIntent();
        } else if (id == R.id.configuracao) {
            configuracaoIntent();
        } else if (id == R.id.sair) {
            desejaSairDialog();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //inicia a transacao para inicioFragment
    private void inicioIntent() {
        getSupportActionBar().setTitle("Inicio");
        InicioFragment fragment = new InicioFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
        fragmentTransaction.commit();
    }

    //inicia a transacao para registroFragment
    private void registroIntent() {
        getSupportActionBar().setTitle("Registro");
        RegistroFragment fragment = new RegistroFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Registro");
        fragmentTransaction.commit();
    }

    //inicia a transacao para transacaoFragment
    private void graficoIntent() {
        getSupportActionBar().setTitle("Gráfico");
        GraficoFragment fragment = new GraficoFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Gráfico");
        fragmentTransaction.commit();
    }

    //inicia a transacao para metasFragment
    private void metasIntent() {
        getSupportActionBar().setTitle("Metas");
        MetaFragment fragment = new MetaFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Metas");
        fragmentTransaction.commit();
    }

    //inicia a transacao para orcamentoIntent
    private void orcamentoIntent() {
        getSupportActionBar().setTitle("Orçamento");
        OrcamentoFragment fragment = new OrcamentoFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Orçamento");
        fragmentTransaction.commit();
    }

    //inicia a transacao para configuracaoActivity
    private void configuracaoIntent() {
        startActivity(new Intent(MainActivity.this, ConfiguracaoActivity.class));
    }

    //inicia a transacao para logout
    private void logoutIntent() {
        usuarioServices.logout();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    //pergunta se o usuario deseja mesmo sair
    private void desejaSairDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Você tem certeza que deseja sair?")
                .setCancelable(false)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {logoutIntent();}
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //inicia a transacao para inicioFragment
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            getSupportActionBar().setTitle("Inicio");
            drawerLayout.closeDrawer(GravityCompat.START);
            if (navigationView.getMenu().getItem(0).isChecked()){
                desejaSairDialog();
            } else{
                navigationView.getMenu().getItem(0).setChecked(true);
                super.onBackPressed();
            }
        }
    }
}
