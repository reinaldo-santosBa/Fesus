package com.example.fefsus.domain.licitacoes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fefsus.R;
import com.example.fefsus.pages.detailsLegislacao.DetailsLegislacaoActivity;
import com.example.fefsus.pages.listLicitacoes.ListLicitacoesActivity;

import java.util.ArrayList;

public class LicitacoesAdapter extends RecyclerView.Adapter<LicitacoesAdapter.ViewHolderLicitacoes> {

    private ArrayList<LicitacoesModel> licitacoesList;
    private ArrayList<LicitacoesModel> licitacoesListFiltradas;
    private LayoutInflater inflater;
    private Context context;

    public LicitacoesAdapter(ArrayList<LicitacoesModel> licitacoesList,Context context) {
        this.licitacoesList = licitacoesList;
        this.licitacoesListFiltradas = new ArrayList<>(licitacoesList);
        this.context = context;
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
        LicitacoesModel licitacoesModel = licitacoesListFiltradas.get(position);
        holder.descricao.setText(licitacoesModel.getDescricao());
        holder.numero.setText(licitacoesModel.getNumero());
        if (licitacoesModel.getDetalhe().length() > 100) {
            String textoCompleto = licitacoesModel.getDetalhe().substring(0,100) + ". Clique para ver mais...";
            SpannableStringBuilder builder = new SpannableStringBuilder(textoCompleto);

            SpannableString clickSpan = new SpannableString("Clique para ver mais...");
            clickSpan.setSpan(new UnderlineSpan(), 0, clickSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            clickSpan.setSpan(new ForegroundColorSpan(Color.BLUE), 0, clickSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            builder.replace(builder.length() - "Clique para ver mais...".length(), builder.length(), clickSpan);

            holder.detalhe.setText(builder);
        } else {
            // Exibir o detalhe completo
            holder.detalhe.setText(licitacoesModel.getDetalhe());
        }

        holder.cardViewLicitacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("licitacoesModel.getID",licitacoesModel.getId().toString());
                Intent intent = new Intent(context, DetailsLegislacaoActivity.class);
                intent.putExtra("id", licitacoesModel.getId());
                context.startActivity(intent);
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
            for (LicitacoesModel licitacao : licitacoesList) {
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
