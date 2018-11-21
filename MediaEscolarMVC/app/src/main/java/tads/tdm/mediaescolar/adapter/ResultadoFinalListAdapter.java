package tads.tdm.mediaescolar.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.tdm.mediaescolar.R;
import android.tdm.mediaescolar.controller.MediaEscolarController;
import android.tdm.mediaescolar.model.MediaEscolar;
import android.tdm.mediaescolar.view.MainActivity;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tads.tdm.mediaescolar.controller.MediaEscolarController;
import tads.tdm.mediaescolar.model.MediaEscolar;

/**
 * Created by marcomaddo on 23/02/2018.
 */

public class ResultadoFinalListAdapter
        extends ArrayAdapter<MediaEscolar>
        implements View.OnClickListener {


    Context context;

    // AlertDialog
    AlertDialog.Builder build;
    AlertDialog alert;

    MediaEscolar mediaEscolar;
    MediaEscolarController controller;

    // Atualização do dataSet
    ArrayList<MediaEscolar> dados;

    ViewHolder linha;

    private static class ViewHolder {

        TextView txtBimestre;
        TextView txtSituacao;
        TextView txtMateria;
        TextView txtMedia;
        ImageView imgLogo, imgDeletar, imgEditar, imgConsultar, imgSalvar;

    }

    public ResultadoFinalListAdapter(ArrayList<MediaEscolar> dataSet, Context context) {
        super(context, tads.tdm.mediaescolar.R.layout.lisview_resultado_final, dataSet);

        this.context = context;
        this.dados = dataSet;
    }

    public void atualizarLista(ArrayList<MediaEscolar> novosDados) {
        this.dados.clear();
        this.dados.addAll(novosDados);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        int posicao = (Integer) view.getTag();

        Object object = getItem(posicao);

        mediaEscolar = (MediaEscolar) object;

        controller = new MediaEscolarController(getContext());

        switch (view.getId()) {

            case tads.tdm.mediaescolar.R.id.imgLogo:

                // Aprensentar os dados detalhados

                Snackbar.make(view, "Nota da Prova " + mediaEscolar.getNotaProva(),
                        Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;


            case tads.tdm.mediaescolar.R.id.imgConsultar:

                build = new AlertDialog.Builder(getContext());
                build.setTitle("CONSULTAR");
                build.setMessage("\n\nBimestre: " +
                        mediaEscolar.getBimestre() + "\nMatéria: " +
                        mediaEscolar.getMateria() + "\nSituação: " +
                        mediaEscolar.getSituacao() + "\n\nMédia Final: " +
                        mediaEscolar.getMediaFinal());
                build.setCancelable(true);
                build.setIcon(tads.tdm.mediaescolar.R.mipmap.ic_launcher);

                build.setPositiveButton("VOLTAR", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });

                alert = build.create();
                alert.show();


                break;

            case tads.tdm.mediaescolar.R.id.imgDeletar:

                build = new AlertDialog.Builder(getContext());
                build.setTitle("ALERTA");
                build.setMessage("Deseja DELETAR este registro?");
                build.setCancelable(true);
                build.setIcon(tads.tdm.mediaescolar.R.mipmap.ic_launcher);

                build.setPositiveButton("SIM", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        controller.deletar(mediaEscolar);

                       // linha.imgDeletar.setEnabled(false);
                       // linha.imgDeletar.setVisibility(View.GONE);

                        atualizarLista(controller.getResultadoFinal());


                    }
                });

                build.setNegativeButton("CANCELAR", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                alert = build.create();
                alert.show();


                break;

            case tads.tdm.mediaescolar.R.id.imgSalvar:


                build = new AlertDialog.Builder(getContext());
                build.setTitle("ALERTA");
                build.setMessage("Salvando.....");
                build.setCancelable(true);
                build.setIcon(tads.tdm.mediaescolar.R.mipmap.ic_launcher);



                build.setNegativeButton("OK", new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                alert = build.create();
                alert.show();


                break;

            case tads.tdm.mediaescolar.R.id.imgEditar:

                View alertView = view.inflate(getContext(), tads.tdm.mediaescolar.R.layout.alert_dialog_editar_listview, null);

                final EditText editMateria = alertView.findViewById(tads.tdm.mediaescolar.R.id.editMateria);
                final EditText editNotaTrabalho =  alertView.findViewById(tads.tdm.mediaescolar.R.id.editNotaTrabalho);
                final EditText editNotaProva =  alertView.findViewById(tads.tdm.mediaescolar.R.id.editNotaProva);


                editMateria.setText(mediaEscolar.getMateria());
                editNotaProva.setText(String.valueOf(mediaEscolar.getNotaProva()));
                editNotaTrabalho.setText(String.valueOf(mediaEscolar.getNotaTrabalho()));


                AlertDialog.Builder alertbox = new AlertDialog.Builder(alertView.getRootView().getContext());
                alertbox.setMessage(mediaEscolar.getBimestre());
                alertbox.setTitle("Editando");

                alertbox.setView(alertView);

                alertbox.setNeutralButton("Salvar",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                                mediaEscolar.setMateria(editMateria.getText().toString());
                                mediaEscolar.setNotaProva(Double.parseDouble(editNotaProva.getText().toString()));
                                mediaEscolar.setNotaTrabalho(Double.parseDouble(editNotaTrabalho.getText().toString()));

                                Double mediaFinal = controller.calcularMedia(mediaEscolar);

                                mediaEscolar.setMediaFinal(mediaFinal);
                                mediaEscolar.setSituacao(controller.resultadoFinal(mediaFinal));

                                controller.alterar(mediaEscolar);

                                atualizarLista(controller.getResultadoFinal());

                            }
                        });
                alertbox.show();

                break;
        }
    }

    // Padrão de projeto Observer
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @NonNull
    @Override
    public View getView(int position,
                        View dataSet,
                        @NonNull ViewGroup parent) {

        mediaEscolar = getItem(position);

        //ViewHolder linha;

        if (dataSet == null) {

            linha = new ViewHolder();

            LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());

            dataSet = layoutResultadoFinalList.inflate(tads.tdm.mediaescolar.R.layout.lisview_resultado_final,
                    parent,
                    false);

            // Personalização da linha no Listview
            linha.txtMateria = dataSet.findViewById(tads.tdm.mediaescolar.R.id.txtMateria);
            linha.txtBimestre = dataSet.findViewById(tads.tdm.mediaescolar.R.id.txtBimestre);
            linha.txtSituacao = dataSet.findViewById(tads.tdm.mediaescolar.R.id.txtResultado);
            linha.txtMedia = dataSet.findViewById(tads.tdm.mediaescolar.R.id.txtMedia);

            linha.imgLogo = dataSet.findViewById(tads.tdm.mediaescolar.R.id.imgLogo);
            linha.imgDeletar = dataSet.findViewById(tads.tdm.mediaescolar.R.id.imgDeletar);
            linha.imgEditar = dataSet.findViewById(tads.tdm.mediaescolar.R.id.imgEditar);
            linha.imgConsultar = dataSet.findViewById(tads.tdm.mediaescolar.R.id.imgConsultar);
            linha.imgSalvar = dataSet.findViewById(tads.tdm.mediaescolar.R.id.imgSalvar);

            dataSet.setTag(linha);


        } else {

            linha = (ViewHolder) dataSet.getTag();

        }

        linha.txtMateria.setText(mediaEscolar.getMateria());
        linha.txtBimestre.setText(mediaEscolar.getBimestre());
        linha.txtSituacao.setText(mediaEscolar.getSituacao());

        if(mediaEscolar.getMediaFinal()<7)  linha.txtSituacao.setText("*** REPROVADO ***");

        linha.txtMedia.setText(MainActivity.formatarValorDecimal(mediaEscolar.getMediaFinal()));

        linha.txtMedia.setText(String.valueOf(mediaEscolar.getMediaFinal()));

        linha.imgLogo.setOnClickListener(this);
        linha.imgLogo.setTag(position);

        linha.imgConsultar.setOnClickListener(this);
        linha.imgConsultar.setTag(position);

        linha.imgDeletar.setOnClickListener(this);
        linha.imgDeletar.setTag(position);

        linha.imgEditar.setOnClickListener(this);
        linha.imgEditar.setTag(position);

        linha.imgSalvar.setOnClickListener(this);
        linha.imgSalvar.setTag(position);

        return dataSet;
    }


}
