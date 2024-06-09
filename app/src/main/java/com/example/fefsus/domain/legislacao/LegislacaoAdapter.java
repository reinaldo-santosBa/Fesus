package com.example.fefsus.domain.legislacao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fefsus.R;
import com.example.fefsus.pages.detailsLegislacao.DetailsLegislacaoActivity;

import java.util.ArrayList;

public class LegislacaoAdapter extends RecyclerView.Adapter<LegislacaoAdapter.ViewHolderLicitacoes> {

    private ArrayList<LegislacaoModel> licitacoesList;
    private ArrayList<LegislacaoModel> licitacoesListFiltradas;
    private LayoutInflater inflater;
    private Context context;
    private ActivityResultLauncher<Intent> addEditLauncher;

    public LegislacaoAdapter(ArrayList<LegislacaoModel> licitacoesList, Context context, ActivityResultLauncher<Intent> addEditLauncher) {
        this.licitacoesList = licitacoesList;
        this.licitacoesListFiltradas = new ArrayList<>(licitacoesList);
        this.context = context;
        this.addEditLauncher = addEditLauncher;

    }

    @NonNull
    @Override
    public ViewHolderLicitacoes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardLicitacoes = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_licitacao,
                parent,
                false
        );
        return new ViewHolderLicitacoes(cardLicitacoes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLicitacoes holder, int position) {
        LegislacaoModel licitacoesModel = licitacoesListFiltradas.get(position);
        if(!licitacoesModel.getNumero().isEmpty()){
            String numeroLegislacao =licitacoesModel.getNumero();
            String textoCompleto = "Número da legisção: " + numeroLegislacao;

            SpannableStringBuilder builder = new SpannableStringBuilder(textoCompleto);

            int detalheStart = 0;
            int detalheEnd = "Número da legisção:".length();
            builder.setSpan(new StyleSpan(Typeface.BOLD), detalheStart, detalheEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.numero.setText(builder);
        }

        if(licitacoesModel.getDescricao().length() > 30){
            String descricao =licitacoesModel.getDescricao().substring(0, 30);
            String textoCompleto = "Descrição: " + descricao + "...";

            SpannableStringBuilder builder = new SpannableStringBuilder(textoCompleto);

            int detalheStart = 0;
            int detalheEnd = "Descrição:".length();
            builder.setSpan(new StyleSpan(Typeface.BOLD), detalheStart, detalheEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.descricao.setText(builder);
        }else {
            String descricao =licitacoesModel.getDescricao();
            String textoCompleto = "Descrição: " + descricao;

            SpannableStringBuilder builder = new SpannableStringBuilder(textoCompleto);

            int detalheStart = 0;
            int detalheEnd = "Descrição:".length();
            builder.setSpan(new StyleSpan(Typeface.BOLD), detalheStart, detalheEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.descricao.setText(builder);
        }
        if (licitacoesModel.getDetalhe().length() > 110) {
            String detalhe = licitacoesModel.getDetalhe().substring(0, 110);
            String textoCompleto = "Detalhe: " + detalhe + " Clique para ver mais...";
            SpannableStringBuilder builder = new SpannableStringBuilder(textoCompleto);
            int detalheStart = 0;
            int detalheEnd = "Detalhe:".length();
            builder.setSpan(new StyleSpan(Typeface.BOLD), detalheStart, detalheEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            int cliqueStart = textoCompleto.lastIndexOf("Clique para ver mais...");
            int cliqueEnd = cliqueStart + "Clique para ver mais...".length();
            builder.setSpan(new UnderlineSpan(), cliqueStart, cliqueEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new ForegroundColorSpan(Color.BLUE), cliqueStart, cliqueEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.detalhe.setText(builder);
        } else {
            String detalhe = licitacoesModel.getDetalhe();
            String textoCompleto = "Detalhe: " + detalhe;
            SpannableStringBuilder builder = new SpannableStringBuilder(textoCompleto);
            int detalheStart = 0;
            int detalheEnd = "Detalhe:".length();
            builder.setSpan(new StyleSpan(Typeface.BOLD), detalheStart, detalheEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.detalhe.setText(builder);
        }

        holder.cardViewLicitacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("licitacoesModel.getID",licitacoesModel.getId().toString());
                Intent intent = new Intent(context, DetailsLegislacaoActivity.class);
                intent.putExtra("id", licitacoesModel.getId());
                addEditLauncher.launch(intent);
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtrar(String texto) {
        licitacoesListFiltradas.clear();
        if (texto.isEmpty()) {
            licitacoesListFiltradas.addAll(licitacoesList);
        } else {
            texto = texto.toLowerCase();
            for (LegislacaoModel licitacao : licitacoesList) {
                if (
                        licitacao.getDescricao().toLowerCase().contains(texto)
                                ||
                        licitacao.getNumero().toLowerCase().contains(texto)
                                ||
                        licitacao.getDetalhe().toLowerCase().contains(texto)
                ) {
                    licitacoesListFiltradas.add(licitacao);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return licitacoesListFiltradas.size();
    }

    public static class ViewHolderLicitacoes extends RecyclerView.ViewHolder {
        TextView detalhe;
        TextView descricao;
        TextView numero;
        CardView cardViewLicitacao;
        public ViewHolderLicitacoes(@NonNull View itemView) {
            super(itemView);
            detalhe = itemView.findViewById(R.id.textViewDetalhes);
            descricao = itemView.findViewById(R.id.textViewDetailsDescricao);
            numero = itemView.findViewById(R.id.textViewNumero);
            cardViewLicitacao = itemView.findViewById(R.id.card);
        }
    }
}
