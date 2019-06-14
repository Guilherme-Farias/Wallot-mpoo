package com.ufrpe.bsi.mpoo.wallotapp.infra.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ufrpe.bsi.mpoo.wallotapp.R;
import com.ufrpe.bsi.mpoo.wallotapp.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.negocio.UsuarioServices;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String barra;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentTransaction fragmentTransaction;
    private UsuarioServices usuarioServices = new UsuarioServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario usuario =  SessaoUsuario.instance.getUsuario();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InicioFragment fragment = new InicioFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
        barra = "Inicio";
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(barra);


        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nome_usuario_main);
        txtProfileName.setText(usuario.getNome());
        TextView txtProfileEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email_usuario_main);
        txtProfileEmail.setText(usuario.getEmail());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.inicio){
            InicioFragment fragment = new InicioFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
            barra = "Inicio";
            fragmentTransaction.commit();
        } else if (id == R.id.registro) {
            RegistroFragment fragment = new RegistroFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Registro");
            barra = "Registro";
            fragmentTransaction.commit();
        } else if (id == R.id.grafico) {
            GraficoFragment fragment = new GraficoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Gráfico");
            barra = "Gráfico";
            fragmentTransaction.commit();
        } else if (id == R.id.metas) {
            MetaFragment fragment = new MetaFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Metas");
            barra = "Metas";
            fragmentTransaction.commit();
        } else if (id == R.id.orcamento) {
            OrcamentoFragment fragment = new OrcamentoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Orçamento");
            barra = "Orçamento";
            fragmentTransaction.commit();
        } else if (id == R.id.configuracao) {
            startActivity(new Intent(MainActivity.this, ConfiguracaoActivity.class));
        } else if (id == R.id.sair) {
            usuarioServices.logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        getSupportActionBar().setTitle(barra);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
