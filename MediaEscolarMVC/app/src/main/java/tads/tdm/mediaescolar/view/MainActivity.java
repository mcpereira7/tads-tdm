package tads.tdm.mediaescolar.view;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.DecimalFormat;

import tads.tdm.mediaescolar.fragmets.BimestreAFragment;
import tads.tdm.mediaescolar.fragmets.BimestreBFragment;
import tads.tdm.mediaescolar.fragmets.BimestreCFragment;
import tads.tdm.mediaescolar.fragmets.BimestreDFragment;
import tads.tdm.mediaescolar.fragmets.ResultadoFinalFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Primeiro Passo
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(tads.tdm.mediaescolar.R.layout.activity_main);
        Toolbar toolbar = findViewById(tads.tdm.mediaescolar.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(tads.tdm.mediaescolar.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sincronização do sistema....", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(tads.tdm.mediaescolar.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, tads.tdm.mediaescolar.R.string.navigation_drawer_open, tads.tdm.mediaescolar.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(tads.tdm.mediaescolar.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Segundo Passo
        fragmentManager = getSupportFragmentManager();

        // Quarto passo - Setando a tela principal
        //fragmentManager.beginTransaction().replace(R.id.content_fragment, new ModeloFragment()).commit();
        fragmentManager.beginTransaction().replace(tads.tdm.mediaescolar.R.id.content_fragment, new ResultadoFinalFragment()).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(tads.tdm.mediaescolar.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(tads.tdm.mediaescolar.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == tads.tdm.mediaescolar.R.id.action_sair) {

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == tads.tdm.mediaescolar.R.id.nav_bimestre_a) {

            setTitle("Notas 1º Bimestre");

            fragmentManager.beginTransaction().replace(tads.tdm.mediaescolar.R.id.content_fragment, new BimestreAFragment()).commit();

        } else if (id == tads.tdm.mediaescolar.R.id.nav_bimestre_b) {

            setTitle("Notas 2º Bimestre");

            fragmentManager.beginTransaction().replace(tads.tdm.mediaescolar.R.id.content_fragment, new BimestreBFragment()).commit();

        } else if (id == tads.tdm.mediaescolar.R.id.nav_bimestre_c) {

            setTitle("Notas 3º Bimestre");

            fragmentManager.beginTransaction().replace(tads.tdm.mediaescolar.R.id.content_fragment, new BimestreCFragment()).commit();

        } else if (id == tads.tdm.mediaescolar.R.id.nav_bimestre_d) {

            setTitle("Notas 4º Bimestre");

            fragmentManager.beginTransaction().replace(tads.tdm.mediaescolar.R.id.content_fragment, new BimestreDFragment()).commit();

        } else if (id == tads.tdm.mediaescolar.R.id.nav_resultado_final) {

            setTitle("Resultado Final");

            fragmentManager.beginTransaction().replace(tads.tdm.mediaescolar.R.id.content_fragment, new ResultadoFinalFragment()).commit();

        } else if (id == tads.tdm.mediaescolar.R.id.nav_share) {

            String shareBody = getString(tads.tdm.mediaescolar.R.string.share_body);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(tads.tdm.mediaescolar.R.string.share_subject));

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getString(tads.tdm.mediaescolar.R.string.share_with)));

        }

        DrawerLayout drawer = findViewById(tads.tdm.mediaescolar.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static String formatarValorDecimal(Double valor)
    {
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return df.format(valor);
    }

}
