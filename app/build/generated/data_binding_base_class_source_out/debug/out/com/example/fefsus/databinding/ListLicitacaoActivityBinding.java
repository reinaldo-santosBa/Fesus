// Generated by view binder compiler. Do not edit!
package com.example.fefsus.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fefsus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListLicitacaoActivityBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final FloatingActionButton btnAdd;

  @NonNull
  public final EditText inputSearch;

  @NonNull
  public final ConstraintLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final RecyclerView recyclerViewLicitacoes;

  private ListLicitacaoActivityBinding(@NonNull LinearLayout rootView,
      @NonNull FloatingActionButton btnAdd, @NonNull EditText inputSearch,
      @NonNull ConstraintLayout linearLayout, @NonNull LinearLayout linearLayout2,
      @NonNull RecyclerView recyclerViewLicitacoes) {
    this.rootView = rootView;
    this.btnAdd = btnAdd;
    this.inputSearch = inputSearch;
    this.linearLayout = linearLayout;
    this.linearLayout2 = linearLayout2;
    this.recyclerViewLicitacoes = recyclerViewLicitacoes;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListLicitacaoActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListLicitacaoActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_licitacao_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListLicitacaoActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAdd;
      FloatingActionButton btnAdd = ViewBindings.findChildViewById(rootView, id);
      if (btnAdd == null) {
        break missingId;
      }

      id = R.id.inputSearch;
      EditText inputSearch = ViewBindings.findChildViewById(rootView, id);
      if (inputSearch == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      ConstraintLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      LinearLayout linearLayout2 = (LinearLayout) rootView;

      id = R.id.recyclerViewLicitacoes;
      RecyclerView recyclerViewLicitacoes = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewLicitacoes == null) {
        break missingId;
      }

      return new ListLicitacaoActivityBinding((LinearLayout) rootView, btnAdd, inputSearch,
          linearLayout, linearLayout2, recyclerViewLicitacoes);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
