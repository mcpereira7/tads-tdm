package tads.tdm.mediaescolar.controller;

import android.content.ContentValues;
import android.content.Context;
import android.tdm.mediaescolar.datamodel.MediaEscolarDataModel;
import android.tdm.mediaescolar.datasource.DataSource;
import android.tdm.mediaescolar.model.MediaEscolar;

import java.util.ArrayList;
import java.util.List;

import tads.tdm.mediaescolar.datamodel.MediaEscolarDataModel;
import tads.tdm.mediaescolar.datasource.DataSource;

/**
 * Created by marcomaddo on 18/01/2018.
 */

public class MediaEscolarController extends DataSource {

    ContentValues dados;

    public MediaEscolarController(Context context) {
        super(context);
    }

    public double calcularMedia(MediaEscolar obj) {
        return (obj.getNotaProva() + obj.getNotaTrabalho()) / 2;
    }

    public String resultadoFinal(double media) {
        return media >= 7 ? "Aprovado" : "Reprovado";
    }

    /**
     * Método que recebe um objeto MediaEscolar e prepara para enviar
     * para o DataSource e salvar no banco de dados.
     *
     * @param obj é um Objeto MediaEscolar
     * @return verdadeiro se salvou com sucesso, falso em caso de erro.
     * @see android.tdm.mediaescolar.datasource
     * @since versão 1.0-2018
     */
    public boolean salvar(MediaEscolar obj) {

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(MediaEscolarDataModel.getMateria(), obj.getMateria());
        dados.put(MediaEscolarDataModel.getBimestre(), obj.getBimestre());
        dados.put(MediaEscolarDataModel.getSituacao(), obj.getSituacao());
        dados.put(MediaEscolarDataModel.getNotaProva(), obj.getNotaProva());
        dados.put(MediaEscolarDataModel.getNotaMateria(), obj.getNotaTrabalho());
        dados.put(MediaEscolarDataModel.getMediaFinal(), obj.getMediaFinal());

        sucesso = insert(MediaEscolarDataModel.getTABELA(), dados);

        return sucesso;
    }

    public boolean deletar(MediaEscolar obj) {

        boolean sucesso = true;

        sucesso = deletar(MediaEscolarDataModel.getTABELA(), obj.getId());

        return sucesso;
    }

    public boolean alterar(MediaEscolar obj) {

        boolean sucesso = true;

        dados = new ContentValues();

        dados.put(MediaEscolarDataModel.getId(), obj.getId());
        dados.put(MediaEscolarDataModel.getMateria(), obj.getMateria());
        dados.put(MediaEscolarDataModel.getBimestre(), obj.getBimestre());
        dados.put(MediaEscolarDataModel.getSituacao(), obj.getSituacao());
        dados.put(MediaEscolarDataModel.getNotaProva(), obj.getNotaProva());
        dados.put(MediaEscolarDataModel.getNotaMateria(), obj.getNotaTrabalho());
        dados.put(MediaEscolarDataModel.getMediaFinal(), obj.getMediaFinal());

        sucesso = alterar(MediaEscolarDataModel.getTABELA(), dados);

        return sucesso;
    }

    public List<MediaEscolar> listar() {
        return getAllMediaEscolar();
    }

    public ArrayList<MediaEscolar> getResultadoFinal() {

        return getAllResultadoFinal();

    }


}
