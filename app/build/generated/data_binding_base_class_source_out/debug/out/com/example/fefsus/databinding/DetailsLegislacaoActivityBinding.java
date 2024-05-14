// Generated by view binder compiler. Do not edit!
package com.example.fefsus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fefsus.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DetailsLegislacaoActivityBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView textDel;

  @NonNull
  public final TextView textViewDetailsDescricao;

  @NonNull
  public final TextView textViewDetalhesNumero;

  @NonNull
  public final View view2;

  @NonNull
  public final View view3;

  private DetailsLegislacaoActivityBinding(@NonNull LinearLayout rootView,
      @NonNull TextView textDel, @NonNull TextView textViewDetailsDescricao,
      @NonNull TextView textViewDetalhesNumero, @NonNull View view2, @NonNull View view3) {
    this.rootView = rootView;
    this.textDel = textDel;
    this.textViewDetailsDescricao = textViewDetailsDescricao;
    this.textViewDetalhesNumero = textViewDetalhesNumero;
    this.view2 = view2;
    this.view3 = view3;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static DetailsLegislacaoActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DetailsLegislacaoActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.details_legislacao_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DetailsLegislacaoActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.textDel;
      TextView textDel = ViewBindings.findChildViewById(rootView, id);
      if (textDel == null) {
        break missingId;
      }

      id = R.id.textViewDetailsDescricao;
      TextView textViewDetailsDescricao = ViewBindings.findChildViewById(rootView, id);
      if (textViewDetailsDescricao == null) {
        break missingId;
      }

      id = R.id.textViewDetalhesNumero;
      TextView textViewDetalhesNumero = ViewBindings.findChildViewById(rootView, id);
      if (textViewDetalhesNumero == null) {
        break missingId;
      }

      id = R.id.view2;
      View view2 = ViewBindings.findChildViewById(rootView, id);
      if (view2 == null) {
        break missingId;
      }

      id = R.id.view3;
      View view3 = ViewBindings.findChildViewById(rootView, id);
      if (view3 == null) {
        break missingId;
      }

      return new DetailsLegislacaoActivityBinding((LinearLayout) rootView, textDel,
          textViewDetailsDescricao, textViewDetalhesNumero, view2, view3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
