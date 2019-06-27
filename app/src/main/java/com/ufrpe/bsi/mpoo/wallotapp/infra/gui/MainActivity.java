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
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
    private NavigationView navigationView;

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

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        InicioFragment fragment = new InicioFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportActionBar().setTitle("Inicio");


        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nome_usuario_main);
        txtProfileName.setText(usuario.getNome());
        TextView txtProfileEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email_usuario_main);
        txtProfileEmail.setText(usuario.getEmail());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        int id = menuItem.getItemId();
        if (id == R.id.inicio){
            getSupportActionBar().setTitle("Inicio");
            InicioFragment fragment = new InicioFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Inicio");
            fragmentTransaction.commit();
        } else if (id == R.id.registro) {
            getSupportActionBar().setTitle("Registro");
            RegistroFragment fragment = new RegistroFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Registro");
            fragmentTransaction.commit();
        } else if (id == R.id.grafico) {
            getSupportActionBar().setTitle("Gráfico");
            GraficoFragment fragment = new GraficoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Gráfico");
            fragmentTransaction.commit();
        } else if (id == R.id.metas) {
            getSupportActionBar().setTitle("Metas");
            MetaFragment fragment = new MetaFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Metas");
            fragmentTransaction.commit();
        } else if (id == R.id.orcamento) {
            getSupportActionBar().setTitle("Orçamento");
            OrcamentoFragment fragment = new OrcamentoFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(new InicioFragment(), "Inicio").addToBackStack("Inicio");
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Orçamento");
            fragmentTransaction.commit();
        } else if (id == R.id.configuracao) {
            startActivity(new Intent(MainActivity.this, ConfiguracaoActivity.class));
        } else if (id == R.id.sair) {
            usuarioServices.logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            getSupportActionBar().setTitle("Inicio");
            drawerLayout.closeDrawer(GravityCompat.START);
            if (navigationView.getMenu().getItem(0).isChecked()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Você tem certeza que deseja sair?").setCancelable(false)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                usuarioServices.logout();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else{
                navigationView.getMenu().getItem(0).setChecked(true);
                super.onBackPressed();
            }
        }
    }
}
