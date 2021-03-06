package codes.wise.applivros;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import codes.wise.applivros.modelos.Autor;
import codes.wise.applivros.modelos.Livro;

public class FormularioLivroActivity extends AppCompatActivity {

    private EditText edLivroTitulo;
    private EditText edLivroAno;
    private AutoCompleteTextView acLivroAutor;
    private Autor autorSelecionado;
    private TextInputLayout tilLivroAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_livro);

        edLivroTitulo = findViewById(R.id.ed_livro_titulo);
        edLivroAno = findViewById(R.id.ed_livro_ano);

        tilLivroAutor = findViewById(R.id.til_livro_autor);
        acLivroAutor = findViewById(R.id.ac_livro_autor);

        acLivroAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                autorSelecionado = (Autor) adapterView.getItemAtPosition(position);
                tilLivroAutor.setError(null);
            }
        });

        acLivroAutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            // Para apagar oautor caso o usuario edit manualmente
            @Override
            public void afterTextChanged(Editable editable) {
                autorSelecionado = null;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Autor> autores = Autor.listAll(Autor.class);
        ArrayAdapter<Autor> acAdapter = new ArrayAdapter<Autor>(this, android.R.layout.simple_list_item_1, autores);
        acLivroAutor.setAdapter(acAdapter);

    }

    public void salvarLivro(View view) {

        String titulo = edLivroTitulo.getText().toString();
        int ano = 0 ;
        try {
            ano = Integer.valueOf(edLivroAno.getText().toString());
        }catch (NumberFormatException e){
            Snackbar.make(view, R.string.informe_o_ano, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.entendi, (v) -> {})
                    .show();
            return;
        }

        if (autorSelecionado != null){
            Livro livro = new Livro(titulo, ano, autorSelecionado);
            livro.save();

            finish();

        }else{
            tilLivroAutor.setError(getString(R.string.selecione_um_autor));
        }

    }

}
